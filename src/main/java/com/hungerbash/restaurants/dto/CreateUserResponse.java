package com.hungerbash.restaurants.dto;

import lombok.Data;

@Data
public class CreateUserResponse {
	Boolean valid;
	String message;
	UserContext context;
}
