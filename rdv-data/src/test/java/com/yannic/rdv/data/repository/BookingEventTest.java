package com.yannic.rdv.data.repository;

import org.junit.Assert;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("available_events_dataset.xml")
public class BookingEventTest extends BaseTest {
	
	@Test
	public void should_have_10_event() {
		Assert.assertEquals(10, eventRepository.count());
	}
	
	
}
