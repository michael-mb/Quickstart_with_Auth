package com.auth.template.demo.scopes.test;

import com.auth.template.demo.scopes.user.entities.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority ('USER') or hasAuthority ('MODERATOR') or hasAuthority ('ADMIN')")
    public String userAccess() {
        System.err.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminAccess( HttpServletRequest request) {

        System.err.println(request.getUserPrincipal().getName());
        return "Admin Board.";
    }
}