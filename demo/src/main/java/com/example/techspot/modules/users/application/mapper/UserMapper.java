package com.example.techspot.modules.users.application.mapper;


import com.example.techspot.modules.users.application.dto.request.UpdateProfileRequest;
import com.example.techspot.modules.users.application.dto.request.UserRequest;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import com.example.techspot.modules.users.domain.entity.User;
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
