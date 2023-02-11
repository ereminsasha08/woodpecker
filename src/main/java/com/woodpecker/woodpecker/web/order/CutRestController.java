package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.service.order.CutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping(CutRestController.REST_URL)
@RestController
@RequiredArgsConstructor
public class CutRestController {
    private final CutService cutService;

    public final static String REST_URL = "rest/cut";

    @GetMapping
    public List<OrderMap> getOrdersForCut() {
        return cutService.sortedForCut();
    }

    @PatchMapping("/{id}")
    public void setLaser(@NotNull @PathVariable Integer id) {
        cutService.setLaser(id);
    }

    @GetMapping("/info/{id}")
    public List<String> infoAboutCut(@PathVariable Integer id) {
        return cutService.infoCut(id);
    }

    @PostMapping("/info/{id}")
    public void updateInfoCut(@PathVariable Integer id, @RequestParam Boolean listIsComplete, @RequestParam Integer numberList) {
        cutService.cutComplete(id, listIsComplete, numberList);
    }

}
