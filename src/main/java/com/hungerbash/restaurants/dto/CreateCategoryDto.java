package com.hungerbash.restaurants.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CreateCategoryDto {
	String name;
	String description;
	List<MenuItemDto> items = new ArrayList<>();
}
