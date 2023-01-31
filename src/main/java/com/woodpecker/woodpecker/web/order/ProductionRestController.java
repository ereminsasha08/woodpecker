package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.service.order.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ProductionRestController.REST_URL)
@RequiredArgsConstructor
public class ProductionRestController {
    public final static String REST_URL = "rest/production";

    private final ProductionService productionService;

    @GetMapping
    public List<OrderMap> getOrderForProduction() {
        return productionService.getOrderForProduction();
    }


    @PatchMapping("/{id}")
    public void updateCondition(@PathVariable Integer id, @RequestParam Integer conditionMap) {
        productionService.updateConditionOrder(id, conditionMap);
    }
}
