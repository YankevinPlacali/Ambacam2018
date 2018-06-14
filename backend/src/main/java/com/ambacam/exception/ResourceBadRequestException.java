package com.ambacam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException {
	private static final long serialVersionUID = -7542149993439034896L;

	public ResourceBadRequestException() {
		super();
	}

	public ResourceBadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceBadRequestException(String message) {
		super(message);
	}

	public ResourceBadRequestException(Throwable cause) {
		super(cause);
	}
}