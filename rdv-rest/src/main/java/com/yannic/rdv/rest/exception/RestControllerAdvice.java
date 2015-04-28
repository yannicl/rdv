package com.yannic.rdv.rest.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yannic.rdv.rest.exception.LoginFailedException.LoginFailedCause;
import com.yannic.rdv.rest.exception.RestAccessDeniedException.AccessDeniedCause;
import com.yannic.rdv.rest.exception.RestBadRequestException.BadRequestCause;
import com.yannic.rdv.rest.exception.RestConflictException.ConflictCause;

@ControllerAdvice
@ResponseBody
public class RestControllerAdvice {
	
	@ExceptionHandler(RestAccessDeniedException.class)
    public AccessDeniedCause handleAccessDenied(RestAccessDeniedException ade, HttpServletResponse response) {
		
		response.setStatus(ade.getAccessDeniedCause().getStatus());
		
        return ade.getAccessDeniedCause();
    }
	
	@ExceptionHandler(RestBadRequestException.class)
    public BadRequestCause handleBadRequest(RestBadRequestException bre, HttpServletResponse response) {
		
		response.setStatus(bre.getBadRequestCause().getStatus());
		
        return bre.getBadRequestCause();
    }
	
	@ExceptionHandler(RestConflictException.class)
    public ConflictCause handleConflict(RestConflictException bre, HttpServletResponse response) {
		
		response.setStatus(bre.getConflictCause().getStatus());
		
        return bre.getConflictCause();
    }
	
	@ExceptionHandler(LoginFailedException.class)
	public LoginFailedCause handleLoginFailed(LoginFailedException lfe, HttpServletResponse response) {
		
		response.setStatus(lfe.getLoginFailedCause().getStatus());
		
		return lfe.getLoginFailedCause();
		
	}
	
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ConflictCause handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException ole, HttpServletResponse response) {
		
		response.setStatus(ConflictCause.CONFLICT_EVENT_NOT_AVAILABLE.getStatus());
		
		return ConflictCause.CONFLICT_EVENT_NOT_AVAILABLE;
		
	}
	
	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	public AccessDeniedCause handleSpringAccessDenied(org.springframework.security.access.AccessDeniedException ade, HttpServletResponse response) {
		
		response.setStatus(AccessDeniedCause.NO_RESOURCE_ACCESS.getStatus());
		
		return AccessDeniedCause.NO_RESOURCE_ACCESS;
		
	}
	
}
