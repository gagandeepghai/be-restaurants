package com.hungerbash.restaurants.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DeviceInfo {
	@NotNull
	String uuid;
	
	@NotNull
	String manufacturer;
	
	@NotNull
	String serial;
}
