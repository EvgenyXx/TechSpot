package com.example.TechSpot.modules.reviews.mapper;


import com.example.TechSpot.modules.reviews.dto.ReviewResponse;
import com.example.TechSpot.modules.reviews.entity.Review;
import com.example.TechSpot.modules.users.entity.User;
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
