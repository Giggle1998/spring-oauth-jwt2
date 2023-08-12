package com.crit.oauthjwt2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OauthJwt2Application {

	public static void main(String[] args) {
		SpringApplication.run(OauthJwt2Application.class, args);
	}

}
