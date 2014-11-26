package com.yannic.rdv.rest.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
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
import com.yannic.rdv.data.model.type.EventStatus;
import com.yannic.rdv.rest.exception.RestBadRequestException;
import com.yannic.rdv.rest.exception.RestBadRequestException.BadRequestCause;
import com.yannic.rdv.rest.formatter.EventBookingCodeFormatter;
import com.yannic.rdv.rest.requestparam.EventBookingCode;
import com.yannic.rdv.rest.resourcesupport.EventResource;
import com.yannic.rdv.rest.resourcesupport.EventSearchResource;
import com.yannic.rdv.rest.resourcesupport.EventSearchResource.EventSearchType;

@RestController
@Transactional
@RequestMapping("/api/events")
public class EventCtrl extends BaseCtrl {
	
	@Autowired
	EventBookingCodeFormatter eventBookingCodeFormatter;
	
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
			List<Event> list = eventRepository.findByOrganizerAndStatusAndStartDateGreaterThan(
					person.getOrganizer(),
					EventStatus.AVAILABLE,
					new Date(),
					new PageRequest(0, 5)
					);  
			
			List<EventResource> listRes = new ArrayList<EventResource>();
			for(Event event : list) {
				EventResource eventResource = new EventResource(event, person);
				listRes.add(eventResource);
				if (event.getStatus().equals(EventStatus.AVAILABLE)) {
					EventBookingCode code = new EventBookingCode();
					code.setEventBookingCodeCreationDate(new Date());
					code.setEventId(event.getEventId());
					code.setPersonId(person.getPersonId());
					eventResource.addBookingCode(eventBookingCodeFormatter.print(code, LocaleContextHolder.getLocale()));
				}
			}
			return listRes;
			
			
		}
		
		throw new RestBadRequestException(BadRequestCause.BAD_PARAMETER_VALUE_BY);
        
    }
	
	@RequestMapping(value="/{eventId}", method=RequestMethod.GET)
    public EventResource getEvent(@PathVariable Long eventId) {
		
		Event event = eventRepository.findOne(eventId);
		EventResource eventResource	= new EventResource(event, null);

		return eventResource;
    }
	
	@RequestMapping(value="/{eventId}/book", method=RequestMethod.POST)
    public EventResource bookEvent(@PathVariable Long eventId, @RequestParam String code) throws ParseException {
		
		EventBookingCode eventBookingCode = eventBookingCodeFormatter.parse(code, LocaleContextHolder.getLocale());
		
		Event event = eventRepository.findOne(eventId);
		Person person = personRepository.findOne(eventBookingCode.getPersonId());
		
		if (event.getStatus().equals(EventStatus.AVAILABLE)) {
			event.setAttendee(person);
			event.setStatus(EventStatus.FULL);
			eventRepository.save(event);
		}
				
        return new EventResource(event, person);
    }

}
