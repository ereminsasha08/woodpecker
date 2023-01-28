package com.woodpecker.woodpecker.service;

import com.woodpecker.woodpecker.model.GeographyMap;
import com.woodpecker.woodpecker.model.OrderMap;
import com.woodpecker.woodpecker.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    final private OrderRepository orderRepository;

    final private GeographyMapService geographyMapService;

    public List<OrderMap> getAll() {
        return orderRepository.findAll();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public OrderMap create(Integer id, LocalDateTime orderTerm) {
        GeographyMap map = geographyMapService.getById(id);
        OrderMap orderMap = new OrderMap(orderTerm, map);
        return orderRepository.save(orderMap);
    }

    public OrderMap get(Integer id) {
        return orderRepository.findById(id).orElseThrow();
    }
}
