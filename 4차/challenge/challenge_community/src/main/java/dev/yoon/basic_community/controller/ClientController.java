package dev.yoon.basic_community.controller;

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
public class ClientController {

    @GetMapping("/home")
    public String home(){
        return "client/home";
    }

}
