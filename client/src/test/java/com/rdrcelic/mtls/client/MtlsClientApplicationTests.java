package com.rdrcelic.mtls.client;

import com.rdrcelic.mtls.domain.MtlsUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 *  E2E test
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class MtlsClientApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

    @IfProfileValue(name="test-groups", value = "e2e")
	@Test
	public void getUserReturnsOK() {
		// given

	    // when
	    ResponseEntity<MtlsUser> response = restTemplate.getForEntity("/user", MtlsUser.class);

	    // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isNotEmpty();
        assertThat(response.getBody().getAuthorities()).isNotEmpty();
	}

}
