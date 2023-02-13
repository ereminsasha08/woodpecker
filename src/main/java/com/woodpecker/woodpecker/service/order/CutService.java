package com.woodpecker.woodpecker.service.order;

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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CutService {

    private final OrderService orderService;

    private final LaserRepository laserRepository;

    private final PlywoodListRepository plywoodListRepository;
    private final OrderRepository orderRepository;


    public List<OrderMap> sortedForCut() {
        return orderService.getAll(false).stream()
                .filter(
                        order -> !order.getCompleted()
                                && order.getStage() >= Stage.НОВЫЙ_ЗАКАЗ.ordinal()
                                && order.getStage() < Stage.ЖДЕТ_ПОКРАСКИ.ordinal())
                .sorted(
                        Comparator.comparing(OrderMap::getMarketPlace).reversed()
                                .thenComparing(OrderMap::getIsAvailability)
                                .thenComparing(OrderMap::getOrderTerm))
                .toList();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void setLaser(Integer id) {
        OrderMap orderMap = orderService.findOrderById(id);
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

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<String> infoCut(Integer id) {
        OrderMap orderMap = orderService.findOrderById(id);
        List<String> plywoodList = orderMap.getPlywoodList();
        if (plywoodList.isEmpty()) {
            serListsForMap(orderMap, plywoodList);
        }
        return plywoodList;
    }

    private void serListsForMap(OrderMap orderMap, List<String> plywoodList) {
        String typeMap = orderMap.getGeographyMap().getTypeMap();
//        List<String> standardKit = List.of("1", "2", "3", "4", "5", "6", "7", "8");
        List<String> standardKit = List.of("1");

        int calculationId = 0;
        calculationId += "мир".equalsIgnoreCase(typeMap) ? 1 : 0;
        calculationId += "россия".equalsIgnoreCase(typeMap) ? 2 : 0;
        if (calculationId == 0) {
            plywoodList.addAll(standardKit);
            orderMap.setPlywoodList(plywoodList);
            return;
        }
        calculationId += orderMap.getGeographyMap().getSize();
        calculationId *= orderMap.getGeographyMap().getIsMultiLevel() ? 1 : 10;
        if (orderMap.getGeographyMap().getIsColorPlywood() && !"росиия".equalsIgnoreCase(typeMap)) {
            calculationId *= calculationId;
        }
        Optional<PlywoodList> specialKit = plywoodListRepository.findById(calculationId);
        if (specialKit.isPresent()) {
            plywoodList.addAll(specialKit.get().getLists());
            orderMap.setPlywoodList(plywoodList);
        } else {
            plywoodList.addAll(standardKit);
            orderMap.setPlywoodList(plywoodList);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void cutComplete(Integer id, Boolean listIsComplete, Integer numberList) {
        OrderMap orderById = orderService.findOrderById(id);
        if (orderById.getStage() >= Stage.ЖДЕТ_ПОКРАСКИ.ordinal())
            throw new ApplicationException("Карта выпиленна, обновите страницу", ErrorType.APP_ERROR);
        List<String> plywoodList = orderById.getPlywoodList();
        String element = plywoodList.get(numberList);
        if (listIsComplete) {
            String s;
            if (element.endsWith("Лист загравирован")) {
                s = element.split(" ")[0] + ( " Лист готов");
                plywoodList.set(numberList, s);
            } else if (!element.endsWith(" Лист готов")) {
                s = element.split(" ")[0] + " Лист загравирован";
                plywoodList.set(numberList, s);
            }
        } else {
            plywoodList.set(numberList, element.split(" ")[0]);
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
        Laser laser = laserRepository.findByName(laserName).orElseThrow(() -> new ApplicationException("Лазер не найден", ErrorType.DATA_NOT_FOUND));
        ;
        laser.setCapacity(orderById.getGeographyMap(), -1);
    }

    private boolean checkAllPlywoodList(List<String> plywoodList) {
        return plywoodList
                .stream()
                .allMatch(s -> s.toLowerCase().endsWith("готов"));
    }

    @Transactional
    public void changeLaser(Integer id, String laserName) {
        OrderMap orderById = orderService.findOrderById(id);
        if (orderById.getLaser().equalsIgnoreCase(laserName) || checkAllPlywoodList(orderById.getPlywoodList())) {
            throw new ApplicationException("Лазер не изменен", ErrorType.DATA_NOT_FOUND);
        }
        refreshCapacity(orderById);
        Laser laser = laserRepository.findByName(laserName).orElseThrow(() -> new ApplicationException("Лазер не найден", ErrorType.DATA_NOT_FOUND));
        laser.setCapacity(orderById.getGeographyMap(), 1);
        orderById.setLaser(laser.getName());
    }
}
