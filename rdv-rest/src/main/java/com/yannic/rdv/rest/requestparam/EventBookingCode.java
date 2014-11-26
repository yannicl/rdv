package com.yannic.rdv.rest.requestparam;

import java.util.Date;

public class EventBookingCode {
	
	private Long personId;
	private Long eventId;
	private Date eventBookingCodeCreationDate;
	
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Date getEventBookingCodeCreationDate() {
		return eventBookingCodeCreationDate;
	}
	public void setEventBookingCodeCreationDate(Date eventBookingCodeCreationDate) {
		this.eventBookingCodeCreationDate = eventBookingCodeCreationDate;
	}
	
}
