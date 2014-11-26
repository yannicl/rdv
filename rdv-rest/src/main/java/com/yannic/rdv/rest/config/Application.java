package com.yannic.rdv.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.yannic.rdv.data.repository.AccountRepository;
import com.yannic.rdv.rest.security.RestAuthenticationFilter;

@Configuration
//@ImportResource({"classpath:/applicationContext-security.xml", "classpath:/applicationContext-formatter.xml"})
@EnableAutoConfiguration
@ImportResource({"classpath:/application-context.xml"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}		
	
	@Autowired
	public void configureRestAuthentification(RestAuthenticationFilter filter, AccountRepository repo) {
		filter.setAccountRepository(repo);
	}	
		
}
