package com.example.TechSpot.mapping;


import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserResponse toResponse(User user);


	@Mapping(target = "id", ignore = true)
	@Mapping(target = "hashPassword", source = "password")
//	@Mapping(target = "createdAt", ignore = true)
//	@Mapping(target = "updatedAt", ignore = true)
	User toCustomer(UserRequest userRequest);
}
