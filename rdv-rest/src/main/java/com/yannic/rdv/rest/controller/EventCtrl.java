package com.yannic.rdv.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.Person;
import com.yannic.rdv.data.model.association.AccountPersonAssociation;
import com.yannic.rdv.rest.exception.RestBadRequestException;
import com.yannic.rdv.rest.exception.RestBadRequestException.BadRequestCause;
import com.yannic.rdv.rest.resourcesupport.EventResource;
import com.yannic.rdv.rest.resourcesupport.EventSearchResource;
import com.yannic.rdv.rest.resourcesupport.EventSearchResource.EventSearchType;

@RestController
@Transactional
@RequestMapping("/api/events")
public class EventCtrl extends BaseCtrl {
	
	@RequestMapping(value="", method=RequestMethod.GET)
    public List<EventSearchResource> listEventSearch() {
		List<EventSearchResource> list = new ArrayList<EventSearchResource>();
		Account account = accountRepository.findOne(((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccountId());
		for(AccountPersonAssociation association : account.getAccountPersonAssociations()) {
			list.add(new EventSearchResource(EventSearchType.PERSON, association.getPerson().getPersonId()));
		}
        return list;
    }
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
    public List<EventResource> listAllEvents(@RequestParam String by, @RequestParam Long id) {
		
		if (EventSearchType.PERSON.getSearchBy().equals(by)) {
			Person person = personRepository.findOne(id);
			List<Event> list = eventRepository.findByOrganizer(person.getOrganizer());  
			
			List<EventResource> listRes = new ArrayList<EventResource>();
			for(Event event : list) {
				listRes.add(new EventResource(event));
			}
			return listRes;
		}
		
		throw new RestBadRequestException(BadRequestCause.BAD_PARAMETER_VALUE_BY);
        
    }
	
	@RequestMapping(value="/{eventId}", method=RequestMethod.GET)
    public EventResource getEvent(@PathVariable Long eventId) {
		
		Event event = eventRepository.findOne(eventId);
				
        return new EventResource(event);
    }

}
