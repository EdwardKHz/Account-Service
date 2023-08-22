package account.model;

import javax.naming.NameNotFoundException;

public interface UserDetailsService {

    UserDetails loadUserByName(String name) throws NameNotFoundException;
    UserDetails loadUserByEmail(String email) throws NameNotFoundException;
}
