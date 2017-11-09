package com.hungerbash.restaurants.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbash.restaurants.domain.Password;
import com.hungerbash.restaurants.domain.User;


public interface PasswordRepository extends JpaRepository<Password, Long>{
	Password findByUser(User user);
	Password findByUserAndIsActive(User user, Boolean isActive);
	Password findById(Long id);
}