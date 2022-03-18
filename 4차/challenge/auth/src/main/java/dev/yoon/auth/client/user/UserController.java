package dev.yoon.auth.client.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(
            @Autowired UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("home !!!");
        System.out.println(request.getCookies());

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            log.info("cookie info: {}", cookie.getName());
//            if(cookie.getName().equals("likelion_login_cookie")){
//                log.info("cookie info: {}", cookie.getComment());
//                response.addCookie(cookie);
//                break;
//            }
        }
        return "index";
    }

    @GetMapping(
            "/user/login"
    )
    public String login() {
        return "user/loginForm";
    }

    @GetMapping(
            "/user/sign-up"
    )
    public String signUp() {
        return "user/signUpForm";
    }

    @PostMapping(
            "/user/sign-up"
    )
    public String createUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("passwordCheck") String passwordCheck
    ) {
        if (!password.equals(passwordCheck)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        userService.signUp(username, password);

        return "user/loginForm";
    }
}
