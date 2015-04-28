package com.yannic.rdv.rest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class RestConflictException extends RuntimeException {
	
	
	private static final long serialVersionUID = 6147279787594739250L;
	
	public static final String CONFLICT_EVENT_NOT_AVAILABLE_MSG = "The requested event is not available anymore.";
	public static final String CONFLICT_EVENT_NOT_BOOKED_MSG = "The requested event is not booked and may therefore not be cancelled.";
	
	
	@JsonFormat(shape= JsonFormat.Shape.OBJECT)
	@JsonPropertyOrder({"timestamp"})
	public enum ConflictCause {
		
		CONFLICT_EVENT_NOT_AVAILABLE(409, "E_CON_001", "Conflict", CONFLICT_EVENT_NOT_AVAILABLE_MSG, ""),
		CONFLICT_EVENT_NOT_BOOKED(409, "E_CON_002", "Conflict", CONFLICT_EVENT_NOT_BOOKED_MSG, "");
		
		private int status;
		private String error;
		private String message;
		private String developperMessage;
		private String moreInfo;
		
		private ConflictCause(int status, String error, String message, String developperMessage, String moreInfo) {
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
	
	private ConflictCause internalCause;
	
	public RestConflictException(ConflictCause cause) {
		super(cause.toString());
		this.internalCause = cause;
	}
	
	public ConflictCause getConflictCause() {
		return this.internalCause;
	}

}
