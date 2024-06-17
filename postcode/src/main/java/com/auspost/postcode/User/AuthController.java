package com.auspost.postcode.User;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auspost.postcode.PostCode.PostCode;
import com.auspost.postcode.config.auth.TokenProvider;
import com.auspost.postcode.exceptions.ServiceValidationException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenProvider tokenService;

    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");

    // create a new user via request body
    @PostMapping("/signup")
    public ResponseEntity<UserDetails> signUp(@Valid @RequestBody SignUpDTO data)
            throws ServiceValidationException {
        UserDetails createdUser = authService.signUp(data);
        fullLogsLogger.info("sign up controller responded with a new user: " + createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // receive user details, authenticate via AuthManager and then generate a token
    @PostMapping("/signin")
    public ResponseEntity<JwtDTO> signIn(@Valid @RequestBody SignInDTO data)
            throws ServiceValidationException {
        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        Authentication authUser = authManager.authenticate(usernamePassword);
        fullLogsLogger.info("this is authUser " + authUser);
        String accessToken = tokenService.generateAccessToken((User) authUser.getPrincipal());
        fullLogsLogger.info("this is accessToken " + accessToken);
        JwtDTO JwtToken = new JwtDTO(accessToken); // wrap the token in a JWTDTO
        fullLogsLogger.info("this is JWToekn " + JwtToken);
        fullLogsLogger.info("JWT token provided in sign ui controller");
        return new ResponseEntity<>(JwtToken, HttpStatus.OK);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> allUsers = this.authService.findAllUsers();
        fullLogsLogger.info("findAllUsers Controller responded with all Users");
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> maybeUser = this.authService.findById(id);
        User foundUser = maybeUser.orElseThrow();
        fullLogsLogger.info("findUserById responses with the found postcode:" + foundUser);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

}