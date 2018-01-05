package com.rdrcelic.mtls.client.service;

import com.rdrcelic.mtls.domain.MtlsUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private UserService userService;

    @Before
    public void setup() {
        userService = new UserService(restTemplate);
    }

    @Test
    public void getUser() {
        // given
        MtlsUser dummyMtlsUser = random(MtlsUser.class);
        given(restTemplate.getForEntity(anyString(), eq(MtlsUser.class))).willReturn(new ResponseEntity(dummyMtlsUser, HttpStatus.OK));
        // then
        MtlsUser responseUser = userService.getUser();
        assertThat(responseUser).isNotNull();
        assertThat(responseUser.getUsername()).isEqualTo(dummyMtlsUser.getUsername());
        assertThat(responseUser.getAuthorities()).hasSameSizeAs(dummyMtlsUser.getAuthorities());
        assertThat(responseUser.getAuthorities()).hasSameElementsAs(dummyMtlsUser.getAuthorities());
    }
}