package dev.yoon.challenge_community.filter;

import dev.yoon.challenge_community.LikelionSsoConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * QueryParameter를 확인하기 위한 filter
 */
@Component
@Slf4j
@Order(value = 2)
public class SsoAuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Optional<String> authToken = authTokenFromCookie(httpServletRequest.getCookies());

        /**
         * Cookie에 값이 없는 경우
         * queryparameter에서 가져옴
         */
        if (authToken.isEmpty()) {
            authToken = authTokenFromQuery(httpServletRequest, httpServletResponse);
        }

        if (authToken.isPresent()) {
            log.info("Login Token value: {}", authToken.get());
            this.setSsoAuthentication(authToken.get());
        }
        /**
         * 익명 사용자인 경우: 로그인 안 한 경우
         */
        else {
            log.info("Login Token Missing");
            SecurityContextHolder.getContext().setAuthentication(
                    new AnonymousAuthenticationToken(
                            "anonymous",
                            "anonymous",
                            Collections.singletonList((GrantedAuthority) () -> "ROLE_ANONYMOUS"))
            );
        }

        chain.doFilter(httpServletRequest, httpServletResponse);

    }

    private void setSsoAuthentication(String tokenValue) {
        /**
         * TODO create new Authentication based on token
         * tokenValue가 실제로 valid한지 SSO서버에 요청을 보내고
         * 그에 대한 응답을 보내는 부분
         */
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return (Principal) () -> "dummy";
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "dummy";
            }
        });


    }

    private Optional<String> authTokenFromQuery(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {

        String queryString = httpServletRequest.getQueryString();

        if (queryString == null) {
            log.debug("query string is null");
            return Optional.empty();
        }
        String[] queryParams = queryString.split("&");
        for (String queryParam : queryParams) {
            String[] queryParamSplit = queryParam.split("=");

            if (queryParamSplit.length == 1) continue;

            if (queryParamSplit[0].equals(LikelionSsoConsts.LIKELION_LOGIN_COOKIE)) {
                log.debug("found token in query");
                String loginToken = queryParamSplit[1];
                Cookie newTokenCookie = new Cookie(LikelionSsoConsts.LIKELION_LOGIN_COOKIE, loginToken);
                newTokenCookie.setPath("/");
                httpServletResponse.addCookie(newTokenCookie);
                return Optional.of(queryParamSplit[1]);
            }
        }

        log.debug("could not find token from query");
        return Optional.empty();

    }

    private Optional<String> authTokenFromCookie(Cookie[] cookies) {

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LikelionSsoConsts.LIKELION_LOGIN_COOKIE)) {
                    log.debug("found token in cookie");
                    return Optional.of(cookie.getValue());
                }
            }
        }

        log.debug("could not find token from cookie");
        return Optional.empty();

    }

}
