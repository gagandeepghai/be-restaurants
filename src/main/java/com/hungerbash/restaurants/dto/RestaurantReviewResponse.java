package com.hungerbash.restaurants.dto;

import java.util.ArrayList;
import java.util.List;

import com.hungerbash.restaurants.domain.Reviews;

import lombok.Data;

@Data
public class RestaurantReviewResponse {
	List<Reviews> reviews = new ArrayList<>(); 
}
