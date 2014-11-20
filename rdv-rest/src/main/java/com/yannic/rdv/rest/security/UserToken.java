package com.yannic.rdv.rest.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.yannic.rdv.data.model.Account;

public class UserToken extends AbstractAuthenticationToken {
	
	private static final long serialVersionUID = 4324844040350297936L;

	private static final Logger LOG = LoggerFactory.getLogger(UserToken.class);
	
	private static List<SimpleGrantedAuthority> GRANTED_AUTHORITIES = new ArrayList<SimpleGrantedAuthority>();
		
	static {
		GRANTED_AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
    /**
     * User token
     */
    private final String token;
    
    /**
     * User information.
     */
    private final Account principal;

	public UserToken(Account principal, String token) {
		super(GRANTED_AUTHORITIES);
		this.token = token;
		this.principal = principal;
		LOG.debug("UserToken created for [{}]", principal.getUsername());
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
	
	

}