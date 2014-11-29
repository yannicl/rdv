package com.yannic.rdv.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.Person;
import com.yannic.rdv.data.model.association.AccountPersonAssociation;
import com.yannic.rdv.rest.exception.RestBadRequestException;
import com.yannic.rdv.rest.exception.RestBadRequestException.BadRequestCause;
import com.yannic.rdv.rest.resourcesupport.PersonResource;
import com.yannic.rdv.rest.resourcesupport.PersonSearchResource;
import com.yannic.rdv.rest.resourcesupport.PersonSearchResource.PersonSearchType;

@RestController
@Transactional
@RequestMapping("/api/persons")
public class PersonCtrl extends BaseCtrl {
	
	@RequestMapping(value="", method=RequestMethod.GET)
    public List<PersonSearchResource> listPersonSearch() {
		List<PersonSearchResource> list = new ArrayList<PersonSearchResource>();
		list.add(new PersonSearchResource(PersonSearchType.ACCOUNT));
        return list;
    }
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
    public List<PersonResource> listAllPersons(@RequestParam String by) {
		
		if (PersonSearchType.ACCOUNT.getSearchBy().equals(by)) {
			Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<PersonResource> list = new ArrayList<PersonResource>();
			for(AccountPersonAssociation association : account.getAccountPersonAssociations()) {
				list.add(new PersonResource(association.getPerson()));
			}
			return list;
		}
		
		throw new RestBadRequestException(BadRequestCause.BAD_PARAMETER_VALUE_BY);
        
    }
	
	
	@RequestMapping(value="/{personId}", method=RequestMethod.GET)
	@PreAuthorize("denyAll")
    public PersonResource getPerson(@PathVariable Long personId) {
		
		Person person = personRepository.findOne(personId);
				
        return new PersonResource(person);
    }
	

}
