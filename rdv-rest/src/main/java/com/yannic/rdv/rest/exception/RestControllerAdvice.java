package com.yannic.rdv.rest.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yannic.rdv.rest.exception.RestAccessDeniedException.AccessDeniedCause;
import com.yannic.rdv.rest.exception.RestBadRequestException.BadRequestCause;

@ControllerAdvice
@ResponseBody
public class RestControllerAdvice {
	
	@ExceptionHandler(RestAccessDeniedException.class)
    public AccessDeniedCause handleAccessDenied(RestAccessDeniedException ade, HttpServletResponse response) {
		
		response.setStatus(ade.getAccessDeniedCause().getHttpStatusCode());
		
        return ade.getAccessDeniedCause();
    }
	
	@ExceptionHandler(RestBadRequestException.class)
    public BadRequestCause handleBadRequest(RestBadRequestException bre, HttpServletResponse response) {
		
		response.setStatus(bre.getBadRequestCause().getHttpStatusCode());
		
        return bre.getBadRequestCause();
    }

}
