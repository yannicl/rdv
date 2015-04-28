package com.yannic.rdv.rest.controller;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.rest.resourcesupport.AccountResource;

@RestController
@Transactional
@RequestMapping("/api/account")
public class AccountCtrl extends BaseCtrl {
	
	@RequestMapping(value="", method=RequestMethod.GET)
    public AccountResource getAccount() {
		
		Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
        return new AccountResource(account);
    }
	
}
