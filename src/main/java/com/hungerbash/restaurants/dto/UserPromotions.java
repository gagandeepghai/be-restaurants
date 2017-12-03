package com.hungerbash.restaurants.dto;

import lombok.Data;

@Data
public class UserPromotions {
	String code;
	Integer percentage;
	String description;
	String restriction;
}
