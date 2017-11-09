package com.hungerbash.restaurants.domain;

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
import lombok.Setter;

@Data
@Entity(name="password")
@Table(name = "password")
@NoArgsConstructor(force=true, access=AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Password {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private final String hash;
	private final String salt;
	private final Boolean isTemporary;
	
	@Setter(AccessLevel.PUBLIC)
	private Boolean isActive;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private final User user;
}