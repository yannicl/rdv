package com.yannic.rdv.data.model.type;

public enum EventStatus {
	NEW, // organizer has just created the event. The event is not yet available for booking
	AVAILABLE, // the event is available and may be booked
	FULL, // the event has been booked and may be cancelled by attendee
	FROZEN // organizer has frozen the event and no modification may be made by the attendee
}
