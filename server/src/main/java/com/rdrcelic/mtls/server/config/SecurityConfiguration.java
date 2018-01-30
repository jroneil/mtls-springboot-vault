package com.rdrcelic.mtls.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // TODO: this list should be part of server configuration
    private static List<String> adminClientIds = Arrays.asList("myclient", "browserClient");

    /**
     * Configure mTLS based on client certificate CN field value
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .x509()
                .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                .userDetailsService(userDetailsService());
    }

    /**
     * Authenticate user based on TLS client Common Name (CN) configured in its certificate
     * and assign ROLE_TLS_CLIENT authority.
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User.UserBuilder builder = User.withUsername(username).password("NOT-USED");
            builder = this.adminClientIds.contains(username) ? builder.roles("TLS_CLIENT", "USER") : builder.roles("USER");
            return builder.build();
        };
    }
}
