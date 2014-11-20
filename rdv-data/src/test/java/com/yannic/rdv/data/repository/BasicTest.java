package com.yannic.rdv.data.repository;

import org.junit.Assert;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.Person;

@DatabaseSetup("basic_dataset_1.xml")
public class BasicTest extends BaseTest {
	
	@Test
	public void should_have_1_organizer() {
		Assert.assertEquals(1, organizerRepository.count());
	}
	
	@Test
	public void should_have_1_person() {
		Assert.assertEquals(1, organizerRepository.count());
	}
	
	@Test
	public void should_have_1_location() {
		Assert.assertEquals(1, locationRepository.count());
	}
	
	@Test
	public void should_have_1_event() {
		Assert.assertEquals(1, eventRepository.count());
	}
	
	@Test
	public void should_have_1_account() {
		Assert.assertEquals(1, accountRepository.count());
	}
	
	@Test
	public void event_should_be_linked_to_other_objects() {
		Event event = eventRepository.findOne((long) 2);
		
		Assert.assertNotNull(event.getAttendee());
		Assert.assertNotNull(event.getLocation());
		Assert.assertNotNull(event.getOrganizer());
	}
	
	@Test
	public void account_should_be_linked_to_person() {
		Account account = accountRepository.findOne((long) 1);
		Person person = personRepository.findOne((long) 4);
		
		Assert.assertNotNull(account.getAccountPersonAssociations());
		Assert.assertEquals(1, account.getAccountPersonAssociations().size());
		Assert.assertEquals(person, account.getAccountPersonAssociations().get(0).getPerson());
	}
	
	
}
