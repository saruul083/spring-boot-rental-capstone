package com.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.model.Book;
import com.rental.model.Cart;
import com.rental.model.CartItem;


public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	List<CartItem> findByCartOrderByIdAsc(Cart cart);
	
	Optional<CartItem> findByCartAndBook(Cart cart, Book book);
}
