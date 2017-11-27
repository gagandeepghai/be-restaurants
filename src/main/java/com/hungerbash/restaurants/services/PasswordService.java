package com.hungerbash.restaurants.services;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hungerbash.restaurants.domain.Password;
import com.hungerbash.restaurants.domain.User;
import com.hungerbash.restaurants.exceptions.UnauthorizedException;
import com.hungerbash.restaurants.repositories.PasswordRepository;
import com.hungerbash.restaurants.utils.CommunicationUtils;
import com.hungerbash.restaurants.utils.PasswordUtils;
import com.netflix.util.Pair;

@Service
public class PasswordService {
	
	private static final int TEMP_PAS_LEN = 10;
	
	@Autowired
	PasswordRepository repo;
	
	@Autowired
	CommunicationUtils communicationUtils;
	
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
		List<Password> passwordList = this.repo.findByUserAndIsActive(user, true);
		for(Password passwordDao: passwordList) {
			passwordDao.setIsActive(false);
			this.repo.save(passwordDao);
		}
	}

	public boolean validate(User user, String password) throws Exception {
		System.out.println("Received user: " +user);
		List<Password> passwordList = this.repo.findByUserAndIsActive(user, true);
		
		for (Password passwordDao: passwordList) {
			String hash = PasswordUtils.getHashForPasswordAndSalt(password, PasswordUtils.hexStringToByteArray(passwordDao.getSalt()));
			
			System.out.println("HASH: " +hash);
			System.out.println("passwordDao.hash: " +passwordDao.getHash());
			if(hash.equals(passwordDao.getHash())) {
				return passwordDao.getIsTemporary();
			}
		}
		throw new UnauthorizedException(user.getEmail() + " is not authorized.");
	}

	public void generateTemporary(User user, String email) throws Exception {
		deactivateTemporaryPasswords(user);
		String temporaryPassword = RandomStringUtils.randomAlphanumeric(TEMP_PAS_LEN);
		System.out.println("Generated: " +temporaryPassword);
		
		communicationUtils.sendPasswordChangeConfirmation(email, temporaryPassword, user.getName());
		this.create(user, temporaryPassword, true);
	}

	private void deactivateTemporaryPasswords(User user) {
		Password passwordDao = this.repo.findByUserAndIsActiveAndIsTemporary(user, true, true);
		if(passwordDao != null) {
			passwordDao.setIsActive(false);
			this.repo.save(passwordDao);
		}
	}
	
}