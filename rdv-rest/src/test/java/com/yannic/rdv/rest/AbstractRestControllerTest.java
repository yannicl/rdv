package com.yannic.rdv.rest;

import java.util.Collections;
import java.util.Date;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.yannic.rdv.data.model.Account;
import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.Location;
import com.yannic.rdv.data.model.Organizer;
import com.yannic.rdv.data.model.Person;
import com.yannic.rdv.data.model.association.AccountPersonAssociation;
import com.yannic.rdv.data.model.type.EventStatus;
import com.yannic.rdv.data.repository.AccountRepository;
import com.yannic.rdv.data.repository.EventRepository;
import com.yannic.rdv.data.repository.LocationRepository;
import com.yannic.rdv.data.repository.OrganizerRepository;
import com.yannic.rdv.data.repository.PersonRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestRestServiceConfig.class, TestRestServiceWebConfig.class })
@WebAppConfiguration
public abstract class AbstractRestControllerTest {
	
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected PersonRepository personRepository;
	
	@Autowired
	protected AccountRepository accountRepository;
	
	@Autowired
	protected LocationRepository locationRepository;
	
	@Autowired
	protected EventRepository eventRepository;
	
	@Autowired
	protected OrganizerRepository organizerRepository;

    @Before
    public void setup() {
        Mockito.reset(personRepository);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    public static final long EVENT_ID = 10;
    public static final Date EVENT_START_DATE = new Date();
    public static final Date EVENT_END_DATE = new Date();
    
    public Event getSingleAvailableEventTest() {
    	Event event = new Event();
    	event.setEventId(EVENT_ID);
    	event.setStartDate(EVENT_START_DATE);
    	event.setEndDate(EVENT_END_DATE);
    	event.setStatus(EventStatus.AVAILABLE);
    	return event;
    }
    
    public static final long PERSON_ID = 20;
    
    public Person getSinglePersonTest() {
    	Person person = new Person();
    	person.setPersonId(PERSON_ID);
    	person.setOrganizer(getSingleOrganizerTest());
    	return person;
    }
    
    public static final long ORGANIZER_ID = 30;
    
    public Organizer getSingleOrganizerTest() {
    	Organizer organizer = new Organizer();
    	organizer.setOrganizerId(ORGANIZER_ID);
    	return organizer;
    }
    
    public static final long LOCATION_ID = 40;
    
    public Location getSingleLocationTest() {
    	Location location = new Location();
    	location.setLocationId(LOCATION_ID);
    	location.setOrganizer(getSingleOrganizerTest());
    	return location;
    }
    
    public static final long ACCOUNT_ID = 50;
    
    public Account getSingleAccountTest() {
    	Account account = new Account();
    	account.setAccountId(ACCOUNT_ID);
    	account.setAccountPersonAssociations(Collections.singletonList(getSingleAccountPersonAssociationTest()));
    	return account;
    }
    
    public static final long ACCOUNT__PERSON_ASSOCIATION_ID = 60;
    
    public AccountPersonAssociation getSingleAccountPersonAssociationTest() {
    	AccountPersonAssociation accountPersonAssociation = new AccountPersonAssociation();
    	accountPersonAssociation.setAccountPersonAssociationId(ACCOUNT__PERSON_ASSOCIATION_ID);
    	accountPersonAssociation.setAccount(getSingleAccountTest());
    	accountPersonAssociation.setPerson(getSinglePersonTest());
    	return accountPersonAssociation;
    	
    }

}
