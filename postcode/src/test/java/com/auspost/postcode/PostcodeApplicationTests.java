package com.auspost.postcode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@ActiveProfiles("test") // ensures the JWT token variable is loaded from the test-properties
class PostcodeApplicationTests {

	@Test
	void contextLoads() {
	}

}
