package com.yannic.rdv.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@ComponentScan(basePackages = {
        "com.yannic.rdv.rest.controller", "com.yannic.rdv.rest.formatter", "com.yannic.rdv.rest.service" 
})
public class TestRestServiceWebConfig extends WebMvcConfigurerAdapter {

}
