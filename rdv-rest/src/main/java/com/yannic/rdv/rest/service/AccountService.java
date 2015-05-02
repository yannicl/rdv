package com.yannic.rdv.rest.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.type.LoginMethod;
import com.yannic.rdv.rest.exception.LoginFailedException;
import com.yannic.rdv.rest.exception.LoginFailedException.LoginFailedCause;

@Service
public class AccountService extends BaseService {
	
	public static final int API_KEY_LENGTH = 26;
	private static final int RADIX_32 = 32;
	
	private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);
	
	private SecureRandom secureRandom = new SecureRandom();
	
	
	
	public void performPasswordAuthentication(Account account, String password) {
		
		if (StringUtils.isEmpty(password)) {
			throw new LoginFailedException(LoginFailedCause.LOGIN_FAILED_EXCEPTION_INVALID_USERNAME_OR_PASSWORD);
		}
		
		if (LoginMethod.USERNAME_PASSWORD_MD5.equals(account.getMethod())) {
			performPasswordAuthenticationWithMD5Hash(account, password);
		} else {
			throw new LoginFailedException(LoginFailedCause.LOGIN_FAILED_EXCEPTION_NOT_SUPPORTED_AUTHENTICATION_METHOD);
		}
		
	}
	
	public Account generateApiKey(Account account) {
		
		account.setApiKey(nextApiKey());
		account.setLastLoginDate(new Date());
		
		return account;
		
	}
	
	/**
	 * Generate a random string
	 * 
	 * This works by choosing 130 bits from a cryptographically secure random bit generator, 
	 * and encoding them in base-32. 128 bits is considered to be cryptographically strong, 
	 * but each digit in a base 32 number can encode 5 bits, so 128 is rounded up to the next multiple of 5. 
	 * 
	 * This encoding is compact and efficient, with 5 random bits per character. Compare this to a random UUID, 
	 * which only has 3.4 bits per character in standard layout, and only 122 random bits in total.
	 * 
	 * @see http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string 
	 */
	private String nextApiKey() {
		String random = new BigInteger(130, secureRandom).toString(RADIX_32); 
	    return StringUtils.leftPad(random, API_KEY_LENGTH, '0');
	  }

	private void performPasswordAuthenticationWithMD5Hash(Account account, String password) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(account.getSalt().getBytes(StandardCharsets.UTF_8));
			md.update(account.getUsername().getBytes(StandardCharsets.UTF_8));
			md.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] encodedPassword = md.digest();
			
			if (!(Arrays.equals(hexStringToByteArray(account.getPassword()), encodedPassword))) {
				throw new LoginFailedException(LoginFailedCause.LOGIN_FAILED_EXCEPTION_INVALID_USERNAME_OR_PASSWORD);
			}
			
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Authentication failure", e);
			throw new LoginFailedException(LoginFailedCause.LOGIN_FAILED_EXCEPTION_FAILURE_AUTHENTICATION_METHOD);
		}
		
	}
	
	public static byte[] hexStringToByteArray(String s) {
		
		if (s == null || s.length() < 2 || (s.length() % 2 != 0)) {
			LOG.error("Fail to convert [{0}] to hexString", s);
			throw new LoginFailedException(LoginFailedCause.LOGIN_FAILED_EXCEPTION_FAILURE_AUTHENTICATION_METHOD);
		}
		
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
