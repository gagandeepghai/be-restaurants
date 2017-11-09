package com.hungerbash.restaurants.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbash.restaurants.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

	Restaurant findById(Long restaurantId);

}
