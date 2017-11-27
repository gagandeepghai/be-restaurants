package com.hungerbash.restaurants.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hungerbash.restaurants.email.EmailSender;

@Component
public class CommunicationUtils {

	private static final String PASSWORD_STRING_GENERATED = "PASSWORD_STRING_GENERATED";
	private static final String USER_NAME = "USER_NAME";

	@Autowired
	EmailSender emailSender;
	
	private static String WELCOME_EMAIL_SUBJECT = "Welcome to Punjaabs";
	
	private static String WELCOME_EMAIL_BODY = "Hello " + USER_NAME + ",\n \n" +
			"Welcome to Punjaabs. Glad to see you here." + 
			"\n\nFollow Us, Earn Rewards, Get Offers and Have a great time." +
			"\n\n -Punjaabs \n [Powered by: Hungerbash]";
	
	private static String TEMPORARY_PASSWORD_EMAIL_SUBJECT = "Temporary password for Punjaabs";
	private static String TEMPORARY_PASSWORD_EMAIL_BODY = "Hello " + USER_NAME + ",\n \n" +
			"Got to know that you have lost your password. We understand. $#!T happens. " + 
			"Our trained robots are here to help and they have generated a temporary password for you. "+
			"\n\nPlease use following password for login: \t" + PASSWORD_STRING_GENERATED +
			"\n\nAnd remember to change this temporary password as soon as possible."+
			"\n\nHappy to help. Have a great time." +
			"\n\n -Punjaabs \n [Powered by: Hungerbash]";
	
	public void sendWelcomeEmail(String email, String userName) {
		emailSender.sendSimpleMessage(email, WELCOME_EMAIL_SUBJECT, WELCOME_EMAIL_BODY.replace(USER_NAME, userName));
	}

	public void sendPasswordChangeConfirmation(String email, String temporaryPassword, String userName) {
		String messageText = TEMPORARY_PASSWORD_EMAIL_BODY.replace(PASSWORD_STRING_GENERATED, temporaryPassword);
		messageText = messageText.replace(USER_NAME, userName);
		
		emailSender.sendSimpleMessage(email, TEMPORARY_PASSWORD_EMAIL_SUBJECT, messageText);
	}
	
}
