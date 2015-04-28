package com.yannic.rdv.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.repository.AccountRepository;
import com.yannic.rdv.rest.exception.RestAccessDeniedException;
import com.yannic.rdv.rest.exception.RestAccessDeniedException.AccessDeniedCause;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger LOG = LoggerFactory.getLogger(RestAuthenticationFilter.class);
	
	public static final String HEADER_SECURITY_API_KEY = "X-API-KEY"; 
	public static final int API_KEY_LENGTH = 26;
	private ObjectWriter accessDeniedCauseObjectWriter;
	
	private AccountRepository accountRepository;

	public RestAuthenticationFilter(RequestMatcher requestMatcher) {
		super(requestMatcher);
		ObjectMapper objectMapper = new ObjectMapper();
		accessDeniedCauseObjectWriter = objectMapper.writerWithType(AccessDeniedCause.class);
	}
	
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try {
			super.doFilter(req, res, chain);
		} catch(RestAccessDeniedException e) {
			HttpServletResponse response = (HttpServletResponse) res;
			response.setStatus(e.getAccessDeniedCause().getStatus());
			response.setContentType("application/json");
			accessDeniedCauseObjectWriter.writeValue(response.getOutputStream(), e.getAccessDeniedCause());
			return;
		}
	}



	/**
	 * Attempt to authenticate request - basically just pass over to another method to authenticate request headers 
	 */
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		LOG.info("attemptAuthentication... for " + ((HttpServletRequest) request).getRequestURI());
		String apiKey = request.getHeader(HEADER_SECURITY_API_KEY);

		if (apiKey == null) {
			throw new RestAccessDeniedException(AccessDeniedCause.NO_KEY);
		}
		if (apiKey.length() != API_KEY_LENGTH) {
			throw new RestAccessDeniedException(AccessDeniedCause.ILLEGAL_KEY);
		}

		AbstractAuthenticationToken userAuthenticationToken = authUserByApiKey(apiKey);

		Authentication authenticate = getAuthenticationManager().authenticate(userAuthenticationToken);

		return authenticate;
		
	}
	
	@Override
    protected final void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);

        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
        
        chain.doFilter(request, response);
    }


	


	/**
	 * authenticate the user based on the provided api key
	 * @return
	 */
	private AbstractAuthenticationToken authUserByApiKey(String apiKey) {
		
		Account credential = accountRepository.findByApiKey(apiKey);
		
		if (credential == null) {
			throw new RestAccessDeniedException(AccessDeniedCause.WRONG_KEY);
		}
		
		return new UserToken(credential, apiKey);
		
	}

	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

}
