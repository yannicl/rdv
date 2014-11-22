package com.yannic.rdv.rest.resourcesupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.yannic.rdv.data.model.Person;
import com.yannic.rdv.rest.controller.EventCtrl;
import com.yannic.rdv.rest.controller.PersonCtrl;
import com.yannic.rdv.rest.resourcesupport.EventSearchResource.EventSearchType;

public class PersonResource extends ResourceSupport {
	
	private final Person person;
	
	public PersonResource(Person person) {
		this.person = person;
		this.add(linkTo(methodOn(PersonCtrl.class).getPerson(person.getPersonId())).withSelfRel());
		this.add(linkTo(methodOn(EventCtrl.class).
				listAllEvents(EventSearchType.PERSON.getSearchBy(), person.getPersonId())).withRel(EventSearchType.PERSON.getRel()
				));
	}

	public Person getPerson() {
		return person;
	}

}
