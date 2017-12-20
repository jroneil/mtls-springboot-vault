package com.rdrcelic.mtls.util;

import org.junit.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class UrlComposerTest {
    private final String urlTemplate = "https://localhost:8443/slowdata/{delay:[0-9]{1,6}}";

    @Test
    public void buildUrlWithIntegerPathVariable_OK() throws Exception {
        UriComponents uriComponents = UrlComposer.buildUri(urlTemplate, Collections.singletonMap("delay", 123));

        assertThat(uriComponents.getPath()).isEqualTo("/slowdata/123");
        assertThat(uriComponents.toUriString()).isEqualTo("https://localhost:8443/slowdata/123");
    }

    // TODO: this test should fail, regex in urlTemplate should throw, examine why it doesn't
    @Test
    public void buildUrlWithIntegerPathVariable_wrongValue() throws Exception {
        UriComponents uriComponents = UrlComposer.buildUri(urlTemplate, Collections.singletonMap("delay", "hey"));

        assertThat(uriComponents.getPath()).isEqualTo("/slowdata/hey");
        assertThat(uriComponents.toUriString()).isEqualTo("https://localhost:8443/slowdata/hey");
    }
}