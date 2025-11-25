package com.vekrest.vekgateway;

import com.vekrest.vekgateway.vekgateway.config.JwtDecoderConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class VekgatewayApplicationTests {
	@MockitoBean
	private JwtDecoderConfig jwtDecoderConfig;

	@MockitoBean
	private ReactiveJwtDecoder reactiveJwtDecoder;

	@Mock
	private SecurityWebFilterChain securityWebFilterChain;

	@Test
	void contextLoads() {
	}

}
