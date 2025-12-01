package com.example.TechSpot.modules.api.user;

import com.example.TechSpot.modules.users.dto.response.UserResponse;
import com.example.TechSpot.modules.users.entity.User;

public interface UserLoginProvider {


	User getUserByEmail(String email);

	UserResponse mapToResponse(User user);
}
