package com.hungerbash.restaurants.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PasswordChangeRequest {
	@JsonProperty("email")
	private final String email;
	
	@JsonProperty("password")
	private final String password;
	
	@JsonProperty("deviceId")
	private final String deviceId;
}
