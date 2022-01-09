package com.itechart.retailers.controller.advice;

import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.itechart.retailers.controller.constant.Message.*;

@ControllerAdvice
public class ValidationExceptionAdvice {

	@ExceptionHandler(ItemAmountException.class)
	public ResponseEntity<?> handleItemAmountException(ItemAmountException e) {
		return ResponseEntity.badRequest().body(new MessageResp(e.getMessage()));
	}

	@ExceptionHandler(DispatchItemException.class)
	public ResponseEntity<?> handleDispatchItemException(DispatchItemException e) {
		return ResponseEntity.badRequest().body(new MessageResp(e.getMessage()));
	}

	@ExceptionHandler(LocationIdentifierAlreadyExists.class)
	public ResponseEntity<?> handleLocationIdentifierAlreadyExistsException(LocationIdentifierAlreadyExists e) {
		return ResponseEntity.badRequest().body(new MessageResp(LOCATION_IDENTIFIER_EXISTS_MSG));
	}

	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException e) {
		return ResponseEntity.badRequest().body(new MessageResp(INCORRECT_CURRENT_PASSWORD_MSG));
	}

	@ExceptionHandler(EmptyPasswordException.class)
	public ResponseEntity<?> handleEmptyPasswordException(EmptyPasswordException e) {
		return ResponseEntity.badRequest().body(new MessageResp(EMPTY_PASSWORD_MSG));
	}

	@ExceptionHandler(MailIsAlreadyInUse.class)
	public ResponseEntity<?> handleMailIsAlreadyInUse(MailIsAlreadyInUse e) {
		return ResponseEntity.badRequest().body(new MessageResp(EMAIL_TAKEN_MSG));
	}

	@ExceptionHandler(IncorrectTaxException.class)
	public ResponseEntity<?> handleIncorrectTaxException(IncorrectTaxException e) {
		return ResponseEntity.badRequest().body(new MessageResp(NEGATIVE_TAX_MSG));
	}

	@ExceptionHandler(UserRoleNotApplicableToLocation.class)
	public ResponseEntity<?> handleUserRoleNotApplicableToLocationException(UserRoleNotApplicableToLocation e) {
		return ResponseEntity.badRequest().body(new MessageResp(USER_ROLE_NOT_APPLICABLE_TO_LOCATION_MSG));
	}
}
