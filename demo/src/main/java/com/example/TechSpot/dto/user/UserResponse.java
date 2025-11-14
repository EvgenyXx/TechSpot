package com.example.TechSpot.dto.user;



import java.util.UUID;

public record UserResponse(

		UUID id,

		String firstname,


		String lastname,


		String email,


		String phoneNumber

) {
}
