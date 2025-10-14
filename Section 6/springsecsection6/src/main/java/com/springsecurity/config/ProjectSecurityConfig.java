package com.springsecurity.config;

import com.springsecurity.exceptionhandling.CustomAccessDeniedHandler;
import com.springsecurity.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
       // http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());

        //http.authorizeHttpRequests((requests) -> requests.requestMatchers("/welcome","/myAccount","/myBalance","/myCards","/myLoans").authenticated());
        //http.authorizeHttpRequests((requests) -> requests.requestMatchers("/contact","/notices","/error","/register").permitAll());
        http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidsession").maximumSessions(1))
                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/welcome","/myAccount","/myBalance","/myCards","/myLoans").authenticated()
                .requestMatchers("/contact","/notices","/error","/register","/invalidsession").permitAll()
        );
        http.formLogin(withDefaults() );
        // This only handle exception while login
        http.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        // down is global config. This will handle all exception coming from application due to spring security
        //http.exceptionHandling(ehc->ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
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
