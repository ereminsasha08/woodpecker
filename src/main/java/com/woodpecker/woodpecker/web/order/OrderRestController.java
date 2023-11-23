package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.service.order.OrderService;
import com.woodpecker.woodpecker.web.AuthUser;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(OrderRestController.REST_URL)
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderRestController {
    public final static String REST_URL = "rest/orders/";

    private final OrderService orderService;

    @GetMapping
    public List<OrderMap> getAll() {
        return orderService.getAll(false);
    }

    @GetMapping("/{id}")
    public OrderMap get(@PathVariable Integer id) {
        return orderService.findOrderById(id);
    }

    @PostMapping
    public OrderMap create(@AuthenticationPrincipal AuthUser authUser,
                           @NotNull @RequestParam Integer orderId,
                           @Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime orderTerm,
                           @RequestParam Boolean isMarketPlace,
                           @RequestParam Boolean isAvailability) {
        return orderService.create(authUser, orderId, orderTerm, isMarketPlace, isAvailability);
    }


    @PatchMapping
    public OrderMap modifyOrderFromAvailability(@AuthenticationPrincipal AuthUser authUser,
                                                @NotNull @RequestParam Integer id,
                                                @Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime orderTerm,
                                                @RequestParam String light,
                                                @RequestParam String additional,
                                                @RequestParam String description,
                                                @RequestParam String contact,
                                                @RequestParam Integer price,
                                                @RequestParam Boolean isMarketPlace,
                                                @RequestParam Boolean isAvailability) {
        return orderService.modifyOrder(authUser, id, orderTerm, light, additional, description, contact, price, isMarketPlace, isAvailability);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void paid(@PathVariable Integer id, @RequestParam Boolean isPaid) {
        orderService.setPaid(id, isPaid);
    }


}
