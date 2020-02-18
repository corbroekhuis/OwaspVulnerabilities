package com.academy.training.controller;

import com.academy.training.model.User;
import com.academy.training.repository.UnsafeUserRepository;
import com.academy.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired UnsafeUserRepository userRepository;

    // Uncomment to use safe parameterized query
    // @Autowired UserRepository userRepository;

    @PostMapping(value = "/authentication/authenticate", produces = "application/json")
    public ResponseEntity<User> authenticate(
            @RequestParam(value = "user", required = true) final String user,
            @RequestParam(value = "password", required = true) final String password) {

        User authenticatedUser = userRepository.findUserByUserAndPasswordNamedParams( user, password);

        if( authenticatedUser != null){
            return ResponseEntity.ok(authenticatedUser) ;
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() ;
        }

    }

}

