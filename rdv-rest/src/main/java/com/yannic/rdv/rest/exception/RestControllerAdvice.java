package com.yannic.rdv.rest.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yannic.rdv.rest.exception.RestAccessDeniedException.AccessDeniedCause;

@ControllerAdvice
public class RestControllerAdvice {
	
	@ExceptionHandler(RestAccessDeniedException.class)
	@ResponseBody
    public AccessDeniedCause handleAccessDenied(RestAccessDeniedException ade, HttpServletResponse response) {
		
		response.setStatus(ade.getAccessDeniedCause().getHttpStatusCode());
		
        return ade.getAccessDeniedCause();
    }

}
