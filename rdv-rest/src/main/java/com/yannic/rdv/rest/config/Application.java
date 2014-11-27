package com.yannic.rdv.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.yannic.rdv.data.repository.AccountRepository;
import com.yannic.rdv.rest.security.RestAuthenticationFilter;

@Configuration
@EnableAutoConfiguration
@ImportResource({"classpath:/application-context.xml"})
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@EnableWebMvc
@EnableSpringDataWebSupport
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}		
	
	@Autowired
	public void configureRestAuthentification(RestAuthenticationFilter filter, AccountRepository repo) {
		filter.setAccountRepository(repo);
	}	
		
}
