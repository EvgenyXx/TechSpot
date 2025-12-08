package com.example.techspot.modules.products.domain.service.command;

import com.example.techspot.modules.api.user.UserSecurityProvider;
import com.example.techspot.modules.products.application.exception.ProductAccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductOwnershipService {

	private final UserSecurityProvider userSecurityProvider;

	public void checkDeletePermission(UUID userId, UUID ownerId) {
		if (!userSecurityProvider.canDeleteProduct(userId, ownerId)) {
			throw new ProductAccessDeniedException();
		}
	}
}
