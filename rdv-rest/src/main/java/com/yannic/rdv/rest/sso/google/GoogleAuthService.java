package com.yannic.rdv.rest.sso.google;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.yannic.rdv.rest.sso.SsoException;

@Service
public class GoogleAuthService {
	
	/**
	 * Environment used to retrieved the client id and client secret (sensitive information)
	 */
	@Autowired
	private Environment env;

	/**
	 * Please provide a value for the CLIENT_ID constant before proceeding, set this up at https://code.google.com/apis/console/
	 */
	private String CLIENT_ID;
	/**
	 * Please provide a value for the CLIENT_SECRET constant before proceeding, set this up at https://code.google.com/apis/console/
	 */
	private String CLIENT_SECRET;	

	/**
	 * Callback URI that google will redirect to after successful authentication
	 */
	private static final String CALLBACK_URI = "http://localhost:8080/rdv-rest/sso/googlecallback";

	// start google authentication constants
	private static final Collection<String> SCOPE = Arrays.asList("profile;email".split(";"));
	private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	ObjectMapper mapper = new ObjectMapper();
	
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	
	private GoogleAuthorizationCodeFlow flow;
	
	public GoogleAuthService() {}
	
	@PostConstruct
	protected void initializeFlow() throws IOException {
		CLIENT_ID = env.getProperty("GOOGLE_AUTH_CLIENT_ID");
		CLIENT_SECRET = env.getProperty("GOOGLE_AUTH_CLIENT_SECRET");
		
		if (CLIENT_ID == null) throw new IllegalStateException("Client id required. Client id must be set into GOOGLE_AUTH_CLIENT_ID env variable.");
		if (CLIENT_SECRET == null) throw new IllegalStateException("Client secret required. Client secret must be set into GOOGLE_AUTH_CLIENT_SECRET env variable.");
		
		flow =  new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY,
				CLIENT_ID, CLIENT_SECRET, SCOPE).build();
	}
	
	/**
	 * Builds a login URL based on client ID, secret, callback URI, and scope 
	 */
	public String buildLoginUrl() {
		
		final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		
		return url.setRedirectUri(CALLBACK_URI).build();
	}
	
	/**
	 * Expects an Authentication Code, and makes an authenticated request for the user's profile information
	 * @return JSON formatted user profile information
	 * @param authCode authentication code provided by google
	 * @throws SsoException 
	 */
	public GoogleUserProfile getUserProfile(final String authCode) throws SsoException {

		String jsonIdentity;
		try {
			GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
			Credential credential = flow.createAndStoreCredential(response, null);
			HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
			GenericUrl url = new GenericUrl(USER_INFO_URL);
			HttpRequest request = requestFactory.buildGetRequest(url);
			request.getHeaders().setContentType("application/json");
			jsonIdentity = request.execute().parseAsString();
			
			System.out.println(jsonIdentity);
		} catch (IOException e) {
			throw new SsoException("fail to contact google", e);
		}
		
		try {
			return createUserProfile(jsonIdentity);
		} catch (IOException e) {
			throw new SsoException("unable to parse json identity", e);
		}

	}
	
	private GoogleUserProfile createUserProfile(String userInfoJson) throws IOException {
		return mapper.readValue(userInfoJson, GoogleUserProfile.class);
	}

}
