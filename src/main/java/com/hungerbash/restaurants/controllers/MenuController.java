package com.hungerbash.restaurants.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
//Spring Imports
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	private static final String POD_ROUTING_COOKIE_NAME = "ADPRMPODROUTING";
	MenuProcessor processor;

	@Autowired
	public MenuController(MenuProcessor processor) {
		this.processor = processor;
	}

	@GetMapping("/dummy")
	public void dummy(@RequestParam(name = "prc", required = false) String podRoutingCookie,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean foundPRCCookie = false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (POD_ROUTING_COOKIE_NAME.equals(cookie.getName())) {
					foundPRCCookie = true;
					break;
				}
			}
		}
		if (!foundPRCCookie && podRoutingCookie != null) {
			Cookie cookie = new Cookie(POD_ROUTING_COOKIE_NAME, URLEncoder.encode(podRoutingCookie, "UTF-8"));
			cookie.setPath("/");
			cookie.setHttpOnly(true);
			cookie.setSecure(false);
			response.addCookie(cookie);

			StringBuilder sb = new StringBuilder();
			sb.append(request.getContextPath());
			sb.append(request.getRequestURI().substring(request.getContextPath().length() + 1));
			if (request.getQueryString() != null)
				sb.append("?").append(request.getQueryString());

			response.setHeader("Refresh", "0; URL=https://restaurants-be.herokuapp.com/v1/menu/dummy?prc=RMPOD1");
			return;
		}
		response.setStatus(HttpStatus.OK.value());
		org.apache.commons.io.IOUtils.copy(new ByteArrayInputStream(DummyData.DUMMY.getBytes()), response.getOutputStream());
	}

	@GetMapping("/sm")
	public void testSitemap(@RequestParam("s") String site, @RequestParam("i") String instance,
			@RequestParam(value = "si", required = false) String sitemapId,
			@RequestParam(name = "prc", required = false) String podRoutingCookie, HttpServletResponse response)
			throws ClientProtocolException, IOException {
		System.out.println("Sitemap call for id: " + sitemapId + " site: " + site + " instance: " + instance);

		String baseUrl = "https://recruiting.adp.com/rm/public/third-party-integration/google/sitemap?s=";
		baseUrl += (site + "&i=" + instance + "&prc=" + podRoutingCookie);

		if (sitemapId != null) {
			baseUrl += ("&si=" + sitemapId);
		}
		System.out.println("Calling URL: " + baseUrl);

		RequestConfig config = RequestConfig.custom().build();

		HttpClient httpClientInstance = HttpClientBuilder.create().useSystemProperties().setDefaultRequestConfig(config)
				.build();
		HttpResponse restResponse = httpClientInstance.execute(new HttpGet(baseUrl));
		System.out.println("Response: " + restResponse.getStatusLine());
		System.out.println("Received: " + restResponse.getEntity().getContentLength());
		response.setContentType(ContentType.APPLICATION_XML.toString());
		response.setStatus(HttpStatus.OK.value());
		org.apache.commons.io.IOUtils.copy(restResponse.getEntity().getContent(), response.getOutputStream());
		;
	}

	@GetMapping("/jp")
	public ResponseEntity<?> testJobPosting(@RequestParam(value = "d", required = false) Boolean dummy,
			@RequestParam("r") String reqId, @RequestParam("c") Long clientId,
			@RequestParam(value = "u") String resourceUrl, HttpServletRequest request, HttpServletResponse response)
			throws ClientProtocolException, IOException {

		if (Boolean.TRUE.equals(dummy)) {
			return ResponseEntity.ok(DummyData.DUMMY);
		}

		System.out.println("Sitemap call for c: " + clientId + " r: " + reqId + " u: " + resourceUrl);
		String encoding = "UTF-8";
		String baseUrl = "https://recruiting.adp.com/rm/public/third-party-integration/google/jobposting?r=";
		baseUrl += (reqId + "&c=" + clientId + "&u=" + URLEncoder.encode(resourceUrl, encoding));

		System.out.println("Calling URL: " + baseUrl);

		RequestConfig config = RequestConfig.custom().build();
		HttpClient httpClientInstance = HttpClientBuilder.create().useSystemProperties().setDefaultRequestConfig(config)
				.build();

		HttpGet getRequest = new HttpGet(baseUrl);
		String userAgent = request.getHeader("User-Agent");

		System.out.println("User Agent: " + userAgent);
		if (userAgent.toLowerCase().contains("google")) {
			getRequest.setHeader(HttpHeaders.USER_AGENT,
					"User-Agent: Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
		}

		HttpResponse restResponse = httpClientInstance.execute(getRequest);

		HttpHeaders headers = new HttpHeaders();
		// headers.set(HttpHeaders.CONTENT_TYPE, "application/ld+json");

		String body = IOUtils.toString(restResponse.getEntity().getContent(), encoding);
		String responseStr = "<!DOCTYPE html><html><head></head><body><script type=\"application/ld+json\">" + body
				+ "</script></body></html>";
		return new ResponseEntity<Object>(responseStr, headers, HttpStatus.OK);
	}

	@GetMapping("/google6a32c23c943668d8.html")
	public void google6a32c23c943668d8(HttpServletResponse response) throws ClientProtocolException, IOException {
		ClassLoader classLoader = MenuController.class.getClassLoader();
		org.apache.commons.io.IOUtils.copy(classLoader.getResourceAsStream("google6a32c23c943668d8.html"),
				response.getOutputStream());
		;
	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<?> categories(@PathVariable("id") Long restaurantId) {
		try {
			MenuCategoryResponse categories = this.processor.getMenuCategories(restaurantId);
			return ResponseEntity.ok().body(categories);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." + ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." + ex.getMessage());
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
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." + ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." + ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/items/{category}/{id}")
	public ResponseEntity<?> itemsForCategory(@PathVariable("category") String category,
			@PathVariable("id") Long restaurantId) {
		try {
			MenuItemsResponse categories = this.processor.getMenuItemsByCategories(restaurantId, category);
			return ResponseEntity.ok().body(categories);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." + ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." + ex.getMessage());
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
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." + ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." + ex.getMessage());
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
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." + ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Fetch Failed." + ex.getMessage());
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
			ErrorResponse response = new ErrorResponse("FAILED", "Create Failed." + ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("FAILED", "Create Failed." + ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
