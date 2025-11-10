package com.vekrest.vekgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class RouteConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(
                                "/v3/api-docs/**",
                                "/vekrest/vekclient/v3/api-docs/**",
                                "/vekrest/veksecurity/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/vekrest/vekclient/swagger-ui/**",
                                "/vekrest/veksecurity/swagger-ui/**",

                                "/vekrest/veksecurity/user/save/**",
                                "/vekrest/veksecurity/login/**"
                        )
                        .permitAll()
                        .anyExchange()
                        .authenticated()
                ).oauth2ResourceServer(oauth -> oauth.jwt())
                .build();
    }

}
