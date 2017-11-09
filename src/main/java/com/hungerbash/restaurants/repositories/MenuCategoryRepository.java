package com.hungerbash.restaurants.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbash.restaurants.domain.MenuCategory;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long>{

}
