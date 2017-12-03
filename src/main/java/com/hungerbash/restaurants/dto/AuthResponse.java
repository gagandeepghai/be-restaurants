package com.hungerbash.restaurants.dto;

import lombok.Data;

@Data
public class AuthResponse {
	Boolean valid;
	Boolean isTemporary;
	UserContext context;
	
	public AuthResponse(UserContext context) {
		this.valid = false;
	}
	
	public AuthResponse(Boolean temp, UserContext context) {
		this.valid = true;
		this.isTemporary = temp;
		this.context = context;
	}
}
