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
import lombok.Setter;

@Data
@Entity(name="user")
@Table(name = "user")
@NoArgsConstructor(force=true, access=AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private final String email;
	private final Integer age;
	
	@Setter(AccessLevel.PUBLIC)
	private String name;
	
	@Setter(AccessLevel.PUBLIC)
	private String phone;
	
	@Setter(AccessLevel.PUBLIC)
	private String anniversary;
	
	@Setter(AccessLevel.PUBLIC)
	private String birthday;
	
	@Setter(AccessLevel.PUBLIC)
	private Integer rewards;
	
	@Setter(AccessLevel.PUBLIC)
	private boolean isActive;
}