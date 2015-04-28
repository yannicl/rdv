package com.yannic.rdv.rest.exception;

import org.springframework.security.access.AccessDeniedException;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.yannic.rdv.rest.security.RestAuthenticationFilter;

public class RestAccessDeniedException extends AccessDeniedException {
		
	private static final long serialVersionUID = 3661851620339586630L;
	
	private static final String NO_KEY_MSG = "The header " + RestAuthenticationFilter.HEADER_SECURITY_API_KEY + 
			" was not present in request headers.";
	
	private static final String ILLEGAL_KEY_MSG = "The header " + RestAuthenticationFilter.HEADER_SECURITY_API_KEY + " is present but " + 
	"the provided value has not the correct length. The value must contains exactly " +  RestAuthenticationFilter.API_KEY_LENGTH + " characters.";
	
	private static final String WRONG_KEY_MSG = "The provided key has the correct format and was correctly processed but was rejected because " + 
	"this key does not correspond to any existing account.";
	
	private static final String NO_RESOURCE_ACCESS_MSG = "Access denied to the requested resource. " + 
	"Note that access to specific resource using self links is not permitted for regular user. Use the rdv-prefixed (rdv:) relation to navigate through resources.";

	@JsonFormat(shape= JsonFormat.Shape.OBJECT)
	@JsonPropertyOrder({"timestamp"})
	public enum AccessDeniedCause {
		NO_KEY			(401, "E_SEC_001", "No API Key provided", NO_KEY_MSG, ""),
		ILLEGAL_KEY		(403, "E_SEC_002", "Illegal API Key", ILLEGAL_KEY_MSG, ""),
		WRONG_KEY		(403, "E_SEC_003", "Wrong API Key", WRONG_KEY_MSG, ""),
		EXPIRED_KEY		(403, "E_SEC_004", "Expired API KEY", "RFU", ""),
		NO_RESOURCE_ACCESS(403, "E_SEC_005", "Access Denied", NO_RESOURCE_ACCESS_MSG, "");
		
		private int status;
		private String error;
		private String message;
		private String developperMessage;
		private String moreInfo;
		
		private AccessDeniedCause(int status, String error, String message, String developperMessage, String moreInfo) {
			this.status = status;
			this.error = error;
			this.message = message;
			this.developperMessage = developperMessage;
			this.moreInfo = moreInfo;
		}
		
		@JsonProperty
		public long getTimestamp() {
			return System.currentTimeMillis();
		}

		public int getStatus() {
			return status;
		}

		public String getError() {
			return error;
		}

		public String getMessage() {
			return message;
		}

		public String getDevelopperMessage() {
			return developperMessage;
		}

		public String getMoreInfo() {
			return moreInfo;
		}
		
	}
	
	private AccessDeniedCause internalCause;
	
	public RestAccessDeniedException(AccessDeniedCause cause) {
		super(cause.toString());
		this.internalCause = cause;
	}
	
	public AccessDeniedCause getAccessDeniedCause() {
		return this.internalCause;
	}

}
