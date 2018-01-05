package com.rdrcelic.mtls.server.repository;

import com.rdrcelic.mtls.server.domain.MtlsUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository <MtlsUserEntity, String> {

    public MtlsUserEntity findByName(String name);
}
