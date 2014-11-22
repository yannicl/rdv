package com.yannic.rdv.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yannic.rdv.data.model.Event;
import com.yannic.rdv.data.model.Organizer;

@RepositoryRestResource(exported = false)
public interface EventRepository extends JpaRepository<Event, Long>{

	List<Event> findByOrganizer(Organizer organizer);
	
}
