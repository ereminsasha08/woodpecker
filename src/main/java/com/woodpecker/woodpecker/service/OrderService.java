package com.woodpecker.woodpecker.service;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.model.support.Laser;
import com.woodpecker.woodpecker.model.support.PlywoodList;
import com.woodpecker.woodpecker.repository.LaserRepository;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.repository.PlywoodListRepository;
import com.woodpecker.woodpecker.util.exception.ApplicationException;
import com.woodpecker.woodpecker.util.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    final private LaserRepository laserRepository;

    final private OrderRepository orderRepository;

    final private GeographyMapService geographyMapService;

    final private PlywoodListRepository plywoodListRepository;

    public List<OrderMap> getAll() {
        return orderRepository.findAll();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public OrderMap create(Integer id, LocalDateTime orderTerm, boolean marketPlace, boolean isColorPlyWood) {
        GeographyMap map = geographyMapService.getById(id);
        orderTerm = Objects.isNull(orderTerm) ? map.getDateTime().plusWeeks(2).plusDays(3) : orderTerm;
        OrderMap orderMap = new OrderMap(orderTerm, map, marketPlace, isColorPlyWood, Stage.В_ОЧЕРЕДИ_НА_РЕЗКУ.ordinal());
        map.setOrderMap(orderMap);
        return orderRepository.save(orderMap);
    }

    public OrderMap findOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new ApplicationException("Карта не найдена", ErrorType.DATA_ERROR));
    }

    public List<OrderMap> getOrdersWithSortedForCut() {
        return getAll()
                .stream()
                .filter(order -> !order.getCompleted() && order.getStage() >= Stage.НОВЫЙ_ЗАКАЗ.ordinal() && order.getStage() < Stage.ЖДЕТ_ПОКРАСКИ.ordinal())
                .sorted(Comparator.comparing(OrderMap::getMarketPlace).reversed()
                        .thenComparing(OrderMap::getOrderTerm))
                .toList();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void setLaser(Integer id) {
        OrderMap orderMap = findOrderById(id);
        chooseLaser(orderMap);
        orderMap.setStage(Stage.ПИЛИТСЯ.ordinal());
        orderMap.setCut_begin(LocalDateTime.now());
    }


    private void chooseLaser(OrderMap order) {
        Laser minCapasityLaser = laserRepository.findAll()
                .stream()
                .filter(laser -> laser.getMaxSize() >= order.getGeographyMap().getSize())
                .min(Comparator.comparing(Laser::getCapacity))
                .orElseThrow(() -> new ApplicationException("Нет потходящего лазер", ErrorType.DATA_NOT_FOUND));
        minCapasityLaser.setCapacity(order.getGeographyMap(), 1);
        order.setLaser(minCapasityLaser.getName());
    }

    @Transactional
    public List<String> infoCut(Integer id) {
        OrderMap orderMap = findOrderById(id);
        List<String> plywoodList = orderMap.getPlywoodList();
        if (plywoodList.isEmpty()) {
            serListsForMap(orderMap, plywoodList);
        }
        return plywoodList;
    }

    private void serListsForMap(OrderMap orderMap, List<String> plywoodList) {
        String typeMap = orderMap.getGeographyMap().getTypeMap();
        int calculationId = orderMap.getGeographyMap().getSize();
        calculationId += "мир".equalsIgnoreCase(typeMap) ? 1 : 2;
        calculationId *= orderMap.getGeographyMap().getIsMultiLevel() ? 1 : 10;
        Optional<PlywoodList> byId = plywoodListRepository.findById(calculationId);
        if (byId.isPresent()) {
            plywoodList.addAll(byId.get().getLists());
            orderMap.setPlywoodList(plywoodList);
        }
    }


    @Transactional
    public void cutComplete(Integer id, Boolean listIsComplete, Integer numberList) {
        OrderMap orderById = findOrderById(id);
        if (orderById.getStage() >= Stage.ЖДЕТ_ПОКРАСКИ.ordinal())
            throw new ApplicationException("Карта выпиленна, обновите страницу", ErrorType.APP_ERROR);
        List<String> plywoodList = orderById.getPlywoodList();
        String element = plywoodList.get(numberList);
        if (listIsComplete) {
            String s = element + " Готов";
            plywoodList.set(numberList, s);
        } else {
            plywoodList.set(numberList, element.substring(0, element.length() - 6).trim());
        }
        if (checkAllPlywoodList(plywoodList)) {
            if (orderById.getGeographyMap().getIsColorPlywood()) {
                orderById.setStage(Stage.ЖДЕТ_ПРИКЛЕЙКИ.ordinal());
            } else {
                orderById.setStage(Stage.ЖДЕТ_ПОКРАСКИ.ordinal());
            }
            orderById.setCut_end(LocalDateTime.now());
            refreshCapacity(orderById);
        }
    }

    private void refreshCapacity(OrderMap orderById) {
        String laserName = orderById.getLaser();
        Laser laser = laserRepository.findByName(laserName);
        laser.setCapacity(orderById.getGeographyMap(), -1);
    }

    private boolean checkAllPlywoodList(List<String> plywoodList) {
        return plywoodList
                .stream()
                .allMatch(s -> s.endsWith("Готов"));
    }


}
