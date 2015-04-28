package com.yannic.rdv.rest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.yannic.rdv.rest.formatter.EventBookingCodeFormatter;


public class RestBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 8513832110819971260L;
	
	public static final String BAD_PARAMETER_VALUE_BY_MSG = "The provided parameter 'by' contains a bad value.";
	public static final String BAD_PARAMETER_ILLEGAL_CODE_MSG = "The code is encoded in base64 and must have a length of " + EventBookingCodeFormatter.ENCODED_CODE_LENGTH 
			+ " characters. This code is generated by the REST API and must not be modified.";
	
	@JsonFormat(shape= JsonFormat.Shape.OBJECT)
	@JsonPropertyOrder({"timestamp"})
	public enum BadRequestCause {
		
		BAD_PARAMETER_VALUE_BY(400, "E_BRE_001", "Bad parameter", BAD_PARAMETER_VALUE_BY_MSG, ""),
		BAD_PARAMETER_ILLEGAL_CODE(400, "E_BRE_002", "Illegal code", BAD_PARAMETER_ILLEGAL_CODE_MSG, "");
		
		private int status;
		private String error;
		private String message;
		private String developperMessage;
		private String moreInfo;
		
		private BadRequestCause(int status, String error, String message, String developperMessage, String moreInfo) {
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
	
	private BadRequestCause internalCause;
	
	public RestBadRequestException(BadRequestCause cause) {
		super(cause.toString());
		this.internalCause = cause;
	}
	
	public BadRequestCause getBadRequestCause() {
		return this.internalCause;
	}
	

}
