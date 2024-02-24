package com.viavi.stockservice.repository;

import com.viavi.stockservice.entity.OrderEvents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEvents,Integer> {
}
