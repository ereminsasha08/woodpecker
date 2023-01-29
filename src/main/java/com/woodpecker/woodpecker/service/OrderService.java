package com.woodpecker.woodpecker.service;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.support.Laser;
import com.woodpecker.woodpecker.model.support.PlywoodList;
import com.woodpecker.woodpecker.repository.LaserRepository;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.repository.PlywoodListRepository;
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
    public OrderMap create(Integer id, LocalDateTime orderTerm, boolean marketPlace) {
        GeographyMap map = geographyMapService.getById(id);
        map.setConditionMap(1);
        orderTerm = Objects.isNull(orderTerm)? map.getDateTime().plusWeeks(2).plusDays(3): orderTerm;
        OrderMap orderMap = new OrderMap(orderTerm, map, marketPlace);
        return orderRepository.save(orderMap);
    }

    public OrderMap get(Integer id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public List<OrderMap> getOrdersWithSortedCut() {
        return getAll()
                .stream()
                .filter(map -> !map.getCompleted())
                .sorted(Comparator.comparing(OrderMap::getMarketPlace).reversed()
                        .thenComparing(OrderMap::getOrderTerm))
                .toList();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void setLaser(Integer id) {
        OrderMap orderMap = orderRepository.findById(id).orElseThrow();
        chooseLaser(orderMap);
        orderMap.getGeographyMap().setConditionMap(2);
        orderMap.setCut_begin(LocalDateTime.now());
    }


    private void chooseLaser(OrderMap order) {
        Laser minCapasityLaser = laserRepository.findAll()
                .stream()
                .filter(laser -> laser.getPermissionSize().contains(order.getGeographyMap().getSize()))
                .min(Comparator.comparing(laser -> laser.getCapacity()))
                .get();
        System.out.println(minCapasityLaser);
        minCapasityLaser.setCapacity(order.getGeographyMap());
        System.out.println(minCapasityLaser);
        order.setLaser(minCapasityLaser.getName());
    }

    @Transactional
    public List<String> infoCut(Integer id) {
        OrderMap orderMap = orderRepository.findById(id).get();
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
                .filter(map -> (map.getGeographyMap().getConditionMap() == 3 || map.getGeographyMap().getConditionMap() == 4) && !map.getIsColorPlyWood())
                .filter(map -> !map.getCompleted())
                .sorted(Comparator.comparing(OrderMap::getMarketPlace).reversed()
                        .thenComparing(OrderMap::getOrderTerm))
                .toList();
    }
}
