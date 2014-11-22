package com.yannic.rdv.rest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;


public class RestBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 8513832110819971260L;
	
	public static final String BAD_PARAMETER_VALUE_BY_MSG = "The provided parameter 'by' contains a bad value.";
	
	@JsonFormat(shape= JsonFormat.Shape.OBJECT)
	public enum BadRequestCause {
		
		BAD_PARAMETER_VALUE_BY(400, "E_BRE_001", "Bad parameter", BAD_PARAMETER_VALUE_BY_MSG, "");
		
		private int httpStatusCode;
		private String errorCode;
		private String message;
		private String developperMessage;
		private String moreInfo;
		
		private BadRequestCause(int httpStatusCode, String errorCode, String message, String developperMessage, String moreInfo) {
			this.httpStatusCode = httpStatusCode;
			this.errorCode = errorCode;
			this.message = message;
			this.developperMessage = developperMessage;
			this.moreInfo = moreInfo;
		}

		public int getHttpStatusCode() {
			return httpStatusCode;
		}

		public String getErrorCode() {
			return errorCode;
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
