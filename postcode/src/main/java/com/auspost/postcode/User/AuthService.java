package com.auspost.postcode.User;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auspost.postcode.exceptions.ServiceValidationException;
import com.auspost.postcode.exceptions.ValidationErrors;

import jakarta.validation.Valid;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails user = repo.findByLogin(username); // custom lookup
        return user;
    }

    public UserDetails signUp(@Valid SignUpDTO data) throws ServiceValidationException {
        ValidationErrors errors = new ValidationErrors();
        try {
            // check to see if the username already exists
            if (data.login() != null && repo.findByLogin(data.login()) != null) {
                errors.addError("User", "Username already exists");
                throw new ServiceValidationException(errors);
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            User newUser = new User(data.login(), encryptedPassword, data.role());
            User savedUser = repo.save(newUser);
            fullLogsLogger.info("New user saved to the db");
            return savedUser;
        } catch (Exception ex) {
            fullLogsLogger.error("An error occurred when trying to sign up and create a new user in the db: ",
                    ex.getLocalizedMessage());
            errors.addError("User", "An error occurred during sign up. Please try again");
            throw new ServiceValidationException(errors);
        }
    }

    public List<User> findAllUsers() {
        return this.repo.findAll();
    }

    public Optional<User> findById(Long id) {
        Optional<User> foundUser = this.repo.findById(id);
        fullLogsLogger.info("Located User in db with ID: " + foundUser.get().getId());
        return foundUser;
    }
}
