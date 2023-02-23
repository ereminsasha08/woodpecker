package com.woodpecker.woodpecker.service.order;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.repository.GeographyMapRepository;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.web.AuthUser;
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


    public List<OrderMap> getAvailability() {
        return orderRepository.findByIsAvailability(true);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public GeographyMap createAvailabilityMap(AuthUser authUser, GeographyMap geographyMap, Integer stage, Boolean isColorPlywood, String laser) {
        OrderMap orderMap = new OrderMap(LocalDateTime.now(), geographyMap, false, stage, true);
        if (isColorPlywood && stage == Stage.ЖДУ_ПОКРАСКУ_НАЛИЧИЕ.ordinal())
            throw new IllegalArgumentException("Карта из покрашенных досок не может быть на покраске");
        if (geographyMap.isNew()) {
            geographyMap.setManager(authUser.getUser());
            geographyMap.setDescription("Карта из наличия в момент создания была на стадии: " +
                    Stage.values()[stage].name());
            orderMap.setCompleted(true);
            orderMap.setIsColorPlywood(false);
            orderMap.setPlywoodList(List.of("Готов"));
            orderMap.setAllTime();
            geographyMap.setOrderMap(orderMap);

        } else {
            GeographyMap byId = geographyMapRepository.findById(geographyMap.id()).get();
            geographyMap.setManager(byId.getManager());
            orderMap = byId.getOrderMap();
            geographyMap.setOrderMap(orderMap);
        }
        orderMap.setLaser(laser);
        geographyMap.setIsColorPlywood(isColorPlywood);
        GeographyMap save = geographyMapRepository.save(geographyMap);
        orderRepository.save(orderMap);
        return save;
    }

}
