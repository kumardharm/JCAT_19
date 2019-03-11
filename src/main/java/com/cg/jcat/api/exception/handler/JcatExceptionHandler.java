package com.cg.jcat.api.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import com.cg.jcat.api.controller.UserController;
import com.cg.jcat.api.entity.ValidationException;
import com.cg.jcat.api.exception.CountMissMatchException;
import com.cg.jcat.api.exception.DeleteUserException;
import com.cg.jcat.api.exception.ApplicationIdNotFoundException;
import com.cg.jcat.api.exception.OptionTextNotNullException;
import com.cg.jcat.api.exception.SaveUserException;
import com.cg.jcat.api.exception.SystemExceptions;
import com.cg.jcat.api.exception.UserAlreadyExistsException;
import com.cg.jcat.api.exception.entity.ErrorDTO;

@ControllerAdvice
public class JcatExceptionHandler {
//UserAlreadyExistsExceptionHandler
	
	@ExceptionHandler(value = UserAlreadyExistsException.class)
	public ResponseEntity<Object> UserExistsException(UserAlreadyExistsException exception,WebRequest request) {

		ErrorDTO errorDetails = new ErrorDTO(exception.getErrorDTO().getErrorCode(),
				exception.getErrorDTO().getErrorMessage(), exception.getErrorDTO().getErrorValue(),exception.getErrorDTO().getErrorTimestamp(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = SaveUserException.class)
	public ResponseEntity<Object> UserSaveException(SaveUserException exception,WebRequest request) {

		ErrorDTO errorDetails = new ErrorDTO(exception.getErrorDTO().getErrorCode(),
				exception.getErrorDTO().getErrorMessage(), exception.getErrorDTO().getErrorValue(),exception.getErrorDTO().getErrorTimestamp(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = DeleteUserException.class)
	public ResponseEntity<Object> DeleteException(DeleteUserException exception,WebRequest request) {

		ErrorDTO errorDetails = new ErrorDTO(exception.getErrorDTO().getErrorCode(),
				exception.getErrorDTO().getErrorMessage(), exception.getErrorDTO().getErrorValue(),exception.getErrorDTO().getErrorTimestamp(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = SystemExceptions.class)
	public ResponseEntity<Object> SystemException(SystemExceptions exception,WebRequest request) {

		ErrorDTO errorDetails = new ErrorDTO(exception.getErrorDTO().getErrorCode(),
				exception.getErrorDTO().getErrorMessage(), exception.getErrorDTO().getErrorValue(),exception.getErrorDTO().getErrorTimestamp(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}
	
//	@ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public ResponseEntity<Object> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//   if(e instanceof NullPointerException)
//  {
//	   ErrorDTO errorDetails = new ErrorDTO(req.getErrorDTO().getError_code(),
//				exception.getErrorDTO().getError_message(), exception.getErrorDTO().getError_value(),exception.getErrorDTO().getError_timestamp(),request.getDescription(false));
//  }
//	}
	
	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(ValidationException exception,WebRequest request) {

		ErrorDTO errorDetails = new ErrorDTO(exception.getErrorDTO().getErrorCode(),
				exception.getErrorDTO().getErrorMessage(), exception.getErrorDTO().getErrorValue(),exception.getErrorDTO().getErrorTimestamp(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = OptionTextNotNullException.class)
	public ResponseEntity<Object> optionText(OptionTextNotNullException exception,WebRequest request) {

		ErrorDTO errorDetails = new ErrorDTO(exception.getErrorDTO().getErrorCode(),
				exception.getErrorDTO().getErrorMessage(), exception.getErrorDTO().getErrorValue(),exception.getErrorDTO().getErrorTimestamp(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = CountMissMatchException.class)
	public ResponseEntity<Object> optionText(CountMissMatchException exception,WebRequest request) {

		ErrorDTO errorDetails = new ErrorDTO(exception.getErrorDTO().getErrorCode(),
				exception.getErrorDTO().getErrorMessage(), exception.getErrorDTO().getErrorValue(),exception.getErrorDTO().getErrorTimestamp(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE); 
	}
	
	@ExceptionHandler(value = ApplicationIdNotFoundException.class)
	public ResponseEntity<Object> inputMissMatch(ApplicationIdNotFoundException exception,WebRequest request) {

		ErrorDTO errorDetails = new ErrorDTO(exception.getErrorDTO().getErrorCode(),
				exception.getErrorDTO().getErrorMessage(), exception.getErrorDTO().getErrorValue(),exception.getErrorDTO().getErrorTimestamp(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}

}
