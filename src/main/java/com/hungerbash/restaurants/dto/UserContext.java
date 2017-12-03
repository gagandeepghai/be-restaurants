package com.hungerbash.restaurants.dto;

import lombok.Data;

@Data
public class UserContext {
	String email;
	String name;
	Boolean isFacebook;
    String session; 
}
