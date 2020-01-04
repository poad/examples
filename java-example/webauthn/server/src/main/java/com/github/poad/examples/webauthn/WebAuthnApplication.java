package com.github.poad.examples.webauthn;

import com.github.poad.examples.webauthn.config.WebAuthnConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WebAuthnApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAuthnApplication.class, args);
	}

}
