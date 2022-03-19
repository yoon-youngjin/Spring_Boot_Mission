package dev.yoon.challenge_community.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class OAuthClientController {
    private static final Logger logger = LoggerFactory.getLogger(OAuthClientController.class);
    private final WebClient webClient;

    public OAuthClientController(
            @Autowired
            WebClient webClient
    ) {
        this.webClient = webClient;
    }

    @GetMapping(value = "/test")
    public String[] getCategories(
            @RegisteredOAuth2AuthorizedClient("likelion-auth-code") OAuth2AuthorizedClient authorizedClient,
            @RequestParam(name = "likelion_login_cookie") String likelion_login_cookie
            ) {
        logger.info(authorizedClient.getPrincipalName());
        logger.info(likelion_login_cookie);

        return this.webClient
                .get()
                .uri("http://127.0.0.1:9090/category")
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String[].class)
                .block();
    }
}
