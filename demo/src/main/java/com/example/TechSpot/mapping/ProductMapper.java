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
}