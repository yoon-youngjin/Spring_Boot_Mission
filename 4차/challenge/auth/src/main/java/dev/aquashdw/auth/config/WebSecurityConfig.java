package dev.aquashdw.auth.config;

import dev.aquashdw.auth.handler.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomSuccessHandler customSuccessHandler;

    public WebSecurityConfig(
            @Autowired CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests.antMatchers("/request-login/**").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .formLogin(formLogin -> {
                    formLogin.loginPage("/request-login");
                    formLogin.successHandler(customSuccessHandler);
                    formLogin.permitAll();
                })
                ;
    }


}
