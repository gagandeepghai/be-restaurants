package com.hungerbash.restaurants.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbash.restaurants.domain.User;


public interface UserRepository extends  JpaRepository<User, Long> {
	User findById(Long id);
	User findByEmail(String email);
	User findByEmailAndIsActive(String email, boolean isActive);
}