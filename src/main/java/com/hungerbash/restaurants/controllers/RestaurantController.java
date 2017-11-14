package com.hungerbash.restaurants.controllers;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//Spring Imports
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbash.restaurants.dto.ErrorResponse;
import com.hungerbash.restaurants.dto.RestaurantReviewResponse;
import com.hungerbash.restaurants.processors.RestaurantProcessor;


@RequestMapping("/v1/restaurants")
@RestController
public class RestaurantController {
	
	RestaurantProcessor processor;
	
	@Autowired
    public RestaurantController(RestaurantProcessor processor) {
            this.processor = processor;
    }
	
    @GetMapping("/reviews/{id}")
    public ResponseEntity<?> categories(@PathVariable("id") Long restaurantId) {
    	try {
			RestaurantReviewResponse reviews = this.processor.getReviews(restaurantId);
			return ResponseEntity.ok().body(reviews);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }
}
