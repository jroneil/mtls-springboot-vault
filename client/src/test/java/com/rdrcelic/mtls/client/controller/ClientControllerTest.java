package com.rdrcelic.mtls.client.controller;

import com.rdrcelic.mtls.client.service.UserService;
import com.rdrcelic.mtls.domain.MtlsUser;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.contains;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ClientController.class, secure = false) // wire up only controller under test, no autoconfigured security for MockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getMtlsUser() throws Exception {
        // given
        given(userService.getUser())
                .willReturn(MtlsUser.builder().authorities(Arrays.asList("ROLE_USER")).username("user").build());
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("user"))
                .andExpect(jsonPath("authorities", Matchers.containsInAnyOrder(("ROLE_USER"))));
    }
}
