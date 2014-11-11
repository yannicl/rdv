package com.yannic.rdv.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yannic.rdv.data.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
