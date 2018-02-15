package com.rdrcelic.mtls.client.contract;

import com.rdrcelic.mtls.client.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = {"com.rdrcelic:mtls-server:+:stubs:8443"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class UserServiceContractTest {

    private UserService userService;

    @Before
    public void setup() {
        userService = new UserService(new RestTemplate());
    }

    @Test
    public void userServiceReturnsHello() {

        assertThat(userService.getHello()).contains("Hello");
    }
}
