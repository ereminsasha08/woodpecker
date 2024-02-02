package org.woodpecker.service.order;

import org.woodpecker.model.order.Order;
import org.woodpecker.model.support.Laser;
import org.woodpecker.repository.LaserRepository;
import org.woodpecker.repository.PlywoodListRepository;
import org.woodpecker.util.exception.ApplicationException;
import org.woodpecker.util.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CutService {

    private final OrderService orderService;

    private final LaserRepository laserRepository;

    private final PlywoodListRepository plywoodListRepository;


    public List<Order> sortedForCut() {
        return orderService.getAll(false)
                .stream()
                .filter(
                        order -> !order.getCompleted()
//                                && order.getStage().getOrdersOperation() >= NEW_ORDER.getOrdersOperation()
//                                && order.getStage().getOrdersOperation() < WAITING_PAINT.getOrdersOperation()
                )
                .sorted(
                        Comparator.comparing(Order::getMarketPlace).reversed()
//                                .thenComparing(Order::getIsAvailability)
                                .thenComparing(Order::getDateCreate)
                                .thenComparing(Order::getId)
                )
                .toList();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void setLaserAndList(Integer id, Boolean isColorPlywood, Boolean isWoodStain) {
        Order order = orderService.findOrderById(id);
//        if (!order.getPlywoodList().isEmpty() && checkAllPlywoodList(order.getPlywoodList())) {
//            throw new ApplicationException("Нельзя менять тип производства выпиленной карты", ErrorType.DATA_NOT_FOUND);
//        }
//        if (order.getPlywoodList().isEmpty()) {
//            order.setLaser(chooseLaser(order));
//        }
        setListsForMap(order, new LinkedList<>(), isColorPlywood, isWoodStain);
//        order.setIsColorPlywood(isColorPlywood);
//        order.getGeographyMap().setIsColorPlywood(isColorPlywood);
//        order.setStage(CUTTING);
//        order.setCutBegin(LocalDateTime.now());
    }

    private String chooseLaser(Order order) {
        Laser minCapasityLaser = laserRepository.findAll()
                .stream()
//                .filter(laser -> laser.getMaxSize() >= order.getGeographyMap().getSize())
                .min(Comparator.comparing(Laser::getCapacity))
                .orElseThrow(() -> new ApplicationException("Нет потходящего лазер", ErrorType.DATA_NOT_FOUND));
//        minCapasityLaser.setCapacity(order.getGeographyMap(), 1);
        return minCapasityLaser.getName();
    }


    public List<String> infoCut(Integer id) {
//        List<String> plywoodList = orderService.findOrderById(id).getGeographyMap().get(0).getGeographyMapProduction().getPlywoodList();
//        log.info("InfoCut plywoodList: {}", plywoodList.toArray());
        return null;
    }

    private void setListsForMap(Order order, List<String> plywoodList, Boolean isColorPlywood, Boolean isWoodStain) {
//        String typeMap = order.getGeographyMap().getTypeMap();
//        List<String> standardKit;
//        if (order.getGeographyMap().getIsMultiLevel())
//            standardKit = List.of("3", "6", "8");
//        else standardKit = List.of("1", "2", "3");
//        List<String> standardKit = List.of("1");

//        int calculationId = 0;
//        calculationId += "мир".equalsIgnoreCase(typeMap) ? 1 : 0;
//        calculationId += "россия".equalsIgnoreCase(typeMap) ? 2 : 0;
//        if (calculationId == 0) {
//            plywoodList.addAll(standardKit);
//            order.setPlywoodList(plywoodList);
//            return;
//        }
//        calculationId += order.getGeographyMap().getSize();
//        calculationId *= order.getGeographyMap().getIsMultiLevel() ? 1 : 10;
////        Морилка с цветом
//        if (isColorPlywood && isWoodStain && !"росиия".equalsIgnoreCase(typeMap) && !order.getGeographyMap().getIsMonochromatic()) {
//            calculationId *= calculationId;
//        }
//        Optional<PlywoodList> specialKit = plywoodListRepository.findById(calculationId);
//        if (specialKit.isPresent()) {
//            plywoodList.addAll(specialKit.get().getLists());
//            order.setPlywoodList(plywoodList);
//        } else {
//            plywoodList.addAll(standardKit);
//            order.setPlywoodList(plywoodList);
//        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void cutComplete(Integer id, Boolean listIsComplete, Integer numberList) {
//        Order orderById = orderService.findOrderById(id);
//        if (orderById.getStage().getOrdersOperation() >= WAITING_PAINT.getOrdersOperation())
//            throw new ApplicationException("Карта выпиленна, обновите страницу", ErrorType.APP_ERROR);
//        List<String> plywoodList = orderById.getPlywoodList();
//        String element = plywoodList.get(numberList);
//        if (listIsComplete) {
//            String s;
//            if (element.endsWith("Лист загравирован")) {
//                s = element.split(" ")[0] + (" Лист готов");
//                plywoodList.set(numberList, s);
//            } else if (!element.endsWith(" Лист готов")) {
//                s = element.split(" ")[0] + " Лист загравирован";
//                plywoodList.set(numberList, s);
//            }
//        } else {
//            plywoodList.set(numberList, element.split(" ")[0]);
//        }
//        if (checkAllPlywoodList(plywoodList)) {
//            if (orderById.getGeographyMap().getIsColorPlywood()) {
//                orderById.setStage(WAITING_GLUE);
//            } else {
//                orderById.setStage(WAITING_PAINT);
//            }
//            orderById.setCutEnd(LocalDateTime.now());
//            refreshCapacity(orderById);
//        }
    }

    public void refreshCapacity(Order orderById) {
//        String laserName = orderById.getLaser();
//        Laser laser = laserRepository.findByName(laserName).orElseThrow(() -> new ApplicationException("Лазер не найден", ErrorType.DATA_NOT_FOUND));
//        laser.setCapacity(orderById.getGeographyMap(), -1);
    }

    private boolean checkAllPlywoodList(List<String> plywoodList) {
        return plywoodList
                .stream()
                .allMatch(s -> s.toLowerCase().endsWith("готов"));
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void changeLaser(Integer id, String laserName) {
//        Order orderById = orderService.findOrderById(id);
//        if (orderById.getLaser().equalsIgnoreCase(laserName) || checkAllPlywoodList(orderById.getPlywoodList())) {
//            throw new ApplicationException("Лазер не изменен", ErrorType.DATA_NOT_FOUND);
//        }
//        refreshCapacity(orderById);
//        Laser laser = laserRepository.findByName(laserName).orElseThrow(() -> new ApplicationException("Лазер не найден", ErrorType.DATA_NOT_FOUND));
//        laser.setCapacity(orderById.getGeographyMap(), 1);
//        orderById.setLaser(laser.getName());
    }
}
