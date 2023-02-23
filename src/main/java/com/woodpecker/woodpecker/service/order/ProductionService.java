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
        if (conditionMap < Stage.ЖДЕТ_ПРИКЛЕЙКИ.ordinal())
            throw new ApplicationException("Нельзя менять состояния заказа до покраски", ErrorType.WRONG_REQUEST);
        OrderMap orderMap = orderService.findOrderById(id);
        int newCondition = conditionMap + 1;
        orderMap.setStage(newCondition);
        setTimeAndCompleted(orderMap, newCondition);
        updateStatusForAvailability(conditionMap, orderMap);
    }

    private static void setTimeAndCompleted(OrderMap orderMap, int newCondition) {
        if (newCondition == Stage.ДОДЕЛЫВАЕТСЯ.ordinal())
            orderMap.setGluing_end(LocalDateTime.now());
        if (newCondition == Stage.ЗАПАКОВЫВАЕТСЯ.ordinal())
            orderMap.setPacked_end(LocalDateTime.now());
        if (newCondition == Stage.ОТПРАВЛЕН.ordinal() && orderMap.getIsAvailability()) {
            orderMap.setStage(Stage.НАЛИЧИЕ.ordinal());
            orderMap.setCompleted(true);
        } else if (newCondition == Stage.ОТПРАВЛЕН.ordinal()) {
            orderMap.setPost_end(LocalDateTime.now());
            orderMap.setCompleted(true);
        }
    }

    private static void updateStatusForAvailability(Integer conditionMap, OrderMap orderMap) {
        if (conditionMap == Stage.ЗАКАЗ_ИЗ_НАЛИЧИЯ.ordinal())
        {
            orderMap.setStage(Stage.ЗАПАКОВЫВАЕТСЯ.ordinal());
            orderMap.setPacked_end(LocalDateTime.now());
        }
        if (conditionMap == Stage.ЗАКАЗ_ИЗ_НАЛИЧИЯ_НЕ_ПОКРАШЕННЫЙ.ordinal())
        {
            orderMap.setStage(Stage.ЖДЕТ_ПОКРАСКИ.ordinal());
            orderMap.setCut_end(LocalDateTime.now());
        }
        if (conditionMap == Stage.ЗАКАЗ_ИЗ_НАЛИЧИЯ_НЕ_ПРИКЛЕЕННЫЙ.ordinal())
        {
            orderMap.setStage(Stage.ЖДЕТ_ПРИКЛЕЙКИ.ordinal());
            orderMap.setPainting_end(LocalDateTime.now());
        }
    }
}
