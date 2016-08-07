package com.chemaxon.service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FibonacciCalculatorServiceImpl implements FibonacciCalculatorService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private List<Integer> cache;

	public FibonacciCalculatorServiceImpl() {
		cache = initializeCache();
		logger.info("Created cache of size: " + cache.size());
	}

	public Integer findNthTerm(Integer input) {
		if (input <= 1) {
			return input;
		} else if (input > 1 && input < FibonacciCalculatorService.UPPER_BOUND) {
			return cache.get(input);
		} else {
			return findNthTerm(input-1) + findNthTerm(input-2);
		}
	}

	private List<Integer> initializeCache() {
		return Stream.generate(new Generator()).limit(UPPER_BOUND).collect(Collectors.toList());
	}
	
	private class Generator implements Supplier<Integer> {
		private int current = 0;
		private int previous = 1;
		
		@Override
		public Integer get() {
			int temp = current;
			current += previous;
			previous = temp;
			return previous;
		}
		
	}
}
