package com.woodpecker.woodpecker.service.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.util.exception.ApplicationException;
import com.woodpecker.woodpecker.util.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductionService {

    private final OrderService orderService;

    public List<OrderMap> getOrderForProduction() {
        return orderService.getAll(false)
                .stream()
                .filter(order -> !order.getCompleted())
                .sorted(
                        Comparator.comparing(OrderMap::getMarketPlace).reversed()
                                .thenComparing(OrderMap::getIsAvailability)
                                .thenComparing(OrderMap::getOrderTerm)
                                .thenComparing(OrderMap::getId)
                )
                .toList();
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void updateConditionOrder(Integer id, Integer conditionMap) {
        if (conditionMap < Stage.WAITING_GLUE.getOrdersOperation())
            throw new ApplicationException("Нельзя менять состояния заказа до покраски", ErrorType.WRONG_REQUEST);
        OrderMap orderMap = orderService.findOrderById(id);
        int newCondition = conditionMap + 1;
        orderMap.setStage(Stage.values()[newCondition]);
        setTimeAndCompleted(orderMap, newCondition);
        updateStatusForAvailability(conditionMap, orderMap);
    }

    private static void setTimeAndCompleted(OrderMap orderMap, int newCondition) {
        if (newCondition == Stage.BEING_COMPLETED.getOrdersOperation())
            orderMap.setGluing_end(LocalDateTime.now());
        if (newCondition == Stage.PACKAGING.getOrdersOperation())
            orderMap.setPacked_end(LocalDateTime.now());
        if (newCondition == Stage.SENDING.getOrdersOperation() && orderMap.getIsAvailability()) {
            orderMap.setStage(Stage.AVAILABILITY);
            orderMap.setCompleted(true);
        } else if (newCondition == Stage.SENDING.getOrdersOperation()) {
            orderMap.setPost_end(LocalDateTime.now());
            orderMap.setCompleted(true);
        }
    }

    private static void updateStatusForAvailability(Integer conditionMap, OrderMap orderMap) {
        if (conditionMap == Stage.ORDERS_FROM_AVAILABILITY.getOrdersOperation())
        {
            orderMap.setStage(Stage.PACKAGING);
            orderMap.setPacked_end(LocalDateTime.now());
        }
        if (conditionMap == Stage.ORDER_FROM_AVAILABILITY_NO_PAINT.getOrdersOperation())
        {
            orderMap.setStage(Stage.WAITING_PAINT);
            orderMap.setCut_end(LocalDateTime.now());
        }
        if (conditionMap == Stage.ORDER_FROM_AVAILABILITY_NO_GLUE.getOrdersOperation())
        {
            orderMap.setStage(Stage.WAITING_GLUE);
            orderMap.setPainting_end(LocalDateTime.now());
        }
    }
}
