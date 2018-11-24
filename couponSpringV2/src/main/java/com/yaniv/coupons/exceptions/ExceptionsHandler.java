package com.yaniv.coupons.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yaniv.coupons.beans.ErrorBeans;

@ControllerAdvice
public class ExceptionsHandler implements ExceptionMapper<Throwable> {
	
	@ExceptionHandler(ApplicationException.class)
	public Response toResponse(ApplicationException applicationException) {

			int errorCode = applicationException.getErrorType().getErrorCode();
			String internalMessage = applicationException.getMessage();
			String errorMessage = applicationException.getErrorType().getErrorMessage();
			ErrorBeans errorBeans = new ErrorBeans(errorCode, internalMessage, errorMessage);
			return Response.status(errorCode).entity(errorBeans).build();
		
	}

	@Override
	@ExceptionHandler({Exception.class,Error.class})
	public Response toResponse(Throwable exception) {
		String internalMessage = exception.getMessage();
		ErrorBeans errorBeans = new ErrorBeans(601, internalMessage, "General error");
		return Response.status(601).entity(errorBeans).build();
	}
}
