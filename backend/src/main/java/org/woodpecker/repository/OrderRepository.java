package org.woodpecker.repository;

import org.woodpecker.model.order.Order;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;


public interface OrderRepository extends BaseRepository<Order> {
    @EntityGraph(attributePaths = {"manager", "geographyMap", "geographyMap.geographyMapProduction"})
    List<Order> findByCompleted(boolean isCompleted);

}
