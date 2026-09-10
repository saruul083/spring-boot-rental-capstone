package com.rental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.dto.Order;
import com.rental.dto.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	
	List<OrderItem> findByOrderOrderByIdAsc(Order order);
}
