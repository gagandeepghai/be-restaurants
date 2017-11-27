package com.hungerbash.restaurants.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbash.restaurants.domain.Password;
import com.hungerbash.restaurants.domain.User;


public interface PasswordRepository extends JpaRepository<Password, Long>{
	Password findByUser(User user);
	List<Password> findByUserAndIsActive(User user, Boolean isActive);
	Password findByUserAndIsActiveAndIsTemporary(User user, Boolean isActive, Boolean isTemp);
	Password findById(Long id);
}