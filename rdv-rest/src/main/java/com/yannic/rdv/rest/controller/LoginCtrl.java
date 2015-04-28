package com.yannic.rdv.rest.controller;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.rest.exception.LoginFailedException;
import com.yannic.rdv.rest.exception.LoginFailedException.LoginFailedCause;
import com.yannic.rdv.rest.resourcesupport.AccountResource;

@RestController
@Transactional
@RequestMapping("/login")
public class LoginCtrl extends BaseCtrl {
	
	
	
	@RequestMapping(value="login", method=RequestMethod.POST)
    public AccountResource login(@RequestParam String username, @RequestParam String password) {
		
		Account account = accountRepository.findByUsername(username);
		
		if (account == null) {
			throw new LoginFailedException(LoginFailedCause.LOGIN_FAILED_EXCEPTION_INVALID_USERNAME_OR_PASSWORD);
		}
		
		accountService.performPasswordAuthentication(account, password);
		
		accountService.generateApiKey(account);
		
		return new AccountResource(account);
    }
	
	
	
}
