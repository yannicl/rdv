package com.yannic.rdv.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yannic.rdv.data.model.Event;

@RepositoryRestResource(exported = false)
public interface EventRepository extends JpaRepository<Event, Long>{

}
