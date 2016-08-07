package com.chemaxon;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.chemaxon.domain.FibonacciNumber;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class FibonacciWebApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestRestTemplate client;

    @LocalServerPort
    int serverPort;

    @Test
    public void contextLoads() {
    		logger.info("Context is loaded. Tomcat is listening on port: " + serverPort);
    }

	@Test
	public void testDefaultValue () {
		//When
		ResponseEntity<FibonacciNumber> responseEntity = client.getForEntity("/compute", FibonacciNumber.class);
		FibonacciNumber actual = responseEntity.getBody();

		//Then
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), Matchers.is(MediaType.APPLICATION_JSON_UTF8));

		assertThat(actual.getId(), is(0));
		assertThat(actual.getValue(), is(0));
		assertThat(actual.getException(), is(nullValue()));
	}

	@Test
	public void testInputNaN () {
		//When
		ResponseEntity<FibonacciNumber> responseEntity = client.getForEntity("/compute?id={id}", FibonacciNumber.class, "NaN");
		FibonacciNumber actual = responseEntity.getBody();

		//Then
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		assertThat(responseEntity.getHeaders().getContentType(), Matchers.is(MediaType.APPLICATION_JSON_UTF8));

		assertThat(actual.getValue(), is(nullValue()));
		assertThat(actual.getException(), not(isEmptyOrNullString()));
	}

	@Test
	public void testNegativeNumber () {
		//When
		ResponseEntity<FibonacciNumber> responseEntity = client.getForEntity("/compute?id={id}", FibonacciNumber.class, -1);
		FibonacciNumber actual = responseEntity.getBody();

		//Then
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
		assertThat(responseEntity.getHeaders().getContentType(), Matchers.is(MediaType.APPLICATION_JSON_UTF8));

		assertThat(actual.getId(), is(nullValue()));
		assertThat(actual.getValue(), is(nullValue()));
		assertThat(actual.getException(),not(isEmptyOrNullString()));
	}

	@Test
	public void testZero () {
		//When
		ResponseEntity<FibonacciNumber> responseEntity = client.getForEntity("/compute?id={id}", FibonacciNumber.class, 0);
		FibonacciNumber actual = responseEntity.getBody();

		//Then
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), Matchers.is(MediaType.APPLICATION_JSON_UTF8));

		assertThat(actual.getId(), is(0));
		assertThat(actual.getValue(), is(0));
		assertThat(actual.getException(), is(nullValue()));
	}

	@Test
	public void testOne () {
		//When
		ResponseEntity<FibonacciNumber> responseEntity = client.getForEntity("/compute?id={id}", FibonacciNumber.class, 1);
		FibonacciNumber actual = responseEntity.getBody();

		//Then
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), Matchers.is(MediaType.APPLICATION_JSON_UTF8));

		assertThat(actual.getId(), is(1));
		assertThat(actual.getValue(), is(1));
		assertThat(actual.getException(), is(nullValue()));
	}

	@Test
	public void testSmallNumber () {
		//When
		ResponseEntity<FibonacciNumber> responseEntity = client.getForEntity("/compute?id={id}", FibonacciNumber.class, 8);
		FibonacciNumber actual = responseEntity.getBody();

		//Then
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), Matchers.is(MediaType.APPLICATION_JSON_UTF8));

		assertThat(actual.getId(), is(8));
		assertThat(actual.getValue(), is(21));
		assertThat(actual.getException(), is(nullValue()));
	}

	@Test
	public void testLargeNumber () {
		//When
		ResponseEntity<FibonacciNumber> responseEntity = client.getForEntity("/compute?id={id}", FibonacciNumber.class, 25);
		FibonacciNumber actual = responseEntity.getBody();

		//Then
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), Matchers.is(MediaType.APPLICATION_JSON_UTF8));

		assertThat(actual.getId(), is(25));
		assertThat(actual.getValue(), is(75025));
		assertThat(actual.getException(), is(nullValue()));
	}

	@Test
	public void testUpperBoundFromRight () {
		//When
		ResponseEntity<FibonacciNumber> responseEntity = client.getForEntity("/compute?id={id}", FibonacciNumber.class, 43);
		FibonacciNumber actual = responseEntity.getBody();

		//Then
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
		assertThat(responseEntity.getHeaders().getContentType(), Matchers.is(MediaType.APPLICATION_JSON_UTF8));

		assertThat(actual.getId(), is(nullValue()));
		assertThat(actual.getValue(), is(nullValue()));
		assertThat(actual.getException(), not(isEmptyOrNullString()));
	}

	@Test
	public void testUpperBound () {
		//When
		ResponseEntity<FibonacciNumber> responseEntity = client.getForEntity("/compute?id={id}", FibonacciNumber.class, 42);
		FibonacciNumber actual = responseEntity.getBody();

		//Then
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), Matchers.is(MediaType.APPLICATION_JSON_UTF8));

		assertThat(actual.getId(), is(42));
		assertThat(actual.getValue(), is(267914296));
		assertThat(actual.getException(), is(nullValue()));
	}

	@Test
	public void testUpperBoundFromLeft () {
		//When
		ResponseEntity<FibonacciNumber> responseEntity = client.getForEntity("/compute?id={id}", FibonacciNumber.class, 41);
		FibonacciNumber actual = responseEntity.getBody();

		//Then
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), Matchers.is(MediaType.APPLICATION_JSON_UTF8));

		assertThat(actual.getId(), is(41));
		assertThat(actual.getValue(), is(165580141));
		assertThat(actual.getException(), is(nullValue()));
	}

}
