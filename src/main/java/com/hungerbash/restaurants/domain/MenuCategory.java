package com.hungerbash.restaurants.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Entity(name="menu_category")
@Table(name = "menu_category")
@NoArgsConstructor(force=true, access=AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MenuCategory {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private final String name;
	private final String description;
	private final Integer count;
	
	@Setter(AccessLevel.PUBLIC)
	private BigDecimal minRange;
	
	@Setter(AccessLevel.PUBLIC)
	private BigDecimal maxRange;
}
