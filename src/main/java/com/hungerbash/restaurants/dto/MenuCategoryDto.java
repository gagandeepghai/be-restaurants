package com.hungerbash.restaurants.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hungerbash.restaurants.domain.MenuCategory;

import lombok.Data;

@Data
public class MenuCategoryDto {
	
	private String name;
	
	@JsonProperty("desc")
	private String description;
	
	private Integer count;
	private BigDecimal minRange;
	private BigDecimal maxRange;

	public MenuCategoryDto(MenuCategory category) {
		this.name = category.getName();
		this.description = category.getDescription();
		this.count = category.getCount();
		this.minRange = category.getMinRange();
		this.maxRange = category.getMaxRange();
	}
	
}
