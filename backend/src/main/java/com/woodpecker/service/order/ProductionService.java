package com.woodpecker.service.order;

import com.woodpecker.model.map.Stage;
import com.woodpecker.model.order.Order;
import com.woodpecker.util.exception.ApplicationException;
import com.woodpecker.util.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductionService {

    private final OrderService orderService;

    public List<Order> getOrderForProduction() {
        return orderService.getAll(false)
                .stream()
                .filter(order -> !order.getCompleted())
                .sorted(
                        Comparator.comparing(Order::getMarketPlace).reversed()
//                                .thenComparing(Order::getIsAvailability)
                                .thenComparing(Order::getDateCreate)
                                .thenComparing(Order::getId)
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
        Order order = orderService.findOrderById(id);
        int newCondition = conditionMap + 1;
//        order.setStage(Stage.values()[newCondition]);
        setTimeAndCompleted(order, newCondition);
        updateStatusForAvailability(conditionMap, order);
    }

    private static void setTimeAndCompleted(Order order, int newCondition) {
//        if (newCondition == Stage.BEING_COMPLETED.getOrdersOperation())
//            order.setGluingEnd(LocalDateTime.now());
//        if (newCondition == Stage.PACKAGING.getOrdersOperation())
//            order.setPackedEnd(LocalDateTime.now());
//        if (newCondition == Stage.SENDING.getOrdersOperation() && order.getIsAvailability()) {
//            order.setStage(Stage.AVAILABILITY);
//            order.setCompleted(true);
//        } else if (newCondition == Stage.SENDING.getOrdersOperation()) {
//            order.setPostEnd(LocalDateTime.now());
//            order.setCompleted(true);
//        }
    }

    private static void updateStatusForAvailability(Integer conditionMap, Order order) {
//        if (conditionMap == Stage.ORDERS_FROM_AVAILABILITY.getOrdersOperation())
//        {
//            order.setStage(Stage.PACKAGING);
//            order.setPackedEnd(LocalDateTime.now());
//        }
//        if (conditionMap == Stage.ORDER_FROM_AVAILABILITY_NO_PAINT.getOrdersOperation())
//        {
//            order.setStage(Stage.WAITING_PAINT);
//            order.setCutEnd(LocalDateTime.now());
//        }
//        if (conditionMap == Stage.ORDER_FROM_AVAILABILITY_NO_GLUE.getOrdersOperation())
//        {
//            order.setStage(Stage.WAITING_GLUE);
//            order.setPaintingEnd(LocalDateTime.now());
//        }
    }
}
