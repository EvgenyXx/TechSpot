package com.example.TechSpot.mapping;

import com.example.TechSpot.dto.product.ProductCreateRequest;
import com.example.TechSpot.dto.product.ProductResponse;
import com.example.TechSpot.dto.product.ProductUpdateRequest;
import com.example.TechSpot.entity.Category;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	@Mapping(target = "user", ignore = true)
	@Mapping(target = "id", ignore = true)
	Product toProduct(ProductCreateRequest createRequest);

	// 👇 ИСПРАВЬ ЭТУ СТРОКУ:
	@Mapping(target = "sellerEmail", source = "user.email")
	@Mapping(target = "productCategory", source = "category", qualifiedByName = "categoryToString")
	@Mapping(target = "customerName", source = "user", qualifiedByName = "toFirstname")
	ProductResponse toResponseProduct(Product product);

	@Named("categoryToString")
	default String categoryToString(Category category) {
		return category != null ? category.getName() : null;
	}

	@Named("toFirstname")
	default String toFirstname(User user){
		return user.getFirstname();
	}

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateProduct(ProductUpdateRequest request, @MappingTarget Product product);

	default ProductResponse toResponseProductWithCalculatedFields(Product product) {
		if (product == null) return null;

		// 1. Основной маппинг (вызываем стандартный метод)
		ProductResponse response = toResponseProduct(product);

		// 2. Вычисляемые поля
		Boolean isAvailable = product.getQuantity() > 0;
		String categoryDisplayName = product.getCategory() != null ?
				buildCategoryDisplayName(product.getCategory()) : null;

		// 3. Создаем новый record со ВСЕМИ полями
		return new ProductResponse(
				response.id(),
				response.productName(),
				response.price(),
				response.quantity(),
				response.description(),
				response.productCategory(),
				response.customerName(),
				response.sellerEmail(),
				response.createdAt(),
				response.updatedAt(),
				isAvailable,                    // 👈 вычисленное
				categoryDisplayName             // 👈 вычисленное
		);
	}

		default String buildCategoryDisplayName(Category category) {
			if (category == null) return null;
			return category.getName();
		}
}