package com.ambacam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ResourceUnauthorizedException extends RuntimeException {
	private static final long serialVersionUID = -7542149993439034896L;

	public ResourceUnauthorizedException() {
		super();
	}

	public ResourceUnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceUnauthorizedException(String message) {
		super(message);
	}

	public ResourceUnauthorizedException(Throwable cause) {
		super(cause);
	}
}