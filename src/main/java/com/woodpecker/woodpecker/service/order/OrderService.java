package com.woodpecker.woodpecker.service.order;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.repository.GeographyMapRepository;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.util.exception.ApplicationException;
import com.woodpecker.woodpecker.util.exception.ErrorType;
import com.woodpecker.woodpecker.web.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {


    final private OrderRepository orderRepository;

    final private GeographyMapRepository geographyMapRepository;

    public List<OrderMap> getAll(Boolean isCompleted) {
        return orderRepository.findByCompleted(isCompleted);
    }

    public OrderMap findOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new ApplicationException("Карта не найдена", ErrorType.DATA_ERROR));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public OrderMap create(AuthUser authUser, Integer id, LocalDateTime orderTerm, boolean marketPlace, boolean isColorPlyWood, boolean isAvailability) {

        GeographyMap map = geographyMapRepository.getById(id);
//        if (map.getManager().id() != authUser.id())
//            throw new IllegalArgumentException("Начинать можно только свои заказы");
        if (marketPlace && !isAvailability)
            orderTerm = Objects.isNull(orderTerm) ? LocalDateTime.now() : orderTerm;
        else orderTerm = Objects.isNull(orderTerm) ? LocalDateTime.now().plusWeeks(2).plusDays(3) : orderTerm;
        OrderMap orderMap = new OrderMap(orderTerm, map, marketPlace, isColorPlyWood, Stage.В_ОЧЕРЕДИ_НА_РЕЗКУ.ordinal(), isAvailability);
        map.setOrderMap(orderMap);
        return orderRepository.save(orderMap);
    }

    @Transactional
    public OrderMap modifyOrder(AuthUser authUser, Integer id, LocalDateTime orderTerm, String light, String additional, String description,
                                String contact, Integer price, Boolean isMarketPlace, Boolean isAvailability, Boolean isColorPlywood) {
        OrderMap modifyOrder = findOrderById(id);
//        if (!modifyOrder.getIsAvailability() && modifyOrder.getGeographyMap().getManager().id() != authUser.id())
//            throw new IllegalArgumentException("Изменять можно только свои заказы");
        modifyOrder.setMarketPlace(isMarketPlace);
        if (modifyOrder.getStage() >= Stage.ПИЛИТСЯ.ordinal() && isColorPlywood != modifyOrder.getIsColorPlywood())
            throw new IllegalArgumentException("Нельзя менять тип покраски для карты, которая пилится");
        modifyOrder.setIsColorPlywood(isColorPlywood);

        orderTerm = Objects.isNull(orderTerm) ? LocalDateTime.now().plusWeeks(2).plusDays(3) : orderTerm;
        modifyOrder.setOrderTerm(orderTerm);

        modifyOrder.setIsAvailability(isAvailability);
        GeographyMap modifyGeographyMap = modifyOrder.getGeographyMap();
        modifyGeographyMap.setLight(light);
        modifyGeographyMap.setAdditional(additional);
        modifyGeographyMap.setDescription(description);
        modifyGeographyMap.setContact(contact);
        modifyGeographyMap.setPrice(price);
        modifyGeographyMap.setIsColorPlywood(isColorPlywood);
        if (modifyOrder.getStage() == Stage.НАЛИЧИЕ.ordinal()) {
            modifyOrder.setStage(Stage.ЗАКАЗ_ИЗ_НАЛИЧИЯ.ordinal());
            modifyOrder.setCompleted(false);
        }
        return orderRepository.save(modifyOrder);
    }
}
