package com.example.TechSpot.mapping;


import com.example.TechSpot.dto.user.UpdateProfileRequest;
import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserResponse toResponse(User user);


	@Mapping(target = "id", ignore = true)
	@Mapping(target = "hashPassword", source = "password")
	User toCustomer(UserRequest userRequest);


	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateUser(UpdateProfileRequest request, @MappingTarget User user);
}
