package com.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
/*
 *This Web security annotation is used to enable Spring Security in non-spring boot application.
 * Spring boot will take care of this on seeing the dependencies
 */
@EnableWebSecurity
public class EazyBankBackendApplication {

/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
	/**
	 * Entry point for the EazyBankBackendApplication.
	 *
	 * @param args The arguments passed to the application.
	 */
/* <<<<<<<<<<  7226ba69-7c7e-431b-9209-e84883481051  >>>>>>>>>>> */
	public static void main(String[] args) {
		SpringApplication.run(EazyBankBackendApplication.class, args);
	}

}
