package com.woodpecker.woodpecker.service.order;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.util.exception.ApplicationException;
import com.woodpecker.woodpecker.util.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvailabilityService {

    private final OrderRepository orderRepository;

    private final OrderService orderService;


    public List<OrderMap> getAvailability() {
        return orderRepository.findByIsAvailability(true);
    }

    @Transactional
    public OrderMap modifyOrder(Integer id) {
        OrderMap modifyOrder = orderService.findOrderById(id);
        GeographyMap modifyGeographyMap = modifyOrder.getGeographyMap();
        if (!modifyOrder.getIsAvailability())
            throw new IllegalArgumentException("Нельзя менять карту заказ для карту не из наличия");
        modifyOrder.setIsAvailability(false);
        modifyOrder.setCompleted(false);
        modifyOrder.setStage(Stage.ЗАКАЗ_ИЗ_НАЛИЧИЯ.ordinal());
        return orderRepository.save(modifyOrder);
    }
}
