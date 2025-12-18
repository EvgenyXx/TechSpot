package com.example.techspot.modules.api.user;

import com.example.techspot.modules.users.application.dto.response.UserResponse;
import com.example.techspot.modules.users.domain.entity.User;

public interface UserLoginProvider {


	User getUserByEmail(String email);

	UserResponse mapToResponse(User user);
}
