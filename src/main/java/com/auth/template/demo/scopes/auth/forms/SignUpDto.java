package com.auth.template.demo.scopes.auth.forms;

import com.auth.template.demo.validation.Regexes;
import com.auth.template.demo.validation.ValidEmail;

import javax.validation.constraints.*;

public class SignUpDto {
    @NotBlank
    @Size(max=200)
    @ValidEmail
    public String email;

    @NotBlank
    @Size(max = 200, message = "{error.invalid.length}")
    @Pattern(regexp = Regexes.TEXT, message = "{error.invalid.field.text}")
    public String pseudo;

    @NotBlank
    @Size(max = 200, message = "{error.invalid.length}")
    @Pattern(regexp = Regexes.TEXT, message = "{error.invalid.field.text}")
    public String firstName;

    @NotBlank
    @Size(max = 200, message = "{error.invalid.length}")
    @Pattern(regexp = Regexes.TEXT, message = "{error.invalid.field.text}")
    public String lastName;

    @NotBlank
    @Size(max = 200, message = "{error.invalid.length}")
    @Pattern(regexp = Regexes.PASSWORD, message = "{error.registration.invalid.password}")
    public String password;

    @NotBlank
    @Size(max = 200, message = "{error.invalid.length}")
    @Pattern(regexp = Regexes.PASSWORD, message = "{error.registration.invalid.password}")
    public String passwordAgain;

    @NotBlank
    @Size(max = 200, message = "{error.invalid.length}")
    @Pattern(regexp = Regexes.TEXT, message = "{error.invalid.field.text}")
    public String city;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public String toString(){
        return "UserRegistrationDto{"+
                "email='" + email + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", Password='" + password + '\'' +
                ", PasswordAgain='" + passwordAgain + '\'' +
                ", city='" + city + '\'' +
                '}' ;
    }

}
