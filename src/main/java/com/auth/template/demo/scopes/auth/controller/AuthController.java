package com.auth.template.demo.scopes.auth.controller;


import com.auth.template.demo.scopes.auth.forms.SignUpDto;
import com.auth.template.demo.scopes.auth.payload.response.MessageResponse;
import com.auth.template.demo.scopes.auth.forms.LoginDto;
import com.auth.template.demo.scopes.auth.payload.response.JwtResponse;
import com.auth.template.demo.scopes.security.CustomAuthenticationProvider;
import com.auth.template.demo.scopes.security.StaticUtils;
import com.auth.template.demo.scopes.token.TokenServiceImpl;
import com.auth.template.demo.scopes.user.entities.User;
import com.auth.template.demo.scopes.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    CustomAuthenticationProvider authenticationProvider;

    @Autowired
    public UserService userService;

    @Autowired
    TokenServiceImpl tokenService;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid final LoginDto loginDto){

        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenService.createToken(user);

        List<String> roles = user.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        System.err.println(authentication.getPrincipal());

        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getId(),
                user.getPseudo(),
                user.getEmail(),
                roles,
                user.getFirstname(),
                user.getLastname(),
                user.getCity()
                ));
        }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody SignUpDto signUpDto) {

        if (userService.doesEmailAlreadyExists(signUpDto.getEmail()) ||
        userService.doesPseudolAlreadyExists(signUpDto.pseudo)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Pseudo or Email is already taken!"));
        }

        if ( !signUpDto.password.equals(signUpDto.passwordAgain)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Password and Password Again are different!"));
        }

        userService.addUser(signUpDto);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/logout")
    public boolean handleLogout(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            try {
                StaticUtils.logoutAndInvalidateSession();
            } catch (ServletException e) {
                logger.error("Error at log out of '" + authentication.getName() + "'.", e);
            }
        }
        return true;
    }
}
