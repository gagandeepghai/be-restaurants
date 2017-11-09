package com.hungerbash.restaurants.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hungerbash.restaurants.domain.User;
import com.hungerbash.restaurants.domain.UserSession;
import com.hungerbash.restaurants.repositories.UserRepository;
import com.hungerbash.restaurants.repositories.UserSessionRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	UserSessionRepository sessionRepo;

	public User findActiveByEmail(String email) {
		return this.repo.findByEmailAndIsActive(email, true);
	}

	public void createUser(User user) {
		this.repo.save(user);
	}

	public String createSession(String deviceId, String facebookHandle, User user) {
		invalidatePreviousSessions(user);
		
		String sessionId = UUID.randomUUID().toString();
		UserSession session = new UserSession(sessionId, deviceId, facebookHandle);
		session.setUser(user);
		session.setIsActive(true);
		
		this.sessionRepo.save(session);
		return sessionId;
	}

	private void invalidatePreviousSessions(User user) {
		List<UserSession> activeSessions = this.sessionRepo.findByUserAndIsActive(user, true);
		activeSessions.forEach(session -> {
			session.setIsActive(false);
			this.sessionRepo.save(session);
		});
	}

}
