package com.hungerbash.restaurants.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hungerbash.restaurants.domain.User;
import com.hungerbash.restaurants.domain.UserSession;
import com.hungerbash.restaurants.dto.DeviceInfo;
import com.hungerbash.restaurants.dto.UserContext;
import com.hungerbash.restaurants.dto.UserProfile;
import com.hungerbash.restaurants.dto.UserPromotions;
import com.hungerbash.restaurants.exceptions.UnauthorizedException;
import com.hungerbash.restaurants.repositories.UserRepository;
import com.hungerbash.restaurants.repositories.UserSessionRepository;
import com.hungerbash.restaurants.utils.StaticDataFetcher;

@Service
public class UserService {

	private static final String PUNJAABS = "punjaabs";

	@Autowired
	UserRepository repo;

	@Autowired
	UserSessionRepository sessionRepo;
	
	@Autowired
	StaticDataFetcher staticDataFetcher;

	public User findNonFacebookActiveByEmail(String email) {
		return this.repo.findByEmailAndIsActiveAndIsFacebook(email, true, false);
	}

	public User findFacebookActiveByEmail(String email) {
		return this.repo.findByEmailAndIsActiveAndIsFacebook(email, true, true);
	}
	
	public User findActiveByEmail(String email) {
		return this.repo.findByEmailAndIsActive(email, true);
	}

	public void createUser(User user) {
		this.repo.save(user);
	}

	public UserContext createSession(DeviceInfo deviceInfo, String facebookHandle, User user) {
		invalidatePreviousSessions(user);

		String sessionId = UUID.randomUUID().toString();
		System.out.println("device: " + deviceInfo);
		UserSession session = new UserSession(sessionId, deviceInfo.getUuid(), deviceInfo.getSerial(),
				deviceInfo.getManufacturer(), facebookHandle);
		session.setUser(user);
		session.setIsActive(true);

		this.sessionRepo.save(session);

		return generateUserContext(user, facebookHandle, sessionId);
	}

	private UserContext generateUserContext(User user, String facebookHandle, String sessionId) {
		UserContext context = new UserContext();
		context.setEmail(user.getEmail());
		context.setIsFacebook(facebookHandle != null);
		context.setName(user.getName());
		context.setSession(sessionId);

		return context;
	}

	private void invalidatePreviousSessions(User user) {
		List<UserSession> activeSessions = this.sessionRepo.findByUserAndIsActive(user, true);
		activeSessions.forEach(session -> {
			session.setIsActive(false);
			this.sessionRepo.save(session);
		});
	}

	public void logoutUser(User user) {
		invalidatePreviousSessions(user);
	}

	public UserContext getUserContextForDevice(DeviceInfo device) throws UnauthorizedException {
		List<UserSession> activeSessions = this.sessionRepo
				.findByIsActiveAndDeviceIdAndDeviceManufacturerAndDeviceSerial(true, device.getUuid(),
						device.getManufacturer(), device.getSerial());
		if (activeSessions.size() != 1) {
			throw new UnauthorizedException("No Active Session");
		}

		UserSession session = activeSessions.get(0);
		UserContext context = this.generateUserContext(session.getUser(), session.getFacebookHandle(), session.getSession());
		return context;
	}

	public List<UserPromotions> getPromos(String sessionId) {
		List<UserPromotions> promotions = new ArrayList<>();
		UserSession session = this.sessionRepo.findBySessionAndIsActive(sessionId, true);
		if(session != null) {
			promotions = this.staticDataFetcher.fetchUserPromotions(PUNJAABS);
		}
		return promotions;
	}

	public UserProfile getProfile(String sessionId) {
		UserSession session = this.sessionRepo.findBySessionAndIsActive(sessionId, true);
		if(session != null) {
			return new UserProfile(session.getUser());
		}
		return new UserProfile();
	}

	public void saveProfile(String sessionId, UserProfile profile) {
		UserSession session = this.sessionRepo.findBySessionAndIsActive(sessionId, true);
		if(session == null) {
			return;
		} else {
			User user = session.getUser();

			if(profile.getName() != null) {
				user.setName(profile.getName());
			}
			
			if(profile.getPhone() != null) {
				user.setPhone(profile.getPhone());
			}
			
			if(profile.getAnniversary() != null) {
				user.setAnniversary(profile.getAnniversary());
			}
			
			if(profile.getBirthday() != null) {
				user.setBirthday(profile.getBirthday());
			}
			this.repo.save(user); 
		}
	}

	public void deleteUser(User user) {
		user.setActive(false);
		this.repo.save(user);
	}
}
