package com.yannic.rdv.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class RestAuthenticationProvider implements AuthenticationProvider {

	  private static final Logger LOG = LoggerFactory.getLogger(RestAuthenticationProvider.class);
	 
	  @Override
	  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	 
	    authentication.setAuthenticated(doAuthenticate(authentication));
		
	    LOG.info("Creds[{}] Princ[{}] Auth[{}]",  authentication.getCredentials(), authentication.getPrincipal(), authentication.isAuthenticated());
	 
	    if(!authentication.isAuthenticated()) {
	      throw new BadCredentialsException("Unable to authenticate");
	    }
	    return authentication;
	  }
	 
	  @Override
	  public boolean supports(Class<?> authentication) {
	      return UserToken.class.isAssignableFrom(authentication);
	  }
	   
	  private boolean doAuthenticate(Authentication authentication) {
	    return true;
	  }
	 
	}