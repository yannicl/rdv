package com.yannic.rdv.rest.formatter;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.google.common.io.BaseEncoding;
import com.yannic.rdv.rest.exception.RestBadRequestException;
import com.yannic.rdv.rest.exception.RestBadRequestException.BadRequestCause;
import com.yannic.rdv.rest.requestparam.EventBookingCode;

@Component
public class EventBookingCodeFormatter implements Formatter<EventBookingCode> {
	
	public final static int CODE_LENGTH = 24; // 3 Long (8bytes each)
	public final static int ENCODED_CODE_LENGTH = 32; // CODE_LENGTH * 150%
	
	@Override
	public String print(EventBookingCode source, Locale arg1) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(CODE_LENGTH);
		byteBuffer.putLong(source.getEventId()); 
		byteBuffer.putLong(source.getPersonId());
		byteBuffer.putLong(source.getEventBookingCodeCreationDate().getTime());
		//TODO add security token to prevent code tampering
		byte[] encoded = byteBuffer.array();
		return BaseEncoding.base64Url().encode(encoded);
	}

	@Override
	public EventBookingCode parse(String source, Locale arg1)
			throws ParseException {
		if (source == null || source.length() != ENCODED_CODE_LENGTH) {
			throw new RestBadRequestException(BadRequestCause.BAD_PARAMETER_ILLEGAL_CODE);
		}
		byte[] encoded;
		try {
		encoded = BaseEncoding.base64Url().decode(source);
		} catch (IllegalArgumentException e) {
			throw new RestBadRequestException(BadRequestCause.BAD_PARAMETER_ILLEGAL_CODE);
		}
		ByteBuffer byteBuffer = ByteBuffer.wrap(encoded);
		EventBookingCode result = new EventBookingCode();
		result.setEventId(byteBuffer.getLong());
		result.setPersonId(byteBuffer.getLong());
		result.setEventBookingCodeCreationDate(new Date(byteBuffer.getLong()));
		//TODO add security verification to prevent code tampering
		return result;
	}

}
