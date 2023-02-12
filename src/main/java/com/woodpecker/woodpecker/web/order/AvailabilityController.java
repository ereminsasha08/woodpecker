package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.service.order.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AvailabilityController.REST_URL)
@RequiredArgsConstructor
public class AvailabilityController {
    public static final String REST_URL = "rest/availability";

    private final AvailabilityService availabilityService;


    @GetMapping
    public List<OrderMap> getAvailability(){
       return availabilityService.getAvailability();
    }

    @PatchMapping()
    public OrderMap modifyOrderFromAvailability(@RequestParam Integer id){
        return availabilityService.modifyOrder(id);
    }
}
