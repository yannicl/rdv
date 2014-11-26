package com.yannic.rdv.rest.resourcesupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.text.ParseException;

import org.springframework.hateoas.ResourceSupport;

import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.Person;
import com.yannic.rdv.rest.controller.EventCtrl;

public class EventResource extends ResourceSupport {
	
	public static final String REL_EVENT_BOOKING = "rdv:bookEvent";
	
	private final Event event;
	
	public EventResource(Event event, Person person) {
		this.event = event;
		this.add(linkTo(methodOn(EventCtrl.class).getEvent(event.getEventId())).withSelfRel());
	}

	public Event getEvent() {
		return event;
	}
	
	public void addBookingCode(String code) {
		try {
			this.add(linkTo(methodOn(EventCtrl.class).bookEvent(event.getEventId(), code)).withRel(REL_EVENT_BOOKING));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
