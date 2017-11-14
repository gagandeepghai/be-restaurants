package com.hungerbash.restaurants.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hungerbash.restaurants.dto.CreateMenuRequest;
import com.hungerbash.restaurants.dto.MenuCategoryResponse;
import com.hungerbash.restaurants.dto.MenuItemsResponse;
import com.hungerbash.restaurants.dto.SpecialMenuCategoryResponse;
import com.hungerbash.restaurants.exceptions.BadRequestException;
import com.hungerbash.restaurants.services.MenuService;

@Component
public class MenuProcessor {

	@Autowired
	MenuService menuService;
	
	public MenuCategoryResponse getMenuCategories(Long restaurantId) throws BadRequestException {
		return menuService.getCategoriesByRestaurantId(restaurantId);
	}

	public void addMenuItems(Integer restaurantId, CreateMenuRequest menuItemsRequest) {
		// TODO Auto-generated method stub
		
	}

	public void createMenu(Long restaurantId, CreateMenuRequest menuItemsRequest) throws BadRequestException {
		menuService.createMenu(restaurantId, menuItemsRequest);
	}

	public MenuItemsResponse getMenuItemsByCategories(Long restaurantId, String category) throws BadRequestException {
		return menuService.getMenuItemsByCategory(restaurantId, category);
	}

	public SpecialMenuCategoryResponse getSpecialMenuCategories(Long restaurantId) throws BadRequestException {
		return menuService.getSpecialCategoriesByRestaurantId(restaurantId);
	}

	public Object getTakeAwayMenu(Long restaurantId) throws Exception {
		return menuService.getTakeAwayMenu(restaurantId);
	}

	public Object getDrinksMenu(Long restaurantId) throws Exception {
		return menuService.getDrinksMenu(restaurantId);
	}

}
