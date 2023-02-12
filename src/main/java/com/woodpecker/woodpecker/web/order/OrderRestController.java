package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RestController
@RequestMapping(OrderRestController.REST_URL)
@RequiredArgsConstructor
public class OrderRestController {
    public final static String REST_URL = "rest/orders";

    private final OrderService orderService;

    @PostMapping
    public OrderMap create(@NotNull @RequestParam Integer orderId,
                           @Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime orderTerm,
                           @RequestParam Boolean isMarketPlace,
                           @RequestParam Boolean isColorPlywood,
                           @RequestParam Boolean isAvailability) {
        return orderService.create(orderId, orderTerm, isMarketPlace, isColorPlywood, isAvailability);
    }

    @GetMapping("/{id}")
    public OrderMap get(@PathVariable Integer id) {
        return orderService.findOrderById(id);
    }
}
