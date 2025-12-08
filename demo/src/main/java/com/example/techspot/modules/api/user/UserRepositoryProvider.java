package com.example.techspot.modules.api.user;

import com.example.techspot.modules.users.domain.entity.User;

import java.util.UUID;

public interface UserRepositoryProvider {


	User findById(UUID userId);
}
