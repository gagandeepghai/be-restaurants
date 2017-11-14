package com.hungerbash.restaurants.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hungerbash.restaurants.domain.Restaurant;
import com.hungerbash.restaurants.domain.Reviews;
import com.hungerbash.restaurants.dto.RestaurantReviewResponse;
import com.hungerbash.restaurants.exceptions.BadRequestException;
import com.hungerbash.restaurants.repositories.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	RestaurantRepository repo;

	public RestaurantReviewResponse getReviews(Long restaurantId) throws BadRequestException {
		Restaurant restaurant = repo.findById(restaurantId);
		if (restaurant == null) {
			throw new BadRequestException("Invalid Restaurant Id: " + restaurantId);
		}

		RestaurantReviewResponse response = new RestaurantReviewResponse();
		List<Reviews> reviews = restaurant.getReview();
		reviews.sort((one, two) -> one.getCreated().compareTo(two.getCreated()));
		response.getReviews().addAll(reviews);
		
		return response;
	}

}
