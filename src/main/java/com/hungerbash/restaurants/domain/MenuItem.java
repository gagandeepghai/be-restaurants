package com.hungerbash.restaurants.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity(name="menu_items")
@Table(name = "menu_items")
@NoArgsConstructor(force=true, access=AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MenuItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private final String name;
	private final BigDecimal price;
	private final Integer spiceLevel;
	private final String badges;
	private final String description;	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="category_id")
	private final MenuCategory category;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="restaurant_id")
	private final Restaurant restaurant;
}
