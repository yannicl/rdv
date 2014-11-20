package com.yannic.rdv.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yannic.rdv.data.model.Account;

@RepositoryRestResource(exported = false)
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account findByApiKey(String apiKey);

}
