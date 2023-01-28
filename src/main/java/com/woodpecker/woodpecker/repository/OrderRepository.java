package com.woodpecker.woodpecker.repository;

import com.woodpecker.woodpecker.model.OrderMap;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface OrderRepository extends BaseRepository<OrderMap>{
}
