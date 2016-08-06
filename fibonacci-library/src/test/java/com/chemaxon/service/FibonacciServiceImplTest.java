package com.chemaxon.service;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class FibonacciServiceImplTest {

	private FibonacciService sut;
	
	@Before
	public void init() {
		sut = new FibonacciServiceImpl();
	}
	
	@Test(expected=NullPointerException.class)
	public void testfindNthTermDoesNotSupportNullInput() {
		sut.findNthTerm(null);
	}

	@Test
	public void testfindNthTermReturnsInputForNegativeNumber() {
		// Given
		Integer input = -1;
		// When
		Integer actual = sut.findNthTerm(input);
		// Then
		assertEquals(input, actual);
	}

	@Test
	public void testfindNthTermReturnsInputForZero() {
		// Given
		Integer input = 0;
		// When
		Integer actual = sut.findNthTerm(input);
		// Then
		assertEquals(input, actual);
	}

	@Test
	public void testfindNthTermReturnsInputForOne() {
		// Given
		Integer input = 1;
		// When
		Integer actual = sut.findNthTerm(input);
		// Then
		assertEquals(input, actual);
	}
	
	@Test
	public void testfindNthTermIsCorrectForSmallPositiveNumber() {
		// Given
		Integer input = 6;
		Integer expected = 8;
		// When
		Integer actual = sut.findNthTerm(input);
		// Then
		assertEquals(expected, actual);
	}
	
	@Test
	public void testfindNthTermIsCorrectForLargePositiveNumber() {
		// Given
		Integer input = 25;
		Integer expected = 75025;
		// When
		Integer actual = sut.findNthTerm(input);
		// Then
		assertEquals(expected, actual);
	}

	@Test // it breaks if UPPER_BOUND differs from the meaning of life (i.e. 42)
	public void testfindNthTermIsCorrectBeforeUpperBound() {
		// Given
		int input = FibonacciService.UPPER_BOUND -1;
		Integer expected = 165580141; 
		// When
		Integer actual = sut.findNthTerm(input);
		// Then
		assertEquals(expected, actual);
	}

}
