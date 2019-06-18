/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.models;

import ch.bfh.ti.soed.academia.backend.utilities.PasswordHash;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

/**
 * Abstract User class used for authentication/authorization system
 * Extended by Student, Professor
 */
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Version
    protected Long version;

    @Column(unique = true)
    protected String tag;

    protected String password;
    protected Role role;
    protected String email;

    /**
     * empty constructor for User
     */
    public User() {
    }

    /**
     * constructor for User, tag password and role are setted
     * @param tag (String)
     * @param password (String)
     * @param role (Role)
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    public User(String tag, String password, Role role) throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.tag = tag + (new Random().nextInt(100) + 100);
        this.password = PasswordHash.createHash(password);
        this.role = role;
        setEmail(generateEmail());
    }

    /**
     * Get the User ID
     * @return ID (Long)
     */
    public Long getId() {
        return id;
    }

    /**
     * Get username
     * @return tag (String)
     */
    public String getTag() {
        return tag;
    }

    /**
     * Get password
     * @return password (String)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set tag
     * @param tag (String)
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Set password
     * @param password (String)
     */
    public void setPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.password = PasswordHash.createHash(password);
    }

    /**
     * Get version
     * @return version (Long)
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Set version
     * @param version (Long)
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * Get role
     * @return role (ENUM)
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Set role
     * @param role (Role)
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Generate a Tag
     *
     * @param firstName (String)
     * @param lastName (String)
     * @return tag (String)
     */
    public static String generateTag(String firstName, String lastName){
        return (lastName.substring(0, Math.min(lastName.length(), 4)) + firstName.substring(0, 1) + "1").toLowerCase().replace("-", "");
    }

    /**
     * Set email
     * @param email (String)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    private String generateEmail(){
        return this.getTag() + "@students.academia.ng.com";
    }
}
