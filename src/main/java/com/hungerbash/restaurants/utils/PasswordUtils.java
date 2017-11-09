package com.hungerbash.restaurants.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.xml.bind.DatatypeConverter;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.netflix.util.Pair;

public class PasswordUtils {

	private static final String SHA1PRNG = "SHA1PRNG";
	private static final String PBKDF2_WITH_HMAC_SHA1 = "PBKDF2WithHmacSHA1";
	private static final Integer ITERATIONS = 1000;
	private static final Integer KEY_LEN = 64 * 8;

	public static Pair<String, String> generateHash(String password) throws Exception {	
		byte[] salt = getSalt();

		String hash = getHashForPasswordAndSalt(password, salt);

		return new Pair<String, String>(toHex(salt), hash);
	}

	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance(SHA1PRNG);
		byte[] salt = new byte[16];
		sr.nextBytes(salt);

		return salt;
	}

	private static String toHex(byte[] array) throws NoSuchAlgorithmException {
		return DatatypeConverter.printHexBinary(array);
	}
	
	public static byte[] hexStringToByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}
	
	public static String getHashForPasswordAndSalt(String password, byte[] salt) throws Exception {
		System.out.println("Received password: " +password);
		System.out.println("Received salt: " +salt.toString());

		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LEN);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_WITH_HMAC_SHA1);
		byte[] hash = skf.generateSecret(spec).getEncoded();
		
		return toHex(hash);
	}
}