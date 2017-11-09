package com.hungerbash.restaurants.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ValidateUserRequest {
	@JsonProperty("email")
	private final String email;
	
	@JsonProperty("password")
	private final String password;
	
	@JsonProperty("deviceId")
	private final String deviceId;
	
	@JsonProperty("facebookHandle")
	private final String facebookHandle;
}
