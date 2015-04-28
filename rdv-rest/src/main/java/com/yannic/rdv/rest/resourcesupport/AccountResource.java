package com.yannic.rdv.rest.resourcesupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.rest.controller.AccountCtrl;
import com.yannic.rdv.rest.controller.EventCtrl;
import com.yannic.rdv.rest.controller.PersonCtrl;
import com.yannic.rdv.rest.resourcesupport.EventSearchResource.EventSearchType;
import com.yannic.rdv.rest.resourcesupport.PersonSearchResource.PersonSearchType;

public class AccountResource extends ResourceSupport {
	
	private final Account account;
	
	public AccountResource(Account account) {
		this.account = new Account();
		this.account.setUsername(account.getUsername());
		this.account.setApiKey(account.getApiKey());
		this.account.setLastLoginDate(account.getLastLoginDate());
		this.add(linkTo(methodOn(AccountCtrl.class).getAccount()).withSelfRel());
		this.add(linkTo(methodOn(EventCtrl.class).listAllEvents(EventSearchType.ACCOUNT.getSearchBy(), 0L)).withRel(EventSearchType.ACCOUNT.getRel()));
		this.add(linkTo(methodOn(PersonCtrl.class).listAllPersons(PersonSearchType.ACCOUNT.getSearchBy())).withRel(PersonSearchType.ACCOUNT.getRel()));
		
	}

	public Account getAccount() {
		return account;
	}
	
}
