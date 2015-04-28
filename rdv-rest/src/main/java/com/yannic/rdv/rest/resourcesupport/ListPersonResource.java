package com.yannic.rdv.rest.resourcesupport;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class ListPersonResource extends ResourceSupport {
	
	private List<PersonResource> persons;
	
	public ListPersonResource(List<PersonResource> listPerson) {
		this.setList(listPerson);
	}

	public List<PersonResource> getPersons() {
		return persons;
	}

	public void setList(List<PersonResource> listPerson) {
		this.persons = listPerson;
	}

}
