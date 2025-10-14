package com.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
       // http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());

       // http.authorizeHttpRequests((requests) -> requests.requestMatchers("/welcome","/myAccount","/myBalance","/myCards","/myLoans").authenticated());
        //http.authorizeHttpRequests((requests) -> requests.requestMatchers("/contact","/notices","/error","/register").permitAll());
       // .requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) to insure https
        http.requiresChannel(rcc -> rcc.anyRequest().requiresSecure())
                .csrf(csrfConfig->csrfConfig.disable())
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/welcome","/myAccount","/myBalance","/myCards","/myLoans").authenticated()
                .requestMatchers("/contact","/notices","/error","/register").permitAll()
        );
        http.formLogin(withDefaults() );
       // http.formLogin(flc ->flc.disable() );
        http.httpBasic(withDefaults());
        return http.build();
    }

    //use {noop} before password in case of no password encoder
    //generated hash password using https://bcrypt-generator.com
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//       return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker() {
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }
}
