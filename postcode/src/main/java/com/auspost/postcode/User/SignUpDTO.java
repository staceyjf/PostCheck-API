package com.auspost.postcode.User;

// using a record to enforce immutability (eg no setters)
// comes with built in methods
public record SignUpDTO(String login,
        String password,
        USERROLE role) {

}
