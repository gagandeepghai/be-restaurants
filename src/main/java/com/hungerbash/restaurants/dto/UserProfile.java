package com.hungerbash.restaurants.dto;

import com.hungerbash.restaurants.domain.User;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class UserProfile {
	String email;
	String name;
	String phone;
	String anniversary;
	String birthday;
	Integer rewards;
	
	public UserProfile(User user) {
		this.email = user.getEmail();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.anniversary = user.getAnniversary();
		this.birthday = user.getBirthday();
		this.rewards = user.getRewards();
	}
}
