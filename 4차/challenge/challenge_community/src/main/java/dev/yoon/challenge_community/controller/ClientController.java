package dev.yoon.challenge_community.controller;

import dev.yoon.challenge_community.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("home")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public String home(
            @RequestParam(value = "likelion_login_cookie", required = false) String likelion_login_cookie
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getDetails());
        System.out.println(likelion_login_cookie);
        return "login-form";
    }


}
