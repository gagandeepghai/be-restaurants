package com.hungerbash.restaurants.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hungerbash.restaurants.domain.User;
import com.hungerbash.restaurants.dto.AuthResponse;
import com.hungerbash.restaurants.dto.CreateUserRequest;
import com.hungerbash.restaurants.dto.PasswordChangeRequest;
import com.hungerbash.restaurants.dto.ValidateUserRequest;
import com.hungerbash.restaurants.exceptions.BadRequestException;
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
	
	public String create(CreateUserRequest request) throws Exception {
		User user = this.userService.findActiveByEmail(request.getEmail());
		if(user != null) {
			throw new BadRequestException("User Already exist with email: " +request.getEmail());
		}
		
		user = new User(request.getEmail(), request.getName(), null);
		user.setActive(true);
		this.userService.createUser(user);
		
		this.createPassword(request, user);
		String session = this.userService.createSession(request.getDeviceInfo(), request.getFacebookHandle(), user);
//		communicationUtils.sendWelcomeEmail(user.getEmail(), request.getName());
		
		return session;
	}

	private void createPassword(CreateUserRequest request, User user) throws Exception {
		this.passwordService.create(user, request.getPassword(), false);
	}

	public AuthResponse authorize(ValidateUserRequest request) throws Exception {
		User user = validateUser(request.getEmail());
			
		boolean temp = this.passwordService.validate(user, request.getPassword());
		String session = this.userService.createSession(request.getDeviceInfo(), request.getFacebookHandle(), user);
		return new AuthResponse(session, temp);
	}

	public AuthResponse generateTemporaryPassword(String email) throws Exception {
		User user = validateUser(email);
		
		this.passwordService.generateTemporary(user, email);
		return new AuthResponse(null, true);
	}

	public AuthResponse changePassword(PasswordChangeRequest request) throws Exception {
		User user = validateUser(request.getEmail());
		
		this.passwordService.change(user, request.getPassword());
		String session = this.userService.createSession(request.getDeviceInfo(), null, user);
		return new AuthResponse(session, false);
	}

	private User validateUser(String email) throws BadRequestException {
		User user = this.userService.findActiveByEmail(email);
		if(user == null) {
			throw new BadRequestException("User dont exist with email: " +email);
		}
		return user;
	}

}
