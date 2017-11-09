package com.hungerbash.restaurants.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ErrorResponse {
	@JsonProperty
	private final String code;
	
	@JsonProperty
	private final String message;
}