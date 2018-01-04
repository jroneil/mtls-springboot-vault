package com.rdrcelic.mtls.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class MtlsUser {

    private String username;
    private Collection<String> authorities;
}
