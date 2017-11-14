package com.hungerbash.restaurants.domain;

import java.sql.Timestamp;

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
@Entity(name="reviews")
@Table(name = "reviews")
@NoArgsConstructor(force=true, access=AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Reviews {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private final String name;
	private final Double stars;
	private final String review;
	
	private final Timestamp created;
}