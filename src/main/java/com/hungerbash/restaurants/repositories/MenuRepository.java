package com.hungerbash.restaurants.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbash.restaurants.domain.MenuCategory;
import com.hungerbash.restaurants.domain.MenuItem;
import com.hungerbash.restaurants.domain.Restaurant;

public interface MenuRepository extends JpaRepository<MenuItem, Long>{

//	@Query("select m from menu_items m where m.restaurant = :r_id and m.category = :c_id")
//	public List<MenuItem> getByRestaurantIdAndCategoryId(@Param("r_id") Restaurant restaurant, @Param("c_id") MenuCategory categoryDao);
	
	List<MenuItem> findByRestaurantAndCategory(Restaurant restaurant, MenuCategory category);

}
