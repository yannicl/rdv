package com.yannic.rdv.rest.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.Person;
import com.yannic.rdv.data.model.association.AccountPersonAssociation;
import com.yannic.rdv.data.model.type.EventStatus;
import com.yannic.rdv.rest.exception.RestBadRequestException;
import com.yannic.rdv.rest.exception.RestBadRequestException.BadRequestCause;
import com.yannic.rdv.rest.exception.RestConflictException;
import com.yannic.rdv.rest.exception.RestConflictException.ConflictCause;
import com.yannic.rdv.rest.formatter.EventBookingCodeFormatter;
import com.yannic.rdv.rest.requestparam.EventBookingCode;
import com.yannic.rdv.rest.resourcesupport.EventResource;
import com.yannic.rdv.rest.resourcesupport.EventSearchResource;
import com.yannic.rdv.rest.resourcesupport.EventSearchResource.EventSearchType;
import com.yannic.rdv.rest.resourcesupport.ListEventResource;
import com.yannic.rdv.rest.service.EventService;

@RestController
@Transactional
@RequestMapping("/api/events")
public class EventCtrl extends BaseCtrl {
	
	@Autowired
	EventBookingCodeFormatter eventBookingCodeFormatter;
	
	@Autowired
	EventService eventService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
    public List<EventSearchResource> listEventSearch() {
		List<EventSearchResource> list = new ArrayList<EventSearchResource>();
		Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for(AccountPersonAssociation association : account.getAccountPersonAssociations()) {
			list.add(new EventSearchResource(EventSearchType.PERSON, association.getPerson().getPersonId()));
		}
        return list;
    }
	
	
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
    public ListEventResource listAllEvents(@RequestParam String by, @RequestParam(required=false, defaultValue="0") Long id) {
		
		if (EventSearchType.ACCOUNT.getSearchBy().equals(by)) {
			
			return eventService.listAllEventsByAccount();
			
		} 
		else if (EventSearchType.PERSON.getSearchBy().equals(by)) {
			
			List<EventResource> listRes = new ArrayList<EventResource>();
			listRes.addAll(eventService.listAllBookedEventsByPerson(id));
			listRes.addAll(eventService.listAllAvailableEventsByPerson(id, new Date()));
			
			return new ListEventResource(listRes);
		}
		
		throw new RestBadRequestException(BadRequestCause.BAD_PARAMETER_VALUE_BY);
        
    }
	
	@RequestMapping(value="/{eventId}", method=RequestMethod.GET)
	@PreAuthorize("denyAll")
    public EventResource getEvent(@PathVariable Long eventId) {
		
		Event event = eventRepository.findOne(eventId);
		return eventService.getEvent(event);
		
    }
	
	
	
	@RequestMapping(value="/{eventId}/book", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	//No need for @PreAuthorize as the code is used as authorization
    public EventResource bookEvent(@PathVariable Long eventId, @RequestParam String code) throws ParseException {
		
		EventBookingCode eventBookingCode = eventBookingCodeFormatter.parse(code, LocaleContextHolder.getLocale());
		
		Event event = eventRepository.findOne(eventId);
		Person person = personRepository.findOne(eventBookingCode.getPersonId());
		
		if (event.getStatus().equals(EventStatus.AVAILABLE)) {
			event.setAttendee(person);
			event.setStatus(EventStatus.FULL);
			eventRepository.save(event);
			return null;
		} else {
			throw new RestConflictException(ConflictCause.CONFLICT_EVENT_NOT_AVAILABLE);
		}
				
    }
	
	@RequestMapping(value="/{eventId}/cancel", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PreAuthorize("@eventSM.mayCancelEvent(#eventId)")
    public EventResource cancelEvent(@PathVariable Long eventId) {
		
		Event event = eventRepository.findOne(eventId);
		
		if (event.getStatus().equals(EventStatus.FULL)) {
			event.setAttendee(null);
			event.setStatus(EventStatus.AVAILABLE);
			eventRepository.save(event);
			return null;
		} else {
			throw new RestConflictException(ConflictCause.CONFLICT_EVENT_NOT_BOOKED);
		}
				
    }

}
