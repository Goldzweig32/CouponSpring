package com.yaniv.coupons.exceptions;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yaniv.coupons.beans.ErrorBeans;

@ResponseBody
@ControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(ApplicationException.class)
	public ErrorBeans handleApplicationException(HttpServletResponse response,ApplicationException applicationException) {

		int errorCode = applicationException.getErrorType().getErrorCode();
		String internalMessage = applicationException.getMessage();
		String errorMessage = applicationException.getErrorType().getErrorMessage();
		ErrorBeans errorBeans = new ErrorBeans(errorCode, internalMessage, errorMessage);

		response.setStatus(applicationException.getErrorType().getErrorCode());
		applicationException.printStackTrace();
		return errorBeans;

	}
	
	@ExceptionHandler({Exception.class,Error.class})
	public ErrorBeans handleException(HttpServletResponse response, Throwable exception) {
		int errorCode = 601;
		String internalMessage = exception.getMessage();
		ErrorBeans errorBeans = new ErrorBeans(601, internalMessage, "General error");

		response.setStatus(errorCode);
		exception.printStackTrace();
		return errorBeans;
	}
}
