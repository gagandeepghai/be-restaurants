package com.hungerbash.restaurants.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PasswordChangeRequest {
	@NotNull
	@JsonProperty("email")
	private final String email;
	
	@NotNull
	@JsonProperty("password")
	private final String password;
	
	@NotNull
	@JsonProperty("device")
	private final DeviceInfo deviceInfo;
}
