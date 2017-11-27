package com.hungerbash.restaurants.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbash.restaurants.dto.AuthResponse;
import com.hungerbash.restaurants.dto.CreateUserRequest;
import com.hungerbash.restaurants.dto.CreateUserResponse;
import com.hungerbash.restaurants.dto.PasswordChangeRequest;
import com.hungerbash.restaurants.dto.ValidateUserRequest;
import com.hungerbash.restaurants.exceptions.BadRequestException;
import com.hungerbash.restaurants.exceptions.UnauthorizedException;
import com.hungerbash.restaurants.processors.UserProcessor;

@RequestMapping("/v1/user")
@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	UserProcessor processor;
	
	@PostMapping("/create")
	public ResponseEntity<CreateUserResponse> create(@Valid @RequestBody CreateUserRequest request) {
		CreateUserResponse response = new CreateUserResponse();
		try {
			String sessionId = this.processor.create(request);
			response.setValid(true);
			response.setSession(sessionId);
			
			return ResponseEntity.ok().body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			response.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PostMapping("/auth")
	public ResponseEntity<?> authorize (@Valid @RequestBody ValidateUserRequest request) {
		try {
			AuthResponse response = this.processor.authorize(request);
			return ResponseEntity.ok().body(response);
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse());
		} catch (UnauthorizedException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponse());
		}
	}
	
	@PostMapping("/auth/change")
	public ResponseEntity<?> changePassword (@Valid @RequestBody PasswordChangeRequest request) {
		try {
			AuthResponse response = this.processor.changePassword(request);
			return ResponseEntity.ok().body(response);
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} catch (UnauthorizedException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UnAuthorized");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
	
	@PostMapping("/auth/generate")
	public ResponseEntity<?> generateTemp (@RequestParam("email") String email) {
		try {
			AuthResponse response = this.processor.generateTemporaryPassword(email);
			return ResponseEntity.ok().body(response);
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} catch (UnauthorizedException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UnAuthorized");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
}
