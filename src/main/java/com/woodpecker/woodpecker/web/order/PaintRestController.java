package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.service.order.PainterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(PaintRestController.REST_URL)
@RestController
@RequiredArgsConstructor
public class PaintRestController {

    public static final String REST_URL = "rest/paints";

    private final PainterService painterService;


    @GetMapping
    public List<OrderMap> paintOrder() {
        return painterService.getPaint();
    }

    @PatchMapping("/{id}")
    public void setIsColorPlywood(@PathVariable Integer id) {
        painterService.setColorPlywood(id);
    }

    @PatchMapping("stage/{id}")
    public void setEndPainting(@PathVariable Integer id) {
        painterService.setStage(id);
    }

    @PostMapping("painter")
    public void setPainter(@RequestParam Integer id, @RequestParam String namePainter) {
        painterService.setPainter(id, namePainter);
    }
}
