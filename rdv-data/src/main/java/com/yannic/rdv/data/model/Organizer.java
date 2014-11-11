package com.yannic.rdv.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "organizers")
public class Organizer {
	
	@Id
	@Column(name="organizer_id")
	private long organizerId;
	private String name;
	private String description;
	
	public long getOrganizerId() {
		return organizerId;
	}
	public void setOrganizerId(long organizerId) {
		this.organizerId = organizerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
