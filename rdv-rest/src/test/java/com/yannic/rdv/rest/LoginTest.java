package com.yannic.rdv.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.util.NestedServletException;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.type.LoginMethod;
import com.yannic.rdv.rest.exception.LoginFailedException;
import com.yannic.rdv.rest.service.AccountService;

public class LoginTest extends AbstractRestControllerTest {
	
	public static final String USERNAME = "Yannic";
	public static final String PASSWD = "MotdePasse";
	public static final String WRONG_PASSWD = "MotdePass=";
	
	@Before
	public void setupRepositories() {
		Account account = getSingleAccountTest();
		account.setSalt("ABCD");
		account.setUsername("Yannic");
		String hash = "b6f4343848076f381c93be80ff053e94";
		account.setPassword(hash); // md5("ABCDYannicMotdePasse")
		account.setMethod(LoginMethod.USERNAME_PASSWORD_MD5);
		
		when(accountRepository.findByUsername(USERNAME)).thenReturn(account);
	}
	
	
	@Test
	public void should_get_json_account_when_successful_login() throws Exception {
		mockMvc.perform(post("/login/login")
		.param("username", USERNAME)
		.param("password", PASSWD)
		)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.HAL_JSON))
        .andExpect(jsonPath("$.account.username").value(USERNAME));
	}
	
	@Test(expected=NestedServletException.class)
	public void should_get_http_code_422_account_when_failed_login() throws Exception {
		mockMvc.perform(post("/login/login")
		.param("username", USERNAME)
		.param("password", WRONG_PASSWD)
		);
		// the 422 status code is managed by the controller advice and out of test scope here.
	}
	
	@Test(expected=LoginFailedException.class)
	public void should_get_exception_when_stored_hash_null() {
		AccountService.hexStringToByteArray(null);
	}
	
	@Test(expected=LoginFailedException.class)
	public void should_get_exception_when_stored_hash_empty() {
		AccountService.hexStringToByteArray("");
	}
	
	@Test(expected=LoginFailedException.class)
	public void should_get_exception_when_stored_hash_invalid() {
		AccountService.hexStringToByteArray("0");
	}
	
	@Test(expected=LoginFailedException.class)
	public void should_get_exception_when_stored_hash_invalid_2() {
		AccountService.hexStringToByteArray("000");
	}
	
	@Test()
	public void should_get_default_when_stored_hash_invalid_3() {
		Assert.assertArrayEquals(new byte[] {(byte) 0xEF}, AccountService.hexStringToByteArray("=="));
	}
	

}
