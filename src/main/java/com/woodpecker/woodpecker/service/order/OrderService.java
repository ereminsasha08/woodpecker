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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @Cacheable("orders")
    public List<OrderMap> getAll(Boolean isCompleted) {
        return orderRepository.findByCompleted(isCompleted);
    }

    public OrderMap findOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new ApplicationException("Карта не найдена", ErrorType.DATA_ERROR));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public OrderMap create(AuthUser authUser, Integer id, LocalDateTime orderTerm, boolean marketPlace, boolean isAvailability) {

        GeographyMap map = geographyMapRepository.getById(id);
//        if (map.getManager().id() != authUser.id())
//            throw new IllegalArgumentException("Начинать можно только свои заказы");
        if (marketPlace && !isAvailability)
            orderTerm = Objects.isNull(orderTerm) ? LocalDateTime.now() : orderTerm;
        else orderTerm = Objects.isNull(orderTerm) ? LocalDateTime.now().plusWeeks(2).plusDays(3) : orderTerm;

        OrderMap orderMap = new OrderMap(orderTerm, map, marketPlace, Stage.SEQUENCING_CUT, isAvailability);
        map.setOrderMap(orderMap);
        return orderRepository.save(orderMap);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public OrderMap modifyOrder(AuthUser authUser, Integer id, LocalDateTime orderTerm, String light, String additional, String description,
                                String contact, Integer price, Boolean isMarketPlace, Boolean isAvailability) {
        OrderMap modifyOrder = findOrderById(id);
//        if (!modifyOrder.getIsAvailability() && modifyOrder.getGeographyMap().getManager().id() != authUser.id())
//            throw new IllegalArgumentException("Изменять можно только свои заказы");
        modifyOrder.setMarketPlace(isMarketPlace);
        modifyOrder.setIsPaid(isMarketPlace);
        orderTerm = Objects.isNull(orderTerm) ? LocalDateTime.now().plusWeeks(2).plusDays(3) : orderTerm;
        modifyOrder.setOrderTerm(orderTerm);

        modifyOrder.setIsAvailability(isAvailability);
        GeographyMap modifyGeographyMap = modifyOrder.getGeographyMap();
        modifyGeographyMap.setLight(light);
        modifyGeographyMap.setAdditional(additional);
        modifyGeographyMap.setDescription(description);
        modifyGeographyMap.setContact(contact);
        modifyGeographyMap.setPrice(price);
        if (!isAvailability) {
            modifyGeographyMap.setManager(authUser.getUser());
            checkIsAvailability(modifyOrder);
        }
        return orderRepository.save(modifyOrder);
    }

    private static void checkIsAvailability(OrderMap modifyOrder) {
        if (modifyOrder.getStage().getOrdersOperation() == Stage.AVAILABILITY.getOrdersOperation()) {
            modifyOrder.setStage(Stage.ORDERS_FROM_AVAILABILITY);
            modifyOrder.setCompleted(false);
        }
        if (modifyOrder.getStage().getOrdersOperation() == Stage.WAITING_GLUE_AVAILABILITY.getOrdersOperation()) {
            modifyOrder.setStage(Stage.ORDER_FROM_AVAILABILITY_NO_GLUE);
            modifyOrder.setCompleted(false);
        }
        if (modifyOrder.getStage().getOrdersOperation() == Stage.WAITING_PAINT_AVAILABILITY.getOrdersOperation()) {
            modifyOrder.setStage(Stage.ORDER_FROM_AVAILABILITY_NO_PAINT);
            modifyOrder.setCompleted(false);
        }
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void setPaid(Integer id, Boolean isPaid) {
        OrderMap orderMap = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Заказ не найден"));
        orderMap.setIsPaid(isPaid);
    }
}
