package account.controller;

import account.exception.BadRequest;
import account.exception.EmailAlreadyUsed;
import account.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NameNotFoundException;
import java.util.Arrays;

@RestController
public class AccountController {

    private final UserRepository repository;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AccountController(UserRepository repository, UserDetailsServiceImpl userDetailsService) {
        this.repository = repository;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        String[] listUser = new String[] {userDTO.getName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPassword()};
        System.out.println(Arrays.toString(listUser));
        for (String string : listUser) {
            if (string == null || string.isEmpty()) {
                throw new BadRequest();
            }
        }

        if (!userDTO.getEmail().matches("^[A-Za-z0-9_.-]+@acme\\.com$")) {
            throw new BadRequest();
        } else if (repository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsed();
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        repository.save(user);


        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity<?> getPayment(@AuthenticationPrincipal UserDetails userDetails) throws NameNotFoundException {
        UserDetails user = userDetailsService.loadUserByEmail(userDetails.getEmail());
        System.out.println(user);
        return ResponseEntity.ok().body("s");
    }

}














