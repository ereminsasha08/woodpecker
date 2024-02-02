package org.woodpecker.service.order;

import org.woodpecker.model.map.Stage;
import org.woodpecker.model.order.Order;
import org.woodpecker.repository.GeographyMapRepository;
import org.woodpecker.repository.OrderRepository;
import org.woodpecker.model.map.GeographyMap;
import org.woodpecker.controller.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvailabilityService {

    private final OrderRepository orderRepository;

    private final GeographyMapRepository geographyMapRepository;


    public List<Order> getAvailability() {
        return null;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public GeographyMap createAvailabilityMap(AuthUser authUser, GeographyMap geographyMap, Integer stage, Boolean isColorPlywood, String laser) {
        Order order = new Order(LocalDateTime.now(), geographyMap, false, Stage.values()[stage]);
        if (isColorPlywood && stage == Stage.WAITING_PAINT_AVAILABILITY.getOrdersOperation())
            throw new IllegalArgumentException("Карта из покрашенных досок не может быть на покраске");
        if (geographyMap.isNew()) {
//            geographyMap.setManager(authUser.getUser());
            geographyMap.setDescription("Карта из наличия в момент создания была на стадии: " +
                    Stage.values()[stage].name());
            order.setCompleted(true);
//            order.setIsColorPlywood(false);
//            order.setPlywoodList(List.of("Готов"));
            order.setAllTime();
//            geographyMap.setOrder(order);

        } else {
            GeographyMap byId = geographyMapRepository.findById(geographyMap.id()).get();
//            geographyMap.setManager(byId.getManager());
//            order = byId.getOrder();
//            geographyMap.setOrder(order);
        }
//        order.setLaser(laser);
        geographyMap.setIsColorPlywood(isColorPlywood);
        GeographyMap save = geographyMapRepository.save(geographyMap);
        orderRepository.save(order);
        return save;
    }

}
