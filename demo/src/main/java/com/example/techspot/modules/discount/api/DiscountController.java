package com.example.techspot.modules.discount.api;

import com.example.techspot.common.constants.ApiPaths;
import com.example.techspot.modules.discount.application.comand.DiscountCreateService;
import com.example.techspot.modules.discount.application.dto.CreateDiscountRequest;
import com.example.techspot.modules.discount.application.dto.DiscountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.techspot.common.constants.SecurityRoles.IS_SELLER_OR_ADMIN;

@RestController
@RequestMapping(ApiPaths.DISCOUNT_BASE)
@RequiredArgsConstructor
@Log4j2
@Tag(
		name = "Discounts",
		description = "API для управления скидками"
)
public class DiscountController {

	private final DiscountCreateService discountCreateService;

	@Operation(
			summary = "Создать скидку",
			description = """
                    Создает новую скидку в системе.
                    
                    **Возможности:**
                    - Указание процента или фиксированной скидки
                    - Привязка к товарам / категориям (если поддерживается)
                    
                    **Доступ:**
                    - Только администратор
                    """
	)
	@ApiResponse(responseCode = "201", description = "Скидка успешно создана")
	@ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
	@ApiResponse(responseCode = "401", description = "Требуется авторизация")
	@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	@PostMapping
	@PreAuthorize(IS_SELLER_OR_ADMIN)
	public ResponseEntity<DiscountResponse> create(
			@RequestBody @Valid CreateDiscountRequest request
	) {
		log.info("POST {} — создание скидки: {}", ApiPaths.DISCOUNT_BASE, request);

		DiscountResponse discountResponse = discountCreateService.create(request);

		log.info("Скидка успешно создана, id={}", discountResponse.id());

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(discountResponse);
	}
}
