package org.woodpecker.service.order;

import org.woodpecker.model.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PainterService {

    private final OrderService orderService;

    public List<Order> getPaint() {
        return orderService.getAll(false).stream()
//                .filter(
//                        order ->
//                                order.getIsColorPlywood() != null && (
//                                        order.getIsColorPlywood() ||
//                                                order.getStage().getOrdersOperation() >= Stage.WAITING_PAINT.getOrdersOperation() && order.getStage().getOrdersOperation() <= Stage.PAINTING.getOrdersOperation())
//                )
                .sorted(
                        Comparator.comparing(Order::getMarketPlace).reversed()
//                                .thenComparing(Order::getIsAvailability)
                                .thenComparing(Order::getDateCreate)
                                .thenComparing(Order::getId)
                )
                .toList();
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void setColorPlywood(Integer id) {
        Order order = orderService.findOrderById(id);
//        order.setIsColorPlywood(false);
//        order.setPaintingBegin(LocalDateTime.now());
//        order.setPaintingEnd(LocalDateTime.now());
    }
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void setPainter(Integer id, String namePainter) {
        Order order = orderService.findOrderById(id);
//        order.setNamePainter(namePainter);
//        order.setPaintingBegin(LocalDateTime.now());
//        order.setStage(Stage.PAINTING);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void setStage(Integer id) {
        Order order = orderService.findOrderById(id);
//        order.setPaintingEnd(LocalDateTime.now());
//        order.setStage(Stage.WAITING_GLUE);
    }
}
