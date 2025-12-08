package com.example.techspot.modules.api.user;



import java.util.UUID;

public interface UserSecurityProvider {

	boolean canDeleteProduct(UUID userId, UUID productOwnerId);


}
