package com.chemaxon.service;

import org.springframework.stereotype.Service;

@Service
public class FibonacciServiceImpl implements FibonacciService {

	public Integer findNthTerm(Integer input) {
		return input <= 1 ? input : findNthTerm(input - 1) + findNthTerm(input - 2);

	}

}
