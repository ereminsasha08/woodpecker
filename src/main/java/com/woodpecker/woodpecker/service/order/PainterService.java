package com.woodpecker.woodpecker.service.order;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PainterService {

    private final OrderService orderService;

    public List<OrderMap> getPaint() {
        return orderService.getAll(false).stream()
                .filter(
                        order ->
                                order.getIsColorPlywood() != null && (
                                        order.getIsColorPlywood() ||
                                                order.getStage().getOrdersOperation() >= Stage.WAITING_PAINT.getOrdersOperation() && order.getStage().getOrdersOperation() <= Stage.PAINTING.getOrdersOperation())
                )
                .sorted(
                        Comparator.comparing(OrderMap::getMarketPlace).reversed()
                                .thenComparing(OrderMap::getIsAvailability)
                                .thenComparing(OrderMap::getOrderTerm)
                                .thenComparing(OrderMap::getId)
                )
                .toList();
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void setColorPlywood(Integer id) {
        OrderMap orderMap = orderService.findOrderById(id);
        orderMap.setIsColorPlywood(false);
        orderMap.setPaintingBegin(LocalDateTime.now());
        orderMap.setPaintingEnd(LocalDateTime.now());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void setPainter(Integer id, String namePainter) {
        OrderMap orderMap = orderService.findOrderById(id);
        orderMap.setNamePainter(namePainter);
        orderMap.setPaintingBegin(LocalDateTime.now());
        orderMap.setStage(Stage.PAINTING);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void setStage(Integer id) {
        OrderMap orderMap = orderService.findOrderById(id);
        orderMap.setPaintingEnd(LocalDateTime.now());
        orderMap.setStage(Stage.WAITING_GLUE);
    }
}
