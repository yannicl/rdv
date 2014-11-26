package com.yannic.rdv.rest;

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

import com.yannic.rdv.data.model.Event;
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

}
