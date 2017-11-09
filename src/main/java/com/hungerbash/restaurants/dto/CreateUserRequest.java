package com.hungerbash.restaurants.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateUserRequest {
	@JsonProperty("email")
	private final String email;
	
	@JsonProperty("name")
	private final String name;
	
	@JsonProperty("password")
	private final String password;
	
	@JsonProperty("deviceId")
	private final String deviceId;
	
	@JsonProperty("facebookHandle")
	private final String facebookHandle;
}
