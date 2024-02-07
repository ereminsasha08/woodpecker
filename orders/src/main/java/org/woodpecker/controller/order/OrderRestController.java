package org.woodpecker.controller.order;

import org.woodpecker.repository.model.order.Order;
import org.woodpecker.service.order.OrderService;
import org.woodpecker.controller.AuthUser;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(OrderRestController.REST_URL)
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderRestController {
    public final static String REST_URL = "/rest/orders";

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        List<Order> all = orderService.getAll(false);
        all.forEach(order ->
                order.add(linkTo(methodOn(OrderRestController.class)
                                .get(order.getId()))
                                .withRel("getOrder"),
                        linkTo(methodOn(OrderRestController.class)
                                .create(null, null, null, null))
                                .withRel("createLicense")));
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Integer id) {
        return orderService.findOrderById(id);
    }

    @PostMapping
    public Order create(@AuthenticationPrincipal AuthUser authUser,
                        @NotNull @RequestParam Integer orderId,
                        @Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateCreate,
                        @RequestParam Boolean isMarketPlace) {
        return orderService.create(authUser, orderId, dateCreate, isMarketPlace);
    }


    @PatchMapping
    public Order modifyOrderFromAvailability(@AuthenticationPrincipal AuthUser authUser,
                                             @NotNull @RequestParam Integer id,
                                             @Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateCreate,
                                             @RequestParam String light,
                                             @RequestParam String additional,
                                             @RequestParam String description,
                                             @RequestParam String contact,
                                             @RequestParam Integer price,
                                             @RequestParam Boolean isMarketPlace,
                                             @RequestParam Boolean isAvailability) {
        return orderService.modifyOrder(authUser, id, dateCreate, light, additional, description, contact, price, isMarketPlace, isAvailability);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void paid(@PathVariable Integer id, @RequestParam Boolean isPaid) {
        orderService.setPaid(id, isPaid);
    }


}
