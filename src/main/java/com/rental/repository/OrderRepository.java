package com.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.dto.Order;
import com.rental.model.User;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByUserOrderByCreatedAtDesc(User user);
	
	Optional<Order> findByIdAndUser(Long id, User user);
}
