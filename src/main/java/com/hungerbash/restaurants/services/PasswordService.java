package com.hungerbash.restaurants.services;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netflix.util.Pair;
import com.hungerbash.restaurants.domain.Password;
import com.hungerbash.restaurants.domain.User;
import com.hungerbash.restaurants.exceptions.UnauthorizedException;
import com.hungerbash.restaurants.repositories.PasswordRepository;
import com.hungerbash.restaurants.utils.PasswordUtils;

@Service
public class PasswordService {
	
	private static final int TEMP_PAS_LEN = 10;
	@Autowired
	PasswordRepository repo;
	

	public void create(User user, String password, boolean temporary) throws Exception {
		Pair<String, String> saltAndHash = PasswordUtils.generateHash(password);
		String salt = saltAndHash.first();
		String hash = saltAndHash.second();
		
		Password passwordDao = new Password(hash, salt, temporary, user);
		passwordDao.setIsActive(true);
		this.repo.save(passwordDao);
	}
	
	public void change(User user, String password) throws Exception {
		deactivatePassword(user);
		this.create(user, password, false);
	}

	private void deactivatePassword(User user) {
		Password passwordDao = this.repo.findByUserAndIsActive(user, true);
		passwordDao.setIsActive(false);
		this.repo.save(passwordDao);
	}

	public boolean validate(User user, String password) throws Exception {
		System.out.println("Received user: " +user);
		Password passwordDao = this.repo.findByUserAndIsActive(user, true);

		String hash = PasswordUtils.getHashForPasswordAndSalt(password, PasswordUtils.hexStringToByteArray(passwordDao.getSalt()));
		
		System.out.println("HASH: " +hash);
		System.out.println("passwordDao.hash: " +passwordDao.getHash());
		if(!hash.equals(passwordDao.getHash())) {
			throw new UnauthorizedException(user.getEmail() + " is not authorized.");
		}
		return passwordDao.getIsTemporary();
	}

	public void generateTemporary(User user, String email) throws Exception {
		deactivatePassword(user);
		
		String temporaryPassword = RandomStringUtils.randomAlphabetic(TEMP_PAS_LEN);
		System.out.println("Generated: " +temporaryPassword);
		this.create(user, temporaryPassword, true);
	}
	
}