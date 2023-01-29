package com.woodpecker.woodpecker.service;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.OrderMap;
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
        map.setConditionMap(1);
        orderTerm = Objects.isNull(orderTerm) ? map.getDateTime().plusWeeks(2).plusDays(3) : orderTerm;
        OrderMap orderMap = new OrderMap(orderTerm, map, marketPlace, isColorPlyWood);
        return orderRepository.save(orderMap);
    }

    public OrderMap findOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public List<OrderMap> getOrdersWithSortedCut() {
        return getAll()
                .stream()
                .filter(map -> !map.getCompleted() && map.getGeographyMap().getConditionMap() >= 1 && map.getGeographyMap().getConditionMap() < 4)
                .sorted(Comparator.comparing(OrderMap::getMarketPlace).reversed()
                        .thenComparing(OrderMap::getOrderTerm))
                .toList();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void setLaser(Integer id) {
        OrderMap orderMap = findOrderById(id);
        if (orderMap.getIsColorPlyWood())
            throw new ApplicationException("Нужны покрашенные доски", ErrorType.APP_ERROR);
        chooseLaser(orderMap);
        orderMap.getGeographyMap().setConditionMap(2);
        orderMap.setCut_begin(LocalDateTime.now());
    }


    private void chooseLaser(OrderMap order) {
        Laser minCapasityLaser = laserRepository.findAll()
                .stream()
                .filter(laser -> laser.getMaxSize() >= order.getGeographyMap().getSize())
                .min(Comparator.comparing(Laser::getCapacity))
                .get();
        System.out.println(minCapasityLaser);
        minCapasityLaser.setCapacity(order.getGeographyMap(), 1);
        System.out.println(minCapasityLaser);
        order.setLaser(minCapasityLaser.getName());
    }

    @Transactional
    public List<String> infoCut(Integer id) {
        OrderMap orderMap = findOrderById(id);
        List<String> plywoodList = orderMap.getPlywoodList();
        if (plywoodList.isEmpty()) {
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
        return plywoodList;
    }

    public List<OrderMap> getPaint() {
        return getAll().stream()
                .filter(map -> {
                    int conditionMap = map.getGeographyMap().getConditionMap();
                    if ((conditionMap == 4 || conditionMap == 5) || (map.getIsColorPlyWood() && conditionMap == 1))
                        return true;
                    else return false;
                })
                .filter(map -> !map.getCompleted())
                .sorted(Comparator.comparing(OrderMap::getMarketPlace).reversed()
                        .thenComparing(OrderMap::getOrderTerm))
                .toList();
    }

    @Transactional
    public void setColorPlywood(Integer id) {
        OrderMap orderMap = orderRepository.findById(id).get();
        orderMap.setIsColorPlyWood(false);
        if (orderMap.getGeographyMap().getConditionMap() >= 5 && orderMap.getGeographyMap().getConditionMap() <= 7) {
            orderMap.getGeographyMap().setConditionMap(8);
        }
    }

    @Transactional
    public void cutComplete(Integer id, Boolean listIsComplete, Integer numberList) {
        OrderMap orderById = findOrderById(id);
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
                orderById.getGeographyMap().setConditionMap(6);
            } else {
                orderById.getGeographyMap().setConditionMap(4);
            }
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

    @Transactional
    public void setPainter(Integer id, String namePainter) {
        OrderMap orderById = findOrderById(id);
        orderById.setNamePainter(namePainter);
        orderById.getGeographyMap().setConditionMap(5);
    }
}
