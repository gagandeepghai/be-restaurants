package com.hungerbash.restaurants.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force=true, access=AccessLevel.PUBLIC)
public class AuthResponse {
	String session;
	Boolean isTemporary;
	
	public AuthResponse(String session, Boolean temp) {
		this.session = session;
		this.isTemporary = temp;
	}
}
