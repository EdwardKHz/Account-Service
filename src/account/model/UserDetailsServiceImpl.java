package account.model;

import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
    @Override
    public UserDetails loadUserByName(String name) throws NameNotFoundException {
        User user = repository.
                findUserByName(name)
                .orElseThrow(() -> new NameNotFoundException("Not found"));

        return new UserAdapter(user);
    }

    @Override
    public UserDetails loadUserByEmail(String email) throws NameNotFoundException {
        User user = repository.
                findUserByEmail(email)
                .orElseThrow(() -> new NameNotFoundException("Not found"));

        return new UserAdapter(user);
    }
}
