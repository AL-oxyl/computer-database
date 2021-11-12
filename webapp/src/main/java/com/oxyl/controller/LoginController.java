package com.oxyl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oxyl.dto.AuthentificationRequest;
import com.oxyl.dto.AuthentificationResponse;
import com.oxyl.service.CustomUserDetailsService;
import com.oxyl.service.JwtService;

@RestController
public class LoginController {
	private AuthenticationManager authenticationManager;
    private CustomUserDetailsService customUserDetailsService;
    private JwtService jwtService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/*")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthentificationRequest authentificationRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authentificationRequest.getUsername(), authentificationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authentificationRequest.getUsername());
        final String jwt = this.jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthentificationResponse(jwt));
    }
}
