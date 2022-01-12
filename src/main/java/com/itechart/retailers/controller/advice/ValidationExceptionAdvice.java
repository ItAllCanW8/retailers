package com.itechart.retailers.controller.advice;

import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.exception.*;
import com.itechart.retailers.service.exception.ItemAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.itechart.retailers.controller.constant.Message.*;

@ControllerAdvice
public class ValidationExceptionAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionAdvice.class);

	@ExceptionHandler(ItemAmountException.class)
	public ResponseEntity<?> handleItemAmountException(ItemAmountException e) {
		LOGGER.warn(e.getMessage());
		return ResponseEntity.badRequest().body(new MessageResp(e.getMessage()));
	}

	@ExceptionHandler(DispatchItemException.class)
	public ResponseEntity<?> handleDispatchItemException(DispatchItemException e) {
		LOGGER.warn(e.getMessage());
		return ResponseEntity.badRequest().body(new MessageResp(e.getMessage()));
	}

	@ExceptionHandler(LocationIdentifierAlreadyExists.class)
	public ResponseEntity<?> handleLocationIdentifierAlreadyExistsException(LocationIdentifierAlreadyExists e) {
		LOGGER.warn(LOCATION_IDENTIFIER_EXISTS_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(LOCATION_IDENTIFIER_EXISTS_MSG));
	}

	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException e) {
		LOGGER.warn(INCORRECT_CURRENT_PASSWORD_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(INCORRECT_CURRENT_PASSWORD_MSG));
	}

	@ExceptionHandler(EmptyPasswordException.class)
	public ResponseEntity<?> handleEmptyPasswordException(EmptyPasswordException e) {
		LOGGER.warn(EMPTY_PASSWORD_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(EMPTY_PASSWORD_MSG));
	}

	@ExceptionHandler(MailIsAlreadyInUse.class)
	public ResponseEntity<?> handleMailIsAlreadyInUse(MailIsAlreadyInUse e) {
		LOGGER.warn(EMAIL_TAKEN_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(EMAIL_TAKEN_MSG));
	}

	@ExceptionHandler(IncorrectTaxException.class)
	public ResponseEntity<?> handleIncorrectTaxException(IncorrectTaxException e) {
		LOGGER.warn(NEGATIVE_TAX_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(NEGATIVE_TAX_MSG));
	}

	@ExceptionHandler(UserRoleNotApplicableToLocation.class)
	public ResponseEntity<?> handleUserRoleNotApplicableToLocationException(UserRoleNotApplicableToLocation e) {
		LOGGER.warn(USER_ROLE_NOT_APPLICABLE_TO_LOCATION_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(USER_ROLE_NOT_APPLICABLE_TO_LOCATION_MSG));
	}

	@ExceptionHandler(ItemAlreadyExistsException.class)
	public ResponseEntity<?> handleItemAlreadyExists(ItemAlreadyExistsException e) {
		LOGGER.warn(ITEM_ALREADY_EXISTS_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(ITEM_ALREADY_EXISTS_MSG));
	}

	@ExceptionHandler(ApplicationAlreadyExists.class)
	public ResponseEntity<?> handleApplicationAlreadyExists(ApplicationAlreadyExists e) {
		LOGGER.warn(APPLICATION_ALREADY_EXISTS_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(APPLICATION_ALREADY_EXISTS_MSG));
	}

	@ExceptionHandler(BillAlreadyExistsException.class)
	public ResponseEntity<?> handleBillAlreadyExists(BillAlreadyExistsException e) {
		LOGGER.warn(BILL_ALREADY_EXISTS_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(BILL_ALREADY_EXISTS_MSG));
	}

	@ExceptionHandler(WriteOffActAlreadyExistsException.class)
	public ResponseEntity<?> handleWriteOffActAlreadyExists(WriteOffActAlreadyExistsException e) {
		LOGGER.warn(WRITE_OFF_ACT_ALREADY_EXISTS_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(WRITE_OFF_ACT_ALREADY_EXISTS_MSG));
	}
}
