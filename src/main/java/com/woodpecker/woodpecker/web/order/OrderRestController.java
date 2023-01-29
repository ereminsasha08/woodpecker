package com.woodpecker.woodpecker.web.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping(OrderRestController.REST_URL)
@RestController
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    public final static String REST_URL = "rest/orders";
    @GetMapping
    public List<OrderMap> all() {
        return orderService.getOrdersWithSortedCut();    }

    @PostMapping
    public OrderMap create(@NotNull @RequestParam Integer orderId,
                           @Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime orderTerm,
                           @RequestParam Boolean isMarketPlace,
                           @RequestParam Boolean isColorPlywood) {
        return orderService.create(orderId, orderTerm, isMarketPlace, isColorPlywood);
    }

    @GetMapping("/{id}")
    public OrderMap get(@PathVariable Integer id) {
        return orderService.findOrderById(id);
    }
    @GetMapping("/cut")
    public List<OrderMap> getCut(){
        return orderService.getOrdersWithSortedCut();
    }

    @PatchMapping("/{id}")
    public void setLaser(@NotNull @PathVariable Integer id){
        orderService.setLaser(id);
    }


    @GetMapping("/infocut/{id}")
    public List<String> infoCut(@PathVariable Integer id){
       return orderService.infoCut(id);
    }

    @PostMapping("/infocut/{id}")
    public void infoCut(@PathVariable Integer id, @RequestParam Boolean listIsComplete, @RequestParam Integer numberList){
         orderService.cutComplete(id, listIsComplete, numberList);
    }
}
