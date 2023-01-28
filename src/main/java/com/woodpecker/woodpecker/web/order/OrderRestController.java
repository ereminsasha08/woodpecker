package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.OrderMap;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping(OrderRestController.REST_URL)
@RestController
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    public final static String REST_URL = "rest/orders";
    private final OrderRepository orderRepository;

    @GetMapping
    public List<OrderMap> all() {
        return orderService.getAll();
    }

    @PostMapping
    public OrderMap create(@RequestBody Integer id, @RequestBody @DateTimeFormat() LocalDateTime orderTerm) {
        return orderService.create(id, orderTerm);
    }

    @GetMapping("/{id}")
    public OrderMap get(@PathVariable Integer id) {
        return orderService.get(id);
    }

}
