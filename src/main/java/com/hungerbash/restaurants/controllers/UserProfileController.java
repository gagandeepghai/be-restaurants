package com.hungerbash.restaurants.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbash.restaurants.dto.UserProfile;
import com.hungerbash.restaurants.dto.UserPromotions;
import com.hungerbash.restaurants.processors.UserProcessor;

@RequestMapping("/v1/profile")
@RestController
@CrossOrigin
public class UserProfileController {
	@Autowired
	UserProcessor processor;
	
	@GetMapping("/promos")
	public ResponseEntity<?> promos (@RequestParam("session") String session) {
		try {
			List<UserPromotions> response = this.processor.promosForUser(session);
			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<UserPromotions>());
		}
	}
	
	@GetMapping()
	public ResponseEntity<?> profile (@RequestParam("session") String session) {
		try {
			UserProfile response = this.processor.getUserProfile(session);
			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserProfile());
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> save (@RequestParam("session") String session, @RequestBody UserProfile profile) {
		try {
			this.processor.saveUserProfile(session, profile);
			return ResponseEntity.ok().body("ok");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
		}
	}
}
