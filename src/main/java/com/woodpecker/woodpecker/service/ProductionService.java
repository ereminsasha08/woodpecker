package com.woodpecker.woodpecker.service;

import com.woodpecker.woodpecker.model.map.OrderMap;
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
                        .thenComparing(OrderMap::getOrderTerm))
                .toList();
    }

    @Transactional
    public void setConditionForMap(Integer id, Integer conditionMap) {
        if (conditionMap < 8)
            throw new ApplicationException("Нельзя менять состояния заказа до покраски", ErrorType.WRONG_REQUEST);

        int newCondition = conditionMap + 2;
        OrderMap orderMap = orderRepository.findById(id).get();
        orderMap.getGeographyMap().setConditionMap(newCondition);
        if (newCondition == 16) {
            orderMap.setCompleted(true);
        }
    }
}
