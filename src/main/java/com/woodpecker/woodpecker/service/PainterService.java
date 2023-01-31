package com.woodpecker.woodpecker.service;

import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PainterService {

    private final OrderService orderService;
    private final OrderRepository orderRepository;


    @Transactional
    public void setColorPlywood(Integer id) {
        OrderMap orderMap = orderService.findOrderById(id);
        orderMap.setIsColorPlywood(false);
        orderMap.setStage(Stage.ЖДЕТ_ПРИКЛЕЙКИ.ordinal());
    }

    @Transactional
    public void setPainter(Integer id, String namePainter) {
        OrderMap orderMap = orderService.findOrderById(id);
        orderMap.setNamePainter(namePainter);
        orderMap.setStage(Stage.КРАСИТСЯ.ordinal());
    }

    public List<OrderMap> getPaint() {
        return orderService.getAll().stream()
                .filter(order -> order.getIsColorPlywood() ||
                        order.getStage() >= Stage.ЖДЕТ_ПОКРАСКИ.ordinal() && order.getStage() <= Stage.КРАСИТСЯ.ordinal())
                .sorted(Comparator.comparing(OrderMap::getMarketPlace).reversed()
                        .thenComparing(OrderMap::getOrderTerm))
                .toList();
    }

    @Transactional
    public void setStage(Integer id) {
        OrderMap orderMap = orderService.findOrderById(id);
        orderMap.setStage(Stage.ЖДЕТ_ПРИКЛЕЙКИ.ordinal());
    }
}
