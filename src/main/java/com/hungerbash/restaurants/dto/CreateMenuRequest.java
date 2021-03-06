package com.hungerbash.restaurants.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateMenuRequest {
	@JsonProperty("categories")
	List<CreateCategoryDto> categories = new ArrayList<>();
}
