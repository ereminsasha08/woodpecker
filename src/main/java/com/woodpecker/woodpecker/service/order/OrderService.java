package com.woodpecker.woodpecker.service.order;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.repository.GeographyMapRepository;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.service.map.GeographyMapService;
import com.woodpecker.woodpecker.util.exception.ApplicationException;
import com.woodpecker.woodpecker.util.exception.ErrorType;
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
    public OrderMap create(Integer id, LocalDateTime orderTerm, boolean marketPlace, boolean isColorPlyWood, boolean isAvailability) {
        GeographyMap map = geographyMapRepository.getById(id);
        orderTerm = Objects.isNull(orderTerm) ? map.getDateTime().plusWeeks(2).plusDays(3) : orderTerm;
        OrderMap orderMap = new OrderMap(orderTerm, map, marketPlace, isColorPlyWood, Stage.В_ОЧЕРЕДИ_НА_РЕЗКУ.ordinal(), isAvailability);
        map.setOrderMap(orderMap);
        return orderRepository.save(orderMap);
    }

}
