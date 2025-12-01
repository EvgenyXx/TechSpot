package com.example.TechSpot.modules.users.mapper;


import com.example.TechSpot.modules.users.dto.request.UpdateProfileRequest;
import com.example.TechSpot.modules.users.dto.request.UserRequest;
import com.example.TechSpot.modules.users.dto.response.UserResponse;
import com.example.TechSpot.modules.users.entity.User;
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
