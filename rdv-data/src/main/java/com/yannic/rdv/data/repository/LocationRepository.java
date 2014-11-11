package com.yannic.rdv.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yannic.rdv.data.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

}
