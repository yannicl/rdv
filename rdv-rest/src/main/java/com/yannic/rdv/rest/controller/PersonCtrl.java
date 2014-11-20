package com.yannic.rdv.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yannic.rdv.rest.exception.RestAccessDeniedException;
import com.yannic.rdv.rest.exception.RestAccessDeniedException.AccessDeniedCause;

@RestController
@RequestMapping("/api")
public class PersonCtrl {
	
	@RequestMapping(value="/persons2", method=RequestMethod.GET)
	@ResponseBody
    public String listAllPersons() {
        return "Hello World";
    }
	
	@RequestMapping(value="/persons3", method=RequestMethod.GET)
	@ResponseBody
    public String fail() {
		throw new RestAccessDeniedException(AccessDeniedCause.WRONG_KEY);
    }

}
