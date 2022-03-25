package dev.yoon.basic_community.controller;

import dev.yoon.basic_community.config.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public String home(Authentication authentication) {
        try {
            log.info("connected user: {}",
                    authenticationFacade.getAuthentication().getName());
            log.info(authenticationFacade.getAuthentication().getClass().toString());
        } catch (NullPointerException e) {
            log.info("no user logged in");
        }
        return "index";
    }
}
