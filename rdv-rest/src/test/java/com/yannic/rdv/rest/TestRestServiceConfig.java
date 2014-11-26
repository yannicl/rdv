package com.yannic.rdv.rest;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yannic.rdv.data.repository.AccountRepository;
import com.yannic.rdv.data.repository.EventRepository;
import com.yannic.rdv.data.repository.LocationRepository;
import com.yannic.rdv.data.repository.OrganizerRepository;
import com.yannic.rdv.data.repository.PersonRepository;

@Configuration
public class TestRestServiceConfig {

    @Bean
    public AccountRepository accountRepositoryMock() {
        return Mockito.mock(AccountRepository.class);
    }

    @Bean
    public EventRepository eventRepositoryMock() {
        return Mockito.mock(EventRepository.class);
    }

    @Bean
    public LocationRepository locationRepositoryMock() {
        return Mockito.mock(LocationRepository.class);
    }

    @Bean
    public OrganizerRepository organizerRepositoryMock() {
        return Mockito.mock(OrganizerRepository.class);
    }

    @Bean
    public PersonRepository personRepositoryMock() {
        return Mockito.mock(PersonRepository.class);
    }
}