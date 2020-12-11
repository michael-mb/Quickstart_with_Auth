package com.auth.template.demo.scopes.user.entities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TestUser {

    USER_ONE("john" , "Doe" , "admin@admin.com" ,
            "johnny" , "admin0","dresden",
            new HashSet<UserRole>(Arrays.asList(UserRole.USER , UserRole.ADMIN , UserRole.MODERATOR))),

    USER_TWO("Michael" , "Jackson" , "mod@mod.com" ,
            "Mike237" , "moderator0","berlin",
            new HashSet<UserRole>(Arrays.asList(UserRole.USER , UserRole.MODERATOR)));


    public final String firstName;
    public final String lastName;
    public final String email;
    public final String pseudo;
    public final String password;
    public final String city;
    public final Set<UserRole> grantedAuthorities;
    TestUser(String firstName , String lastName , String email , String pseudo ,String password , String city,
             Set<UserRole> grantedAuthorities){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pseudo = pseudo;
        this.password = password;
        this.city = city;
        this.grantedAuthorities = grantedAuthorities;

    }
}