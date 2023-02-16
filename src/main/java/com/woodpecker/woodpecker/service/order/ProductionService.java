package com.woodpecker.woodpecker.service.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.util.exception.ApplicationException;
import com.woodpecker.woodpecker.util.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductionService {

    private final OrderRepository orderRepository;

    public List<OrderMap> getOrderForProduction() {
        return orderRepository
                .findAll()
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
    public void updateConditionOrder(Integer id, Integer conditionMap) {
        if (conditionMap < Stage.ЖДЕТ_ПРИКЛЕЙКИ.ordinal())
            throw new ApplicationException("Нельзя менять состояния заказа до покраски", ErrorType.WRONG_REQUEST);
        OrderMap orderMap = orderRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Карта не найдена", ErrorType.DATA_NOT_FOUND));
        int newCondition = conditionMap + 1;
        orderMap.setStage(newCondition);
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
        if (conditionMap == Stage.ЗАКАЗ_ИЗ_НАЛИЧИЯ.ordinal())
        {
            orderMap.setStage(Stage.ЗАПАКОВЫВАЕТСЯ.ordinal());
            orderMap.setPacked_end(LocalDateTime.now());
        }
    }
}
