package com.yannic.rdv.rest.sso.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yannic.rdv.rest.sso.UserProfile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserProfile implements UserProfile {
	
	private String email;
	
	@Override
	public String getUsername() {
		return email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
