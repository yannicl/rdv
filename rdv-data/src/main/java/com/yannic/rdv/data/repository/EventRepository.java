package com.yannic.rdv.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yannic.rdv.data.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

}
