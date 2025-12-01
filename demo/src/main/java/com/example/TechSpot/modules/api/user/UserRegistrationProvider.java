package com.example.TechSpot.modules.api.user;

import com.example.TechSpot.modules.users.dto.request.UserRequest;
import com.example.TechSpot.modules.users.dto.response.UserResponse;


public interface UserRegistrationProvider {



	UserResponse createUser(UserRequest request);


}
