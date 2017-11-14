package com.hungerbash.restaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbash.restaurants.dto.AuthResponse;
import com.hungerbash.restaurants.dto.CreateUserRequest;
import com.hungerbash.restaurants.dto.PasswordChangeRequest;
import com.hungerbash.restaurants.dto.ValidateUserRequest;
import com.hungerbash.restaurants.exceptions.BadRequestException;
import com.hungerbash.restaurants.exceptions.UnauthorizedException;
import com.hungerbash.restaurants.processors.UserProcessor;

@RequestMapping("/v1/user")
@RestController
public class UserController {
	
	@Autowired
	UserProcessor processor;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CreateUserRequest request) {
		try {
			String sessionId = this.processor.create(request);
			return ResponseEntity.ok().body(sessionId);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
	
	@PostMapping("/auth")
	public ResponseEntity<?> authorize (@RequestBody ValidateUserRequest request) {
		try {
			AuthResponse response = this.processor.authorize(request);
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
	
	@PostMapping("/auth/change")
	public ResponseEntity<?> changePassword (@RequestBody PasswordChangeRequest request) {
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