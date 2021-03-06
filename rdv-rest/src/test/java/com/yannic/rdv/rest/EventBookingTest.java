package com.yannic.rdv.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;
import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.Organizer;
import com.yannic.rdv.data.model.Person;
import com.yannic.rdv.data.model.type.EventStatus;
import com.yannic.rdv.rest.resourcesupport.EventSearchResource.EventSearchType;

public class EventBookingTest extends AbstractRestControllerTest {
	
	private String bookingEventUrl;
	
	@Before
	public void setupRepositories() {
		Event event = getSingleAvailableEventTest();
		when(eventRepository.findOne(EVENT_ID)).thenReturn(event);
		Person person = getSinglePersonTest();
		when(personRepository.findOne(PERSON_ID)).thenReturn(person);
		when(((eventRepository.findByOrganizerAndStatusAndStartDateGreaterThan(
				any(Organizer.class), 
				any(EventStatus.class), 
				any(Date.class),
				any(PageRequest.class)))))
				.thenAnswer(setupDummyListAnswer(event));
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
	
	@Test
	public void should_get_json_available_event_when_searching_by_person() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/events/search?by={by}&id={person_id}", EventSearchType.PERSON.getSearchBy(), PERSON_ID))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.HAL_JSON))
        .andExpect(jsonPath("$.events[0].event.status").value(EventStatus.AVAILABLE.toString())).andReturn();
		
		bookingEventUrl = JsonPath.read(result.getResponse().getContentAsString(), "$.events[0]._links['rdv:bookEvent'].href");
		bookingEventUrl = bookingEventUrl.replace("http://localhost", "");
		Assert.assertTrue(bookingEventUrl.contains("/book"));
		
	}
	
	@Test
	public void should_book_event_when_posting_to_booking_event_url() throws Exception {
		if (bookingEventUrl == null) {
			should_get_json_available_event_when_searching_by_person();
		}
		
		mockMvc.perform(post(bookingEventUrl))
        .andExpect(status().isNoContent());
		
	}
	
	
	
	/**
	 * Create a mockito-compatible list of events.
	 * See http://stackoverflow.com/questions/7366237/mockito-stubbing-methods-that-return-type-with-bounded-wild-cards 
	 */
	private <N extends Event> Answer<List<N>> setupDummyListAnswer(N... values) {
	    final List<N> someList = new ArrayList<N>();

	    someList.addAll(Arrays.asList(values));

	    Answer<List<N>> answer = new Answer<List<N>>() {
	        public List<N> answer(InvocationOnMock invocation) throws Throwable {
	            return someList;
	        }   
	    };
	    return answer;
	}

}
