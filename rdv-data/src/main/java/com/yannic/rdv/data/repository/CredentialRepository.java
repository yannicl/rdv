package com.yannic.rdv.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yannic.rdv.data.model.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long>{

}
