package com.example.TechSpot.controller.auth;


import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.service.user.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPaths.AUTH_BASE)
@Log4j2
public class AuthController {

	private final AuthService authService;


	//TODO ДОБАВИТЬ СВАГЕР ДОКУМЕНТАЦИЮ
	@PostMapping(ApiPaths.REGISTER)
	public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest request) {

		log.info(" HTTP POST api/auth/register {}", request.email());

		UserResponse userResponseRegister = authService.register(request);
		log.info("HTTP 201 успешная регистрация {}", request.email());

		return ResponseEntity.
				status(HttpStatus.CREATED)
				.body(userResponseRegister);

	}
}
