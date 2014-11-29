package com.yannic.rdv.rest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.Person;
import com.yannic.rdv.data.model.association.AccountPersonAssociation;
import com.yannic.rdv.data.model.type.EventStatus;
import com.yannic.rdv.rest.formatter.EventBookingCodeFormatter;
import com.yannic.rdv.rest.requestparam.EventBookingCode;
import com.yannic.rdv.rest.resourcesupport.EventResource;
import com.yannic.rdv.rest.resourcesupport.ListEventResource;

@Service
public class EventService extends BaseService {

	@Autowired
	EventBookingCodeFormatter eventBookingCodeFormatter;

	
	public ListEventResource listAllEventsByAccount() {

		List<EventResource> consolidatedList = new ArrayList<EventResource>();
		Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for(AccountPersonAssociation association : account.getAccountPersonAssociations()) {
			List<Event> list = eventRepository.findByAttendeeAndStatus(association.getPerson(), EventStatus.FULL);
			for(Event e : list) {
				consolidatedList.add(getEvent(e));
			}
		}
		return new ListEventResource(consolidatedList);

	}

	@PreAuthorize("@eventSM.hasAccessToPerson(#personId)")
	public List<EventResource> listAllBookedEventsByPerson(Long personId) {

		Person person = personRepository.findOne(personId);

		List<EventResource> listRes = new ArrayList<EventResource>();

		List<Event> listBookedEvents = eventRepository.findByAttendeeAndStatus(person, EventStatus.FULL);
		for(Event e : listBookedEvents) {
			listRes.add(getEvent(e));
		}

		return listRes;
	}

	@PreAuthorize("@eventSM.hasAccessToPerson(#personId)")
	public List<EventResource> listAllAvailableEventsByPerson(Long personId, Date startDate) {

		Person person = personRepository.findOne(personId);

		List<EventResource> listRes = new ArrayList<EventResource>();

		List<Event> listAvailableEvents = eventRepository.findByOrganizerAndStatusAndStartDateGreaterThan(
				person.getOrganizer(),
				EventStatus.AVAILABLE,
				startDate,
				new PageRequest(0, 10)
				); 

		for(Event event : listAvailableEvents) {
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

	public EventResource getEvent(Event event) {
		EventResource eventResource	= new EventResource(event, null);
		if (event.getStatus().equals(EventStatus.FULL)) {
			eventResource.addCancel();
		}
		return eventResource;
	}
	
	

}
