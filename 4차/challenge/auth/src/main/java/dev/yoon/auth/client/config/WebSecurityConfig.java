package dev.yoon.auth.client.config;

import dev.yoon.auth.handler.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomSuccessHandler customSuccessHandler;

    public WebSecurityConfig(
            @Autowired CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(
            @Autowired AuthenticationProvider authenticationProvider,
            HttpSecurity http
    ) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests.antMatchers("/user/sign-up").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .formLogin(formLogin -> {
                    formLogin.loginPage("/user/login");
                    formLogin.successHandler(customSuccessHandler);
                    formLogin.permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/user/login");
                })
                .csrf().disable()
                .authenticationProvider(authenticationProvider)
        ;
        return http.build();
    }

    @Bean
    // AuthenticationProvider: userDetailsService를 관리하는 객체
    public AuthenticationProvider authenticationProvider(
            @Autowired UserDetailsService userDetailsService,
            @Autowired PasswordEncoder passwordEncoder
    ){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
