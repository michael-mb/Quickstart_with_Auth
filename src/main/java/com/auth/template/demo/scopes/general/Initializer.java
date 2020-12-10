package com.auth.template.demo.scopes.general;


import com.auth.template.demo.scopes.user.entities.TestUser;
import com.auth.template.demo.scopes.user.entities.User;

import com.auth.template.demo.scopes.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Initializer {
    private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

    public UserService userService;

    @Autowired
    public Initializer(UserService userService,
                       final @Value("${spring.jpa.hibernate.ddl-auto}") String database ,
                       final @Value("${spring.profiles.active}") String profile){

        this.userService = userService;

        if(setupDemoUsers(database)){
                setupTestUsers();
        }
    }

    private void setupTestUsers() {
        LOG.info("Creating default Test Users.");
        User user ;
        for (TestUser testUser : TestUser.values()) {
            user = new User(testUser.firstName , testUser.lastName , testUser.email, testUser.pseudo,
                    testUser.password , testUser.grantedAuthorities , testUser.city);

            userService.generateAndSaveNewValidationTokenForUser(user);
            userService.rehashPassword(testUser.password , user);
        }
    }

    private boolean isProdProfile(String profile){
        return "prod".equals(profile);
    }
    private boolean setupDemoUsers(String database){
        return "create".equals(database);
    }

}
