package com.hungerbash.restaurants.dto;

import lombok.Data;

@Data
public class AuthResponse {
	Boolean valid;
	String session;
	Boolean isTemporary;
	
	public AuthResponse() {
		this.valid = false;
	}
	
	public AuthResponse(String session, Boolean temp) {
		this.valid = true;
		this.session = session;
		this.isTemporary = temp;
	}
}
