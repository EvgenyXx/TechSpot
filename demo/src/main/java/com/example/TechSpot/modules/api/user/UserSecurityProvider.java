package com.example.TechSpot.modules.api.user;

import com.example.TechSpot.modules.products.entity.Product;
import com.example.TechSpot.modules.users.entity.Role;
import com.example.TechSpot.modules.users.entity.User;

import java.util.UUID;

public interface UserSecurityProvider {

	boolean canDeleteProduct(UUID userId, UUID productOwnerId);


}
