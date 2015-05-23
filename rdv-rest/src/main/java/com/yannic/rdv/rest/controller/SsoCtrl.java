package com.yannic.rdv.rest.controller;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.type.LoginMethod;
import com.yannic.rdv.rest.sso.SsoException;
import com.yannic.rdv.rest.sso.UserProfile;
import com.yannic.rdv.rest.sso.google.GoogleAuthService;

@RestController
@Transactional
@RequestMapping("/sso")
public class SsoCtrl extends BaseCtrl {
	
	private static final Logger LOG = LoggerFactory.getLogger(SsoCtrl.class);
	
	/**
	 * Login failure, user has not accepted the request from the application on google screen
	 */
	public static String E_SSO_001 = "E_SSO_001";
	/**
	 * Login failure, not able to contact google to retrieve user profile or code from google not valid
	 */
	public static String E_SSO_002 = "E_SSO_002";
	/**
	 * Login failure, the retrieve user profile does not contain valid information
	 */
	public static String E_SSO_003 = "E_SSO_003";
	/**
	 * Login failure, the retrieve user profile is not allowed
	 */
	public static String E_SSO_004 = "E_SSO_004";
	
	
	@Autowired
	GoogleAuthService googleAuthService;
	
	@RequestMapping(value="google", method=RequestMethod.GET)
    public String loginWithGoogle(HttpServletRequest request, HttpServletResponse response) {
		
		InitialContext context;
		try {
			context = new InitialContext();
			System.out.println((String)context.lookup("java:comp/env/GOOGLE_AUTH_CLIENT_SECRET"));
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			response.sendRedirect(googleAuthService.buildLoginUrl(extractBaseUrl(request)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		
		return "redirect";
		
    }
	
	@RequestMapping(value="googlecallback", method=RequestMethod.GET)
    public void callbackGromGoogle(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=false, defaultValue="") String code, @RequestParam(required=false, defaultValue="") String error) throws IOException {
		
		if (StringUtils.isNotEmpty(error)) {
			LOG.info("User rejected the request on google screen");
			response.sendRedirect("/rdv-web/index.html#login?err=" + E_SSO_001);
			return;
		}
		
		UserProfile userProfile;
		
		try {
			userProfile = googleAuthService.getUserProfile(code, extractBaseUrl(request));
		} catch (SsoException e) {
			LOG.warn("Fail to contact google or invalid code", e);
			response.sendRedirect("/rdv-web/index.html#login?err=" + E_SSO_002);
			return;
		}
		
		if (userProfile == null || StringUtils.isEmpty(userProfile.getUsername())) {
			LOG.warn("Invalid user profile");
			response.sendRedirect("/rdv-web/index.html#login?err=" + E_SSO_003);
			return;
		}
		
		Account account;
		try {
			account = accountService.handleSsoAuthentication(userProfile, LoginMethod.SSO_GOOGLE);
		} catch (SsoException e) {
			LOG.warn("Invalid user profile");
			response.sendRedirect("/rdv-web/index.html#login?err=" + E_SSO_004);
			return;
		}
			
		LOG.info("Sso login succeded");
		response.sendRedirect("/rdv-web/index.html#login?code=" + account.getApiKey());
		return;
			
		
    }
	
	private String extractBaseUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length()) + ctx;
		return base;
	}
	
	
	
}
