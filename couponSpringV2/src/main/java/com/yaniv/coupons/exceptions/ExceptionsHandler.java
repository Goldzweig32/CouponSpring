package com.yaniv.coupons.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.yaniv.coupons.beans.ErrorBeans;

@Provider
public class ExceptionsHandler implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {

		if (exception instanceof ApplicationException) {
			ApplicationException appException = (ApplicationException) exception;

			int errorCode = appException.getErrorType().getErrorCode();
			String internalMessage = appException.getMessage();
			String errorMessage = appException.getErrorType().getErrorMessage();
			ErrorBeans errorBeans = new ErrorBeans(errorCode, internalMessage, errorMessage);
			return Response.status(errorCode).entity(errorBeans).build();
		}

		String internalMessage = exception.getMessage();
		ErrorBeans errorBeans = new ErrorBeans(601, internalMessage, "General error");
		return Response.status(601).entity(errorBeans).build();
		
	}

}
