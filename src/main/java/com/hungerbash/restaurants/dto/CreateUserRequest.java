package com.hungerbash.restaurants.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateUserRequest {
	@NotNull(message="Email can not be null.")
	@JsonProperty("email")
	private final String email;
	
	@JsonProperty("name")
	private final String name;
	
	@JsonProperty("password")
	private final String password;
	
	@NotNull(message="device can not be null.")
	@JsonProperty("device")
	private final DeviceInfo deviceInfo;
	
	@JsonProperty("facebookHandle")
	private final String facebookHandle;
	
	@JsonProperty("photo")
	private final String pictureUrl;
}
