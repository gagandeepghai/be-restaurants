package com.hungerbash.restaurants.processors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hungerbash.restaurants.domain.User;
import com.hungerbash.restaurants.dto.AuthResponse;
import com.hungerbash.restaurants.dto.CreateUserRequest;
import com.hungerbash.restaurants.dto.DeviceInfo;
import com.hungerbash.restaurants.dto.PasswordChangeRequest;
import com.hungerbash.restaurants.dto.UserContext;
import com.hungerbash.restaurants.dto.UserProfile;
import com.hungerbash.restaurants.dto.UserPromotions;
import com.hungerbash.restaurants.dto.ValidateUserRequest;
import com.hungerbash.restaurants.exceptions.BadRequestException;
import com.hungerbash.restaurants.exceptions.UnauthorizedException;
import com.hungerbash.restaurants.services.PasswordService;
import com.hungerbash.restaurants.services.UserService;
import com.hungerbash.restaurants.utils.CommunicationUtils;

@Component
public class UserProcessor {

	@Autowired
	UserService userService;
	
	@Autowired
	PasswordService passwordService;
	
	@Autowired
	CommunicationUtils communicationUtils;
	
	public UserContext create(CreateUserRequest request) throws Exception {
		User user = this.userService.findActiveByEmail(request.getEmail());
		if(user != null) {
			throw new BadRequestException("User Already exist with email: " +request.getEmail());
		}
		
		user = new User(request.getEmail(), null, request.getFacebookHandle() != null);
		user.setName(request.getName());
		user.setActive(true);
		this.userService.createUser(user);
		
		if(request.getFacebookHandle() == null) {
			this.createPassword(request, user);
		}
		
		UserContext context = this.userService.createSession(request.getDeviceInfo(), request.getFacebookHandle(), user);
		communicationUtils.sendWelcomeEmail(user.getEmail(), request.getName());
		
		return context;
	}

	private void createPassword(CreateUserRequest request, User user) throws Exception {
		this.passwordService.create(user, request.getPassword(), false);
	}

	public AuthResponse authorize(ValidateUserRequest request) throws Exception {
		User user = validateUser(request.getEmail());
			
		boolean temp = this.passwordService.validate(user, request.getPassword());
		UserContext context = this.userService.createSession(request.getDeviceInfo(), request.getFacebookHandle(), user);
		return new AuthResponse(temp, context);
	}

	public AuthResponse generateTemporaryPassword(String email) throws Exception {
		User user = validateUser(email);
		
		this.passwordService.generateTemporary(user, email);
		return new AuthResponse(true, null);
	}

	public AuthResponse changePassword(PasswordChangeRequest request) throws Exception {
		User user = validateUser(request.getEmail());
		
		this.passwordService.change(user, request.getPassword());
		UserContext context = this.userService.createSession(request.getDeviceInfo(), null, user);
		return new AuthResponse(false, context);
	}

	private User validateUser(String email) throws BadRequestException {
		User user = this.userService.findActiveByEmail(email);
		if(user == null) {
			throw new BadRequestException("User dont exist with email: " +email);
		}
		return user;
	}

	public void processLogout(String email) throws BadRequestException {
		User user = this.userService.findActiveByEmail(email);
		if(user == null) {
			throw new BadRequestException("User dont exist with email: " +email);
		}
		this.userService.logoutUser(user);
	}

	public UserContext getDeviceContext(DeviceInfo device) throws UnauthorizedException {
		return this.userService.getUserContextForDevice(device);
	}

	public List<UserPromotions> promosForUser(String session) {
		return this.userService.getPromos(session);
	}

	public UserProfile getUserProfile(String session) {
		return this.userService.getProfile(session);
	}

	public void saveUserProfile(String session, UserProfile profile) {
		this.userService.saveProfile(session, profile);
	}

}
