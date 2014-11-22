package com.yannic.rdv.rest.resourcesupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.rest.controller.EventCtrl;

public class EventResource extends ResourceSupport {
	
	private final Event event;
	
	public EventResource(Event event) {
		this.event = event;
		this.add(linkTo(methodOn(EventCtrl.class).getEvent(event.getEventId())).withSelfRel());
	}

	public Event getEvent() {
		return event;
	}
	
	

}
