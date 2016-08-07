package com.chemaxon.service;

public interface FibonacciCalculatorService {
	
	int UPPER_BOUND = 42;

	Integer findNthTerm(Integer n);
}
