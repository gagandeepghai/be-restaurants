package com.hungerbash.restaurants.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MenuItemsResponse {
	private List<MenuItemDto> items = new ArrayList<>();
}
