/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.User;
import ch.bfh.ti.soed.academia.backend.services.UserService;
import ch.bfh.ti.soed.academia.backend.utilities.PasswordHash;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * The login controller
 */
public class LoginController {

    @Inject
    private UserService userService;

    public LoginController() {
    }

    /**
     * Initialization method
     */
    @PostConstruct
    public void init(){}


    /**
     * @param username tag of the user
     * @param password password of the user
     * @return matching user for username/password combination
     * @throws InvalidKeySpecException exception
     * @throws NoSuchAlgorithmException exception
     */
    public User login(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        User user = this.userService.findByTag(username);
        if(user != null && PasswordHash.validatePassword(password, user.getPassword()))
            return user;
        return null;
    }
}
