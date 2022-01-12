package com.itechart.retailers.controller.advice;

import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.exception.TaxesNotDefinedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.itechart.retailers.controller.constant.Message.TAXES_NOT_DEFINED_MSG;

@ControllerAdvice
public class NotDefinedExceptionAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotDefinedExceptionAdvice.class);

	@ExceptionHandler(TaxesNotDefinedException.class)
	public ResponseEntity<?> handleTaxesNotDefinedException(TaxesNotDefinedException e) {
		LOGGER.warn(TAXES_NOT_DEFINED_MSG);
		return ResponseEntity.badRequest().body(new MessageResp(TAXES_NOT_DEFINED_MSG));
	}
}
