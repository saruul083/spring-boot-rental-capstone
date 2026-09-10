package com.rental.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rental.dto.CheckoutResponse;
import com.rental.dto.Order;
import com.rental.dto.OrderItem;
import com.rental.dto.OrderStatus;
import com.rental.exception.BusinessRuleException;
import com.rental.model.Book;
import com.rental.model.Cart;
import com.rental.model.CartItem;
import com.rental.model.User;
import com.rental.repository.BookRepository;
import com.rental.repository.CartItemRepository;
import com.rental.repository.CartRepository;
import com.rental.repository.OrderItemRepository;
import com.rental.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class CheckoutService {
	private final CurrentUserService currentUserService;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final OrderItemRepository orderItemRepository;
	private final OrderRepository orderRepository;
	private final BookRepository bookRepository;
	
	public CheckoutService(CurrentUserService currentUserService, CartRepository cartRepository,
			CartItemRepository cartItemRepository, OrderItemRepository orderItemRepository,
			OrderRepository orderRepository, BookRepository bookRepository) {
		super();
		this.currentUserService = currentUserService;
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.orderItemRepository = orderItemRepository;
		this.orderRepository = orderRepository;
		this.bookRepository = bookRepository;
	}
	
	@Transactional
	public CheckoutResponse checkout() {
		User user = currentUserService.getCurrentUser();
		
		Cart cart = cartRepository.findByUser(user).orElseThrow(() -> {
			throw new BusinessRuleException("Cart does not exist");
		});
		
		List<CartItem> cartItems = cartItemRepository.findByCartOrderByIdAsc(cart);
		if (cartItems.isEmpty()) {
			throw new BusinessRuleException("Cart is empty");
		}
		for (CartItem item : cartItems) {
			Book book = item.getBook();
			if (!book.isActive()) {
				throw new BusinessRuleException("Book is not available " + book.getTitle());
			}
			
			if (item.getQuantity() > book.getStockQuantity()) {
				throw new BusinessRuleException("Not enough book in stock " + book.getTitle());
			}
		}
		/*
		 * BigDecimal total = BigDecimal.ZERO; for (CartItem item: cartItems) { Book
		 * book = item.getBook(); BigDecimal totalAmountPrice =
		 * BigDecimal.valueOf(item.getQuantity()).multiply(book.getPrice());
		 * total.add(totalAmountPrice); }
		 */
		
		BigDecimal totalAmount = cartItems.stream().map(item -> item.getBook().getPrice()
				.multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		Order order = new Order();
		order.setUser(user);
		order.setStatus(OrderStatus.PENDING);
		order.setTotalAmount(totalAmount);
		order.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder = orderRepository.save(order);
		
		for (CartItem item: cartItems) {
			Book book = item.getBook();
			BigDecimal unitPrice = book.getPrice();
			BigDecimal totalLine = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
			
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(savedOrder);
			orderItem.setBook(book);
			orderItem.setBookTitle(book.getTitle());
			orderItem.setUnitPrice(unitPrice);
			orderItem.setQuantity(item.getQuantity());
			orderItem.setLineTotal(totalLine);
			
			orderItemRepository.save(orderItem);
			
			// 5. reduce book stock
			book.setStockQuantity(book.getStockQuantity() - item.getQuantity());
			bookRepository.save(book);
		}
		
		cartItemRepository.deleteAll(cartItems);
		
		return new CheckoutResponse(savedOrder.getId(), 
				savedOrder.getStatus().name(),
				savedOrder.getTotalAmount(),
				savedOrder.getCreatedAt())
				;
	}
}
