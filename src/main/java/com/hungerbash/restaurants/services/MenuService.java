package com.hungerbash.restaurants.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hungerbash.restaurants.domain.MenuCategory;
import com.hungerbash.restaurants.domain.MenuItem;
import com.hungerbash.restaurants.domain.Restaurant;
import com.hungerbash.restaurants.domain.SpecialMenuCategory;
import com.hungerbash.restaurants.dto.CreateCategoryDto;
import com.hungerbash.restaurants.dto.CreateMenuRequest;
import com.hungerbash.restaurants.dto.MenuCategoryDto;
import com.hungerbash.restaurants.dto.MenuCategoryResponse;
import com.hungerbash.restaurants.dto.MenuItemDto;
import com.hungerbash.restaurants.dto.MenuItemsResponse;
import com.hungerbash.restaurants.dto.SpecialMenuCategoryResponse;
import com.hungerbash.restaurants.exceptions.BadRequestException;
import com.hungerbash.restaurants.repositories.MenuCategoryRepository;
import com.hungerbash.restaurants.repositories.MenuRepository;
import com.hungerbash.restaurants.repositories.RestaurantRepository;
import com.hungerbash.restaurants.utils.StaticDataFetcher;

@Service
public class MenuService {

	@Autowired
	MenuRepository menuRepo;
	
	@Autowired
	MenuCategoryRepository menuCategoryRepo;
	
	@Autowired
	StaticDataFetcher staticDataFetcher;

	@Autowired
	RestaurantRepository restaurantRepo;

	public MenuCategoryResponse getCategoriesByRestaurantId(Long restaurantId) throws BadRequestException {
		Restaurant restaurant = restaurantRepo.findById(restaurantId);
		if (restaurant == null) {
			throw new BadRequestException("Invalid Restaurant Id: " + restaurantId);
		}
		MenuCategoryResponse response = new MenuCategoryResponse();
		
		List<MenuCategory> categories = restaurant.getCategories().stream().collect(Collectors.toList());
		categories.sort((one, two) -> one.getId().compareTo(two.getId()));
		categories.forEach(item -> response.getCategories().add(new MenuCategoryDto(item)));
		
		return response;		
	}

	/**
	 * -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
	 * @param restaurantId
	 * @param menuItemsRequest
	 * @throws BadRequestException
	 */
	public void createMenu(Long restaurantId, CreateMenuRequest menuItemsRequest) throws BadRequestException {
		Restaurant restaurant = restaurantRepo.findById(restaurantId);
		if (restaurant == null) {
			throw new BadRequestException("Invalid Restaurant Id: " + restaurantId);
		}

		for (CreateCategoryDto category : menuItemsRequest.getCategories()) {
			String categoryName = category.getName();
			String desc = category.getDescription();
			Integer count = category.getItems().size();

			BigDecimal min = new BigDecimal(Double.MAX_VALUE);
			BigDecimal max = new BigDecimal(0.0);
			
			MenuCategory menuCategory = new MenuCategory(categoryName, desc, count);

			for (MenuItemDto item : category.getItems()) {
				String badges = new Gson().toJson(item.getBadges());
				if(min.compareTo(item.getPrice()) == 1) {
					min = item.getPrice();
				}
				
				if(max.compareTo(item.getPrice()) == -1) {
					max = item.getPrice();
				}
				System.out.println(item);
				MenuItem menuItem = new MenuItem(item.getName(), item.getPrice(), item.getSpiceLevel(), badges,
						item.getDescription(), menuCategory, restaurant);
				this.menuRepo.save(menuItem);
			}

			menuCategory.setMinRange(min);
			menuCategory.setMaxRange(max);
			restaurant.getCategories().add(menuCategory);
			this.restaurantRepo.save(restaurant);
		}
	}

	public MenuItemsResponse getMenuItemsByCategory(Long restaurantId, String category) throws BadRequestException {
		Restaurant restaurant = restaurantRepo.findById(restaurantId);
		if (restaurant == null) {
			throw new BadRequestException("Invalid Restaurant Id: " + restaurantId);
		}
		MenuCategory categoryDao = restaurant.getCategories().stream()
			.filter(item -> item.getName().equals(category))
			.collect(Collectors.toList())
			.get(0);
		
		List<MenuItem> menuItems = this.menuRepo.findByRestaurantAndCategory(restaurant, categoryDao);
		MenuItemsResponse response = new MenuItemsResponse();
		
		menuItems.forEach(item -> response.getItems().add(new MenuItemDto(item)));
		return response;
	}

	public SpecialMenuCategoryResponse getSpecialCategoriesByRestaurantId(Long restaurantId) throws BadRequestException {
		Restaurant restaurant = restaurantRepo.findById(restaurantId);
		if (restaurant == null) {
			throw new BadRequestException("Invalid Restaurant Id: " + restaurantId);
		}
		SpecialMenuCategoryResponse response = new SpecialMenuCategoryResponse();
		
		List<SpecialMenuCategory> categories = restaurant.getSpecialCategoriesMenu().stream().collect(Collectors.toList());
		categories.sort((one, two) -> one.getId().compareTo(two.getId()));
		response.getCategories().addAll(categories);
		
		return response;
	}

	public Object getTakeAwayMenu(Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantRepo.findById(restaurantId);
		if (restaurant == null) {
			throw new BadRequestException("Invalid Restaurant Id: " + restaurantId);
		}
		return staticDataFetcher.fetchTakeaway(restaurant.getName());
	}

	public Object getDrinksMenu(Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantRepo.findById(restaurantId);
		if (restaurant == null) {
			throw new BadRequestException("Invalid Restaurant Id: " + restaurantId);
		}
		return staticDataFetcher.fetchDrinksMenu(restaurant.getName());
	}

}
