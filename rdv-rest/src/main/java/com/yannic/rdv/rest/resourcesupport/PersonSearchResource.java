package com.yannic.rdv.rest.resourcesupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.yannic.rdv.rest.controller.PersonCtrl;

public class PersonSearchResource extends ResourceSupport {
	
	public enum PersonSearchType {
		ACCOUNT("rdv:searchPersonByAccount", "account"),
		ORGANIZER("rdv:searchPersonAsOrganizer", "organizer"),
		ALL("rdv:searchPersonAsAdmin", "admin");
		
		private final String rel;
		private final String searchBy;
		
		private PersonSearchType(String rel, String searchBy){
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
	
	public PersonSearchResource(PersonSearchType t) {
		this.add(linkTo(methodOn(PersonCtrl.class).listAllPersons(t.getSearchBy())).withRel(t.getRel()));
	}

}
