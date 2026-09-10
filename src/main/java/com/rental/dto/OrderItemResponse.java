package com.rental.dto;

import java.math.BigDecimal;

public record OrderItemResponse (
		Long id,
		Long bookId,
		String bookTitle,
		BigDecimal unitPrice,
		Integer quantity,
		BigDecimal lineTotal
		){

}
