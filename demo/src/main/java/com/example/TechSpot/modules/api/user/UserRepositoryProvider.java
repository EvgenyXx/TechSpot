package com.example.TechSpot.modules.api.user;

import com.example.TechSpot.modules.users.entity.User;

import java.util.UUID;

public interface UserRepositoryProvider {


	User findById(UUID userId);
}
