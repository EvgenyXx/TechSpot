package com.example.TechSpot.dto.user;

import jakarta.validation.constraints.*;

public record UserRequest(

		@NotBlank(message = "First name is required")
		@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
		String firstname,

		@NotBlank(message = "Last name is required")
		@Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
		String lastname,

		@NotBlank(message = "Email is required")
		@Email(message = "Email should be valid")
		String email,

		@NotBlank(message = "Phone number is required")
		@Pattern(regexp = "^\\+7\\d{10}$", message = "Phone must be in format +79123456789")
		String phoneNumber,

		@NotBlank(message = "Password is required")
		@Size(min = 8, message = "Password must be at least 8 characters")
		String password  // 👈 переименуй из hashPassword
) {}