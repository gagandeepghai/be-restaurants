package com.hungerbash.restaurants.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hungerbash.restaurants.dto.RestaurantReviewResponse;
import com.hungerbash.restaurants.exceptions.BadRequestException;
import com.hungerbash.restaurants.services.RestaurantService;

@Component
public class RestaurantProcessor {
	
	@Autowired
	RestaurantService service;

	public RestaurantReviewResponse getReviews(Long restaurantId) throws BadRequestException {
		return this.service.getReviews(restaurantId);				
	}

}
