package com.thefirstwind.orderservice.repositories;

import com.thefirstwind.orderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
