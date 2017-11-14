package com.hungerbash.restaurants.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class StaticDataFetcher {
	private static final String TAKEAWAY_RESOURCE = "takeaway.json";
	private static final String DRINKS_RESOURCE = "drinks.json";
	private static final String DELIM = "/";
	
	public Object fetchTakeaway(String restaurantName) {
		String filePath = restaurantName.toLowerCase() + DELIM + TAKEAWAY_RESOURCE;
		String json = "";
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			json = IOUtils.toString(classLoader.getResourceAsStream(filePath));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new Gson().fromJson(json, Object.class);
	}

	public Object fetchDrinksMenu(String restaurantName) {
		String filePath = restaurantName.toLowerCase() + DELIM + DRINKS_RESOURCE;
		String json = "";
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			json = IOUtils.toString(classLoader.getResourceAsStream(filePath));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new Gson().fromJson(json, Object.class);
	}

}
