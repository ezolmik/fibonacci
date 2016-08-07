package com.chemaxon.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chemaxon.domain.FibonacciNumber;
import com.chemaxon.service.FibonacciCalculatorService;

@Validated
@RestController
public class FibonacciController {

	private FibonacciCalculatorService service;

	public FibonacciController(FibonacciCalculatorService service) {
		this.service = service;
	}

	@RequestMapping("/compute")
	public @ResponseBody FibonacciNumber compute(
			@NotNull @Min(0) @Max(FibonacciCalculatorService.UPPER_BOUND) @RequestParam(required = false, defaultValue = "0") Integer id) {
		FibonacciNumber number = new FibonacciNumber(id);
		Integer value = service.findNthTerm(id);
		number.setValue(value);
		return number;
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder sb = new StringBuilder();
		for (ConstraintViolation<?> violation : violations) {
			sb.append(violation.getMessage() + "\n");
		}
		FibonacciNumber body = new FibonacciNumber();
		body.setException(sb.toString().trim());
		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}

}
