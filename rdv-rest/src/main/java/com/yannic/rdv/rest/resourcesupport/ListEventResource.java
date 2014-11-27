package com.yannic.rdv.rest.resourcesupport;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class ListEventResource extends ResourceSupport {
	
	private List<EventResource> list;
	
	public ListEventResource(List<EventResource> listEvent) {
		this.setList(listEvent);
	}

	public List<EventResource> getList() {
		return list;
	}

	public void setList(List<EventResource> listEvent) {
		this.list = listEvent;
	}

}
