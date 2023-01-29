package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(PaintRestController.REST_URL)
@RestController
@RequiredArgsConstructor
public class PaintRestController {

    public static final String REST_URL = "rest/paints";

    private final OrderService orderService;


    @GetMapping
    public List<OrderMap> paintOrder() {
        return orderService.getPaint();
    }
}
