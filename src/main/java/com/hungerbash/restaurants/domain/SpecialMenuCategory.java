package com.hungerbash.restaurants.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity(name="special_menu_category")
@Table(name = "special_menu_category")
@NoArgsConstructor(force=true, access=AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SpecialMenuCategory {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private final String name;
	private final String description;
	private final String restriction;
}