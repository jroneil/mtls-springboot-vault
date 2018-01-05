package com.rdrcelic.mtls.server.caching;

import com.rdrcelic.mtls.server.config.CachingConfiguration;
import com.rdrcelic.mtls.server.domain.MtlsUserEntity;
import com.rdrcelic.mtls.server.repository.UserRepository;
import com.rdrcelic.mtls.server.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class CachingTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void caching() {
        // given
        MtlsUserEntity dummyUserEntity = random(MtlsUserEntity.class);
        given(userRepository.findByName(anyString())).willReturn(dummyUserEntity);

        // when
        userService.getUser(dummyUserEntity.getName());
        userService.getUser(dummyUserEntity.getName());

        // then
        verify(userRepository, times(1)).findByName(dummyUserEntity.getName());
    }
}
