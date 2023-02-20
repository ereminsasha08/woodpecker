package com.woodpecker.woodpecker.service.order;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.repository.GeographyMapRepository;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.web.AuthUser;
import lombok.RequiredArgsConstructor;
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
    public GeographyMap createAvailabilityMap(AuthUser authUser, GeographyMap geographyMap, Integer stage, Boolean isColorPlywood, String laser) {
        if (isColorPlywood && stage == Stage.ЖДУ_ПОКРАСКУ_НАЛИЧИЕ.ordinal())
            throw new IllegalArgumentException("Карта из покрашенных досок не может быть на покраске");
        geographyMap.setManager(authUser.getUser());
        geographyMap.setDescription("Карта из наличия в момент создания была на стадии: " + Stage.values()[stage].name() + geographyMap.getDescription() == null ? "" : geographyMap.getDescription());
        GeographyMap save = geographyMapRepository.save(geographyMap);
        save.setIsColorPlywood(isColorPlywood);
        OrderMap orderMap = new OrderMap(LocalDateTime.now(), geographyMap, false, stage, true);
        orderRepository.save(orderMap);
        geographyMap.setOrderMap(orderMap);
        orderMap.setLaser(laser);
        orderMap.setCompleted(true);
        orderMap.setIsColorPlywood(false);
        orderMap.setPlywoodList(List.of("Готов"));
        orderMap.setAllTime();
        return save;
    }

}
