package com.hungerbash.restaurants.dto;

public class CreateUserResponse {
	Boolean valid;
	String message;
	String session;

	public CreateUserResponse() {
		this.valid = false;
	}
	
	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
}
