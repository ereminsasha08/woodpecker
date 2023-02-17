package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.service.order.AvailabilityService;
import com.woodpecker.woodpecker.web.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(AvailabilityController.REST_URL)
@RequiredArgsConstructor
public class AvailabilityController {
    public static final String REST_URL = "rest/availability";

    private final AvailabilityService availabilityService;


    @GetMapping
    public List<OrderMap> getAvailability() {
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
