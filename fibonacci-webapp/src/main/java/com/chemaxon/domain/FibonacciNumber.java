package com.chemaxon.domain;

public class FibonacciNumber {

	private final Integer id;
	private Integer value;
	private String exception;

	public FibonacciNumber(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

}
