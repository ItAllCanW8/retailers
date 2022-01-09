package com.itechart.retailers.controller.advice;

import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.exception.TaxesNotDefinedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.itechart.retailers.controller.constant.Message.TAXES_NOT_DEFINED_MSG;

@ControllerAdvice
public class NotDefinedExceptionAdvice {

	@ExceptionHandler(TaxesNotDefinedException.class)
	public ResponseEntity<?> handleTaxesNotDefinedException(TaxesNotDefinedException e){
		return ResponseEntity.badRequest().body(new MessageResp(TAXES_NOT_DEFINED_MSG));
	}
}
