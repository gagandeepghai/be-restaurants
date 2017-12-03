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
@Entity(name="user_session")
@Table(name = "user_session")
@NoArgsConstructor(force=true, access=AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserSession {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private final String session;
	private final String deviceId;
	private final String deviceSerial;
	private final String deviceManufacturer;
	private final String facebookHandle;
	
	@Setter(AccessLevel.PUBLIC)
	private Boolean isActive;
	
	@Setter(AccessLevel.PUBLIC)
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
}
