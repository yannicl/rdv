package com.yannic.rdv.rest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class LoginFailedException extends RuntimeException {
	
			
	private static final long serialVersionUID = 6579202860951389053L;
	
	public static final String LOGIN_FAILED_EXCEPTION_INVALID_USERNAME_OR_PASSWORD_MSG = "The given username and password does not match to a valid account.";
	public static final String LOGIN_FAILED_EXCEPTION_NOT_SUPPORTED_AUTHENTICATION_METHOD_MSG = "The authentication method specified for the account is not supported.";
	public static final String LOGIN_FAILED_EXCEPTION_FAILURE_AUTHENTICATION_METHOD_MSG = "The server was not able to perform authentication specified for the account.";
	
	@JsonFormat(shape= JsonFormat.Shape.OBJECT)
	@JsonPropertyOrder({"timestamp"})
	public enum LoginFailedCause {
		
		LOGIN_FAILED_EXCEPTION_INVALID_USERNAME_OR_PASSWORD(422, "E_LOG_001", "Login failure", LOGIN_FAILED_EXCEPTION_INVALID_USERNAME_OR_PASSWORD_MSG, ""),
		LOGIN_FAILED_EXCEPTION_NOT_SUPPORTED_AUTHENTICATION_METHOD(500, "E_LOG_002", "Login failure", LOGIN_FAILED_EXCEPTION_NOT_SUPPORTED_AUTHENTICATION_METHOD_MSG, ""),
		LOGIN_FAILED_EXCEPTION_FAILURE_AUTHENTICATION_METHOD(500, "E_LOG_003", "Login failure", LOGIN_FAILED_EXCEPTION_FAILURE_AUTHENTICATION_METHOD_MSG, "");
		
		private int status;
		private String error;
		private String message;
		private String developperMessage;
		private String moreInfo;
		
		private LoginFailedCause(int status, String error, String message, String developperMessage, String moreInfo) {
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
	
	private LoginFailedCause internalCause;
	
	public LoginFailedException(LoginFailedCause cause) {
		super(cause.toString());
		this.internalCause = cause;
	}
	
	public LoginFailedCause getLoginFailedCause() {
		return this.internalCause;
	}

}
