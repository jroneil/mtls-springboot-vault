package com.rdrcelic.mtls.server.repository;

import com.rdrcelic.mtls.server.domain.MtlsUserEntity;
import io.vavr.collection.List;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findByName_returnsMtlsUser() {
        // given
        MtlsUserEntity dummyUser = random(MtlsUserEntity.class);
        MtlsUserEntity savedUser = testEntityManager.persistFlushFind(dummyUser);

        // when
        MtlsUserEntity userEntity = userRepository.findByName(dummyUser.getName());

        // then
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()){
            softly.assertThat(userEntity.getName()).isEqualTo(savedUser.getName());
            softly.assertThat(userEntity.getAuthorities()).hasSameSizeAs(savedUser.getAuthorities());
            softly.assertThat(userEntity.getAuthorities()).hasSameElementsAs(savedUser.getAuthorities());
        }
    }

    @Test
    public void storeUserWithSameUsername_NOK() {
        // given
        MtlsUserEntity dummyUser = random(MtlsUserEntity.class);
        testEntityManager.persistFlushFind(dummyUser);
        dummyUser.setAuthorities(List.of("Some dummy role").asJava());

        // when try to save user with same username again
        Throwable throwable = catchThrowable(() -> testEntityManager.persistFlushFind(dummyUser));

        // then
        assertThat(throwable).isInstanceOf(EntityExistsException.class);
    }
}
