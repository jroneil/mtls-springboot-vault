package com.rdrcelic.mtls.client.util;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

public class UrlComposer {

    /**
     * Builds uri from string url template and map with named parameters
     * @param urlStringTeplate template with named placeholders (e.g. "/somepath/{param1}?{param2}={param3}
     * @param parameters key value pairs (as java.util.Map) where keys are matching named placeholders in urlStringTemplate
     *                   (e.g. in given example it should be {("param1", value1), ("param2", value2), ("param3", value3)}).
     *                   Keys are always of type java.lang.String, values are java.lang.Object.
     * @return UriComponents out of which you can get urlString, or any of components from that url
     */
    public static UriComponents buildUri(String urlStringTeplate, Map<String, Object> parameters) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(urlStringTeplate).build();
        return uriComponents.expand(parameters);
    }
}
