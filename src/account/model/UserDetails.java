package account.model;

import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;

import java.io.Serializable;

public interface UserDetails extends Serializable {

    String getPassword();

    String getName();

    String getEmail();

    String getLastName();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();
}
