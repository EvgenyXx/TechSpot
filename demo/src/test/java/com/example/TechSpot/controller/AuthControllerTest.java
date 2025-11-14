package com.example.TechSpot.controller;

import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.service.user.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private AuthService authService;

	@Test
	void register_ShouldReturnCreated_WhenValidRequest() throws Exception {
		// given - ВАЛИДНЫЕ данные
		UserRequest request = new UserRequest(
				"evgeny",
				"pavlov",
				"test@gmail.com",
				"+79181339188",
				"qwer1234"
		);

		UserResponse response = new UserResponse(
				UUID.randomUUID(),
				"evgeny",
				"pavlov",
				"test@gmail.com",     // ✅ должен совпадать с request.email
				"+79181339188"         // ✅ должен совпадать с request.phoneNumber
		);

		when(authService.register(any(UserRequest.class))).thenReturn(response);

		// when & then
		mockMvc.perform(post("/api/v1/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.email").value("test@gmail.com")) // ← исправь на gmail.com
				.andExpect(jsonPath("$.phoneNumber").value("+79181339188"));
	}
}