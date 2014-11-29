package com.yannic.rdv.rest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RestConflictException extends RuntimeException {
	
	
	private static final long serialVersionUID = 6147279787594739250L;
	
	public static final String CONFLICT_EVENT_NOT_AVAILABLE_MSG = "The requested event is not available anymore.";
	public static final String CONFLICT_EVENT_NOT_BOOKED_MSG = "The requested event is not booked and may therefore not be cancelled.";
	
	
	@JsonFormat(shape= JsonFormat.Shape.OBJECT)
	public enum ConflictCause {
		
		CONFLICT_EVENT_NOT_AVAILABLE(409, "E_CON_001", "Conflict", CONFLICT_EVENT_NOT_AVAILABLE_MSG, ""),
		CONFLICT_EVENT_NOT_BOOKED(409, "E_CON_002", "Conflict", CONFLICT_EVENT_NOT_BOOKED_MSG, "");
		
		private int httpStatusCode;
		private String errorCode;
		private String message;
		private String developperMessage;
		private String moreInfo;
		
		private ConflictCause(int httpStatusCode, String errorCode, String message, String developperMessage, String moreInfo) {
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
	
	private ConflictCause internalCause;
	
	public RestConflictException(ConflictCause cause) {
		super(cause.toString());
		this.internalCause = cause;
	}
	
	public ConflictCause getConflictCause() {
		return this.internalCause;
	}

}
