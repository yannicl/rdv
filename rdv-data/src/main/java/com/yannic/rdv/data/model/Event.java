package com.yannic.rdv.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yannic.rdv.data.model.type.EventStatus;

@Entity
@Table(name = "events")
public class Event {
	
	@Id
	@Column(name = "event_id")
	private long eventId;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Column(name = "end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	private int period;
	
	@Enumerated(EnumType.STRING)
	private EventStatus status;
	
	@OneToOne
	@JoinColumn(name = "organizer_id")
	private Organizer organizer;
	
	@OneToOne
	@JoinColumn(name = "attendee_id")
	private Person attendee;
	
	@OneToOne
	@JoinColumn(name = "location_id")
	private Location location;

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public Organizer getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Organizer organizer) {
		this.organizer = organizer;
	}

	public Person getAttendee() {
		return attendee;
	}

	public void setAttendee(Person attendee) {
		this.attendee = attendee;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}
