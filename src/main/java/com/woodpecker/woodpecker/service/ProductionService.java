package com.woodpecker.woodpecker.service;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.util.exception.ApplicationException;
import com.woodpecker.woodpecker.util.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .sorted(Comparator.comparing(OrderMap::getMarketPlace)
                        .thenComparing(OrderMap::getOrderTerm)
                        .thenComparing(OrderMap::getId))
                .toList();
    }

    @Transactional
    public void setConditionForMap(Integer id, Integer conditionMap) {
        if (conditionMap < Stage.ЖДЕТ_ПРИКЛЕЙКИ.ordinal())
            throw new ApplicationException("Нельзя менять состояния заказа до покраски", ErrorType.WRONG_REQUEST);

        int newCondition = ++conditionMap;
        OrderMap orderMap = orderRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Карта не найдена", ErrorType.DATA_NOT_FOUND));
        orderMap.setStage(newCondition);
        if (newCondition == Stage.ОТПРАВЛЕН.ordinal()) {
            orderMap.setCompleted(true);
        }
    }
}
