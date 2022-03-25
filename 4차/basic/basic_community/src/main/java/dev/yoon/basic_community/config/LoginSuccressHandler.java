package dev.yoon.basic_community.config;

import dev.yoon.basic_community.domain.user.User;
import dev.yoon.basic_community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginSuccressHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        User user = this.userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("onconsistent: user not found after successful login")
        );

        user.setLastLogin(Instant.now());
        this.userRepository.save(user);

        super.onAuthenticationSuccess(request,response,authentication);
    }
}
