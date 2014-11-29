package com.yannic.rdv.data.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.type.EventStatus;

@TransactionConfiguration(defaultRollback=false)
@DatabaseSetup("available_events_dataset.xml")
public class BookingEventTest extends BaseTest {
	
	@Test
	public void should_have_10_event() {
		Assert.assertEquals(10, eventRepository.count());
	}
	
	@Test
	public void should_update_first_event_as_booked() {
		Event e = eventRepository.findOne((long) 10);
		e.setStatus(EventStatus.FULL);
		eventRepository.save(e);
	}
	
	
}
