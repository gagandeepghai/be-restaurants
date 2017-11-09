package com.hungerbash.restaurants.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.hungerbash.restaurants.domain.MenuItem;

import lombok.Data;

@Data
public class MenuItemDto {
	String name;
	BigDecimal price;
	
	@JsonProperty("spicelevel")
	Integer spiceLevel;
	
	List<String> badges = new ArrayList<>();
	String description;
	
	public MenuItemDto() {
		
	}
	
	public MenuItemDto(MenuItem item) {
		this.name = item.getName();
		this.price = item.getPrice();
		this.spiceLevel = item.getSpiceLevel();
		this.badges.addAll(Arrays.asList(new Gson().fromJson(item.getBadges(), String[].class)));
		this.description = item.getDescription();
	}
}
           