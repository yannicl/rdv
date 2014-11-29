package com.yannic.rdv.rest.resourcesupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.yannic.rdv.rest.controller.EventCtrl;

public class EventSearchResource extends ResourceSupport {
	
	public enum EventSearchType {
		ACCOUNT("rdv:searchByAccount", "account"),
		PERSON("rdv:searchByPerson", "person"),
		ORGANIZER("rdv:searchAsOrganizer", "organizer"),
		ALL("rdv:searchAsAdmin", "admin");
		
		private final String rel;
		private final String searchBy;
		
		private EventSearchType(String rel, String searchBy){
			this.rel = rel;
			this.searchBy = searchBy;
		}

		public String getRel() {
			return rel;
		}

		public String getSearchBy() {
			return searchBy;
		}
		
	}
	
	public EventSearchResource(EventSearchType t, Long id) {
		this.add(linkTo(methodOn(EventCtrl.class).listAllEvents(t.getSearchBy(), id)).withRel(t.getRel()));
	}

}
