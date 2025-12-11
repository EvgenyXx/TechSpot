package com.example.techspot.modules.users.api.internal;


import com.example.techspot.modules.users.application.command.UserRegistrationService;
import com.example.techspot.modules.users.application.dto.request.UserRequest;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/internal/users")
public class InternalUserController {

	private final UserRegistrationService userRegistrationService;


	@PostMapping("/register")
	public UserResponse registrationUser(@RequestBody UserRequest userRequest){
		return userRegistrationService.createUser(userRequest);
	}
}
