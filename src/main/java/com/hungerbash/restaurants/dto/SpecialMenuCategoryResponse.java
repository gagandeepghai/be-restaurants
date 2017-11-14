package com.hungerbash.restaurants.dto;

import java.util.ArrayList;
import java.util.List;

import com.hungerbash.restaurants.domain.SpecialMenuCategory;

import lombok.Data;

@Data
public class SpecialMenuCategoryResponse {
	List<SpecialMenuCategory> categories = new ArrayList<>();
}
