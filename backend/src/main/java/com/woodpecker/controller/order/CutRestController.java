package com.woodpecker.controller.order;

import com.woodpecker.model.order.Order;
import com.woodpecker.service.order.CutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@RequestMapping(CutRestController.REST_URL)
@RestController
@RequiredArgsConstructor
public class CutRestController {
    private final CutService cutService;

    public final static String REST_URL =  "/rest/cut/";

    @GetMapping
    public List<Order> getOrdersForCut() {
        return cutService.sortedForCut();
    }

    @PatchMapping
    public void setLaserAndList(@NotNull @RequestParam Integer orderId,
                                @NotNull @RequestParam Boolean isColorPlywood,
                                @NotNull @RequestParam Boolean isWoodStain) {
        cutService.setLaserAndList(orderId, isColorPlywood, isWoodStain);
    }

    @PostMapping
    public void changeLaser(@RequestParam Integer id, @RequestParam String laserName){
        cutService.changeLaser(id, laserName);
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
