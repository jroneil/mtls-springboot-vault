package com.rdrcelic.mtls.server.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class MtlsUserEntity {

    @Id
    private String name;
    @ElementCollection
    private List<String> authorities;
}
