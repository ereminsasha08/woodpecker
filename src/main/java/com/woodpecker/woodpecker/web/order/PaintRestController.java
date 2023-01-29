package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}")
    public void setIsColorPlywood(@PathVariable Integer id) {
        orderService.setColorPlywood(id);
    }

    @PostMapping("painter")
    public void setPainter(@RequestParam Integer id, @RequestParam String namePainter) {
        orderService.setPainter(id, namePainter);
    }
}
