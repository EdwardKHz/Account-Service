package account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest() {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDate.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", "Bad Request");
        responseBody.put("path", "/purchase");

        return ResponseEntity.badRequest().body(responseBody);
    }

    @ExceptionHandler(EmailAlreadyUsed.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyUsed(EmailAlreadyUsed emailAlreadyUsed) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDate.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", "Bad Request");
        responseBody.put("message", "User exist!");
        responseBody.put("path", "/api/auth/signup");

        return ResponseEntity.badRequest().body(responseBody);
    }

    @ExceptionHandler(UnauthorizedRequest.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedRequest(UnauthorizedRequest unauthorizedRequest) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDate.now());
        responseBody.put("status", HttpStatus.UNAUTHORIZED.value());
        responseBody.put("error", "Unauthorized");
        responseBody.put("message", "");
        responseBody.put("path", "/api/empl/payment");

        return ResponseEntity.badRequest().body(responseBody);
    }

    @ExceptionHandler(PasswordTooShort.class)
    public ResponseEntity<Map<String, Object>> handlePasswordTooShort(PasswordTooShort passwordTooShort) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDate.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", "Bad Request");
        responseBody.put("message", "The password length must be at least 12 chars!");
        responseBody.put("path", "/api/auth/changepass");

        return ResponseEntity.badRequest().body(responseBody);
    }

    @ExceptionHandler(SamePassword.class)
    public ResponseEntity<Map<String, Object>> handleSamePassword(SamePassword samePassword) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDate.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", "Bad Request");
        responseBody.put("message", "The passwords must be different!");
        responseBody.put("path", "/api/auth/changepass");

        return ResponseEntity.badRequest().body(responseBody);
    }


    @ExceptionHandler(BreachedPassword.class)
    public ResponseEntity<Map<String, Object>> handleBreachedPassword(BreachedPassword breachedPassword) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDate.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", "Bad Request");
        responseBody.put("message", "The password is in the hacker's database!");
        responseBody.put("path", "/api/auth/changepass");

        return ResponseEntity.badRequest().body(responseBody);
    }
}
