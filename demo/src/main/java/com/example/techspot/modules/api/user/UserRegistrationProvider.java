package com.example.techspot.modules.api.user;

import com.example.techspot.modules.users.application.dto.request.UserRequest;
import com.example.techspot.modules.users.application.dto.response.UserResponse;


public interface UserRegistrationProvider {



	UserResponse createUser(UserRequest request);


}
