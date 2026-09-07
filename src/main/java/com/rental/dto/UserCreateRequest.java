package com.rental.dto;

import com.rental.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest (
	@NotBlank
	@Size(max = 100)
	String firstName,
	
	@NotBlank
	@Size(max = 100)
	String lastName,
	
	@NotBlank
	@Size(max = 150)
	String email,
	
	@NotBlank
	@Size(max = 150, min = 6)
	String password,
	
	@NotNull
	Role role
	) {}
