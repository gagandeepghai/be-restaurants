package com.hungerbash.restaurants.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbash.restaurants.domain.User;
import com.hungerbash.restaurants.domain.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, Long>{

	List<UserSession> findByUserAndIsActive(User user, boolean bool);

}
