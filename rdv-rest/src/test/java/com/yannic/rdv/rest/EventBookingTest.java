package com.yannic.rdv.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;

import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.type.EventStatus;

public class EventBookingTest extends AbstractRestControllerTest {
	
	@Before
	public void setupRepositories() {
		Event event = getSingleAvailableEventTest();
		when(eventRepository.findOne(EVENT_ID)).thenReturn(event);
	}
	
	@Test
    public void should_have_one_event_to_book() throws Exception {
				
        Assert.assertNotNull(eventRepository.findOne(EVENT_ID));
        
    }
	
	@Test
	public void should_get_json_available_event_when_get_event_by_id() throws Exception {
		mockMvc.perform(get("/api/events/{eventId}", EVENT_ID))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.HAL_JSON))
        .andExpect(jsonPath("$.event.status").value(EventStatus.AVAILABLE.toString()));
	}

}
