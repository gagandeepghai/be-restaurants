package com.hungerbash.restaurants.controllers;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//Spring Imports
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbash.restaurants.dto.CreateMenuRequest;
import com.hungerbash.restaurants.dto.ErrorResponse;
import com.hungerbash.restaurants.dto.MenuCategoryResponse;
import com.hungerbash.restaurants.dto.MenuItemsResponse;
import com.hungerbash.restaurants.dto.SpecialMenuCategoryResponse;
import com.hungerbash.restaurants.processors.MenuProcessor;


@RequestMapping("/v1/menu")
@RestController
public class MenuController {
	
	MenuProcessor processor;
	
	@Autowired
    public MenuController(MenuProcessor processor) {
            this.processor = processor;
    }
	
    @GetMapping("/categories/{id}")
    public ResponseEntity<?> categories(@PathVariable("id") Long restaurantId) {
    	try {
			MenuCategoryResponse categories = this.processor.getMenuCategories(restaurantId);
			return ResponseEntity.ok().body(categories);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }
    
    @GetMapping("/categories/special/{id}")
    public ResponseEntity<?> specialCategories(@PathVariable("id") Long restaurantId) {
    	try {
			SpecialMenuCategoryResponse categories = this.processor.getSpecialMenuCategories(restaurantId);
			return ResponseEntity.ok().body(categories);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }
    
    @GetMapping("/items/{category}/{id}")
    public ResponseEntity<?> itemsForCategory(@PathVariable("category") String category, @PathVariable("id") Long restaurantId) {
    	try {
    		MenuItemsResponse categories = this.processor.getMenuItemsByCategories(restaurantId, category);
			return ResponseEntity.ok().body(categories);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }
    
    @GetMapping("/takeaway/{id}")
    public ResponseEntity<?> takeaway(@PathVariable("id") Long restaurantId) {
    	try {
    		Object menu = this.processor.getTakeAwayMenu(restaurantId);
			return ResponseEntity.ok().body(menu);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }
    
    @GetMapping("/drinks/{id}")
    public ResponseEntity<?> drinks(@PathVariable("id") Long restaurantId) {
    	try {
    		Object menu = this.processor.getDrinksMenu(restaurantId);
			return ResponseEntity.ok().body(menu);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }
    
    @PostMapping("/create/{id}")
    public ResponseEntity<?> addMenuItems(@PathVariable("id") Long restaurantId, 
    		@RequestBody CreateMenuRequest menuItemsRequest) {
    	try {
			this.processor.createMenu(restaurantId, menuItemsRequest);
			return ResponseEntity.ok().body("Done");
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Create Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Create Failed." +ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }
}