package dev.yoon.basic_community;

import dev.yoon.basic_community.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class AutoLockUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Instant lastLogIn;
    // 한달
    private static final long ACCOUNT_LOCK_TRIGGER_TIME = 60 * 60 * 24 * 30;

    public AutoLockUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.lastLogIn = user.getLastLogin();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        /**
         * 30일 지나면 자동 USER LOCK
         */
        return lastLogIn.isBefore(Instant.now().plusSeconds(ACCOUNT_LOCK_TRIGGER_TIME));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
