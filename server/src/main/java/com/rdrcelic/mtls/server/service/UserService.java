package com.rdrcelic.mtls.server.service;

import com.rdrcelic.mtls.domain.MtlsUser;
import com.rdrcelic.mtls.domain.UserNotFoundException;
import com.rdrcelic.mtls.server.domain.MtlsUserEntity;
import com.rdrcelic.mtls.server.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable("users")
    public MtlsUser getUser(String username) {
        MtlsUserEntity userEntity = userRepository.findByName(username);
        if (userEntity == null) {
            throw new UserNotFoundException();
        }
        MtlsUser user = MtlsUser.builder().username(userEntity.getName()).authorities(userEntity.getAuthorities()).build();
        return user;
    }
}
