package com.rental.dto;

import com.rental.model.Role;

public record UserResponse (
		Long id,
		String firstName,
		String lastName,
		String email,
		String password,
		Role role,
		boolean enabled
		){
}
