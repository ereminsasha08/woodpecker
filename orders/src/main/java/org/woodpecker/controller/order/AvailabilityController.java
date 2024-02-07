package org.woodpecker.controller.order;

import org.woodpecker.repository.model.order.Order;
import org.woodpecker.service.order.AvailabilityService;
import org.woodpecker.repository.model.goods.WorldMap;
import org.woodpecker.controller.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(AvailabilityController.REST_URL)
@RequiredArgsConstructor
public class AvailabilityController {
    public static final String REST_URL = "/rest/availability/";

    private final AvailabilityService availabilityService;


    @GetMapping
    public List<Order> getAvailability() {
        return availabilityService.getAvailability();
    }

    @PostMapping
    public WorldMap createAvailabilityMap(@AuthenticationPrincipal AuthUser authUser,
                                          @Valid WorldMap worldMap,
                                          @RequestParam Boolean isColorPlywood,
                                          @RequestParam String laser,
                                          @RequestParam Integer stage) {
        return availabilityService.createAvailabilityMap(authUser, worldMap, stage, isColorPlywood, laser);
    }


}
