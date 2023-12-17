package com.woodpecker.controller.order;

import com.woodpecker.model.order.Order;
import com.woodpecker.service.order.AvailabilityService;
import com.woodpecker.model.map.GeographyMap;
import com.woodpecker.controller.AuthUser;
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
    public GeographyMap createAvailabilityMap(@AuthenticationPrincipal AuthUser authUser,
                                              @Valid GeographyMap geographyMap,
                                              @RequestParam Boolean isColorPlywood,
                                              @RequestParam String laser,
                                              @RequestParam Integer stage) {
        return availabilityService.createAvailabilityMap(authUser, geographyMap, stage, isColorPlywood, laser);
    }


}
