package com.fitback.ssu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SsuApplication {
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}
	public static void main(String[] args) {
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"local");
		SpringApplication.run(SsuApplication.class, args);
	}

}
