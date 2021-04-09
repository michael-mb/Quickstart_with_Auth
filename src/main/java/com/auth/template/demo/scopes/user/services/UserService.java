package com.auth.template.demo.scopes.user.services;


import com.auth.template.demo.scopes.auth.forms.SignUpDto;
import com.auth.template.demo.scopes.token.TokenServiceImpl;
import com.auth.template.demo.scopes.user.entities.User;
import com.auth.template.demo.scopes.user.entities.UserRole;
import com.auth.template.demo.scopes.user.forms.UserRegistrationDto;
import com.auth.template.demo.scopes.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public TokenServiceImpl tokenService;

    @Autowired
    public  PasswordEncoder passwordEncoder;


    public void saveUser(final User user){
        if(user == null) throw new NullPointerException("User must not be null");
        userRepository.save(user);
    }

    public void generateAndSaveNewValidationTokenForUser(final User user) {
        if (user == null) throw new NullPointerException("User must not be null.");
        String token = tokenService.createToken(user);
        user.setValidationToken(token);
        saveUser(user);
    }


    public void updatePassword(final String newPassword, final User user) {
        if (newPassword == null) throw new NullPointerException("NewPassword must not be null.");
        if (newPassword.isEmpty()) throw new IllegalArgumentException("NewPassword must not be empty.");
        if (user == null) throw new NullPointerException("User must not be null.");

        user.hashedPassword = passwordEncoder.encode(newPassword);
        saveUser(user);
    }

    public void addUser(SignUpDto signUpDto){
        if (signUpDto == null) throw new NullPointerException("signUpDto must not be null.");

        User user = new User(signUpDto.firstName, signUpDto.lastName, signUpDto.email, signUpDto.pseudo,
                signUpDto.password, new HashSet<UserRole>(Arrays.asList(UserRole.USER)),
                signUpDto.city);

        generateAndSaveNewValidationTokenForUser(user);
        rehashPassword(user.hashedPassword , user);
    }
    public void rehashPassword(final String password, final User user) {
        this.updatePassword(password, user);
    }

    public boolean isPasswordCorrect(final String password, final User user) {
        if (password == null) throw new NullPointerException("Password must not be null.");
        if (password.isEmpty()) throw new IllegalArgumentException("Password must not be empty.");
        if (user == null) throw new NullPointerException("User must not be null.");

        return passwordEncoder.matches(password, user.getHashedPassword());
    }

    public Optional<User> findUserByEmail(String email) {
        if (email == null) throw new NullPointerException("EMail must not be null.");
        if (email.isEmpty()) throw new NullPointerException("EMail must not be empty.");

        return userRepository.findFirstByEmail(email.toLowerCase().trim());
    }

    public Optional<User> findUserById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId;
    }

    public boolean doesEmailAlreadyExists(final UserRegistrationDto userRegistrationDto) {
        if (userRegistrationDto == null) throw new NullPointerException("UserRegistrationDto must not be null.");

        return doesEmailAlreadyExists(userRegistrationDto.email);
    }

    public boolean doesEmailAlreadyExists(final String email) {
        if (email == null) throw new NullPointerException("Email must not be null.");
        if (email.isEmpty()) throw new NullPointerException("Email must not be empty.");

        return findUserByEmail(email).isPresent();
    }

    public boolean doesPseudolAlreadyExists(final String pseudo) {
        if (pseudo == null) throw new NullPointerException("Pseudo must not be null.");
        if (pseudo.isEmpty()) throw new NullPointerException("Pseudo must not be empty.");

        return userRepository.findUsersByPseudo(pseudo).isPresent();
    }

    public Set<User> searchAllUsersWith(String search){
        Set<User> users = new HashSet<>();

        for(String str : search.trim().split(" ") ) {
            if(!str.isEmpty()){
                users.addAll(userRepository.findUsersByEmailContaining(str.toLowerCase()));
                users.addAll(userRepository.findUsersByFirstnameContaining(str.toLowerCase()));
                users.addAll(userRepository.findUsersByLastnameContaining(str.toLowerCase()));

                users.addAll(userRepository.findUsersByEmailContaining(str.toUpperCase()));
                users.addAll(userRepository.findUsersByFirstnameContaining(str.toUpperCase()));
                users.addAll(userRepository.findUsersByLastnameContaining(str.toUpperCase()));

                users.addAll(userRepository.findUsersByEmailContaining(capitalize(str)));
                users.addAll(userRepository.findUsersByFirstnameContaining(capitalize(str)));
                users.addAll(userRepository.findUsersByLastnameContaining(capitalize(str)));
            }
        }
        return users;
    }

    private String capitalize(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
