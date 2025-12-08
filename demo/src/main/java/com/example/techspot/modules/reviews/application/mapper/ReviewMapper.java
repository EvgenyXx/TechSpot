package com.example.techspot.modules.reviews.application.mapper;


import com.example.techspot.modules.reviews.application.dto.ReviewResponse;
import com.example.techspot.modules.reviews.domain.entity.Review;
import com.example.techspot.modules.users.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReviewMapper {



	@Mapping(target = "userName", source = "user",qualifiedByName = "toUserName")
	@Mapping(target = "userId", source = "user",qualifiedByName = "toUserId")
	ReviewResponse toReviewResponse(Review review);

	@Named("toUserId")
	default UUID toUserId (User user){
		return user.getId();
	}

	@Named("toUserName")
	default String toUserName (User user){
		return user.getFirstname() + " " + user.getLastname();
	}
}
