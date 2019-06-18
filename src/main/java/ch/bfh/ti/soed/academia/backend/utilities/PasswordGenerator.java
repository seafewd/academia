/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.utilities;


import java.security.SecureRandom;

/**
 * class for generating a password for Users
 */
public class PasswordGenerator {
    private static SecureRandom random = new SecureRandom();

    /**
     * Generates a random string based on the the length and dictionary used. For now, hardcoded dictionary
     *
     * @param len   the length of the random string
     * @return the random password
     */
    public static String generatePassword(int len) {
        String dic = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_=+-/";
        String result = "";
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(dic.length());
            result += dic.charAt(index);
        }
        return result;
    }

}
