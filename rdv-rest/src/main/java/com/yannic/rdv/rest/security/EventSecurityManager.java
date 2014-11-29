package com.yannic.rdv.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.association.AccountPersonAssociation;
import com.yannic.rdv.rest.exception.RestConflictException;
import com.yannic.rdv.rest.exception.RestConflictException.ConflictCause;

@Component("eventSM")
public class EventSecurityManager extends BaseSecurityManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(EventSecurityManager.class);
	
	public boolean mayCancelEvent(Long eventId) {
		
		Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Event event = eventRepository.findOne(eventId);
		
		if (event == null) {
			LOG.warn(String.format("Account %s tried to cancel an unexisting event.", account.getAccountId()));
			return false;
		}
		
		if (event.getAttendee() == null) {
			LOG.warn(String.format("Account %s tried to cancel an available event.", account.getAccountId()));
			throw new RestConflictException(ConflictCause.CONFLICT_EVENT_NOT_BOOKED);
		}
		
		
		for(AccountPersonAssociation association : account.getAccountPersonAssociations()) {
			if (event.getAttendee().getPersonId().equals(association.getPerson().getPersonId())) {
				LOG.info(String.format("Account %s is authorized to cancel event %s.", account.getAccountId(), event.getEventId()));
				return true;
			}
		}
		
		LOG.warn(String.format("Account %s is unauthorized to cancel event %s.", account.getAccountId(), event.getEventId()));
		return false;
	}
	
	public boolean hasAccessToPerson(Long personId) {
		
		Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		for(AccountPersonAssociation association : account.getAccountPersonAssociations()) {
			if (association.getPerson().getPersonId().equals(personId)) {
				LOG.info(String.format("Account %s is authorized to access person %s.", account.getAccountId(), personId));
				return true;
			}
		}
		
		LOG.warn(String.format("Account %s is unauthorized to access person %s.", account.getAccountId(), personId));
		return false;
		
	}

}
