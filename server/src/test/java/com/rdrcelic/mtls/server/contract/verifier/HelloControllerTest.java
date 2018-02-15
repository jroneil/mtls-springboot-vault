package com.rdrcelic.mtls.server.contract.verifier;

import com.rdrcelic.mtls.server.MtlsServerApplication;
import com.rdrcelic.mtls.server.controller.HelloController;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MtlsServerApplication.class)
@DirtiesContext
public class HelloControllerTest {

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new HelloController());
    }

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//    @Before
//    public void setup() {
//        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
//    }
}
