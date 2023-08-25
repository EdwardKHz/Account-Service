package account.controller;

import account.exception.*;
import account.model.User;
import account.model.UserDTO;
import account.model.UserRepository;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class AccountController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    String[] breachedPasswords = new String[] {"PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"};

    @Autowired
    public AccountController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
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

        for (String password : breachedPasswords) {
            if (userDTO.getPassword().equals(password)) {
                throw new BreachedPassword();
            }
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAuthority("ROLE_USER");

        repository.save(user);

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<?> getPayment(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(repository.findUserByEmail(userDetails.getUsername()));
    }

    @PostMapping("/api/auth/changepass")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String new_password) {
        User user = null;
        user = repository.findUserByEmail(userDetails.getUsername()).get();
        if (new_password.length() < 12) {
            throw new PasswordTooShort();
        } else if (passwordEncoder.matches(new_password,user.getPassword())) {
            throw new SamePassword();
        }
        for (String password : breachedPasswords) {
            if (new_password.equals(password)) {
                throw new BreachedPassword();
            }
        }
        user.setPassword(passwordEncoder.encode(new_password));
        repository.save(user);
        return ResponseEntity.ok().body(user);
    }

}

