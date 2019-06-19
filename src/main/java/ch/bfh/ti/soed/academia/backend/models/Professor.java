/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.models;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Query for finding Professors in DB
 *
 * TODO: This is the place for the 'class' comment, i.e., a description of what
 * this class does. It's not the place to comment the query.
 */
@NamedQueries({
	@NamedQuery(name = "Professor.findAll", query = "SELECT p FROM Professor AS p"),
	@NamedQuery(name = "Professor.findByPattern", query = "SELECT p FROM Professor AS p "
			+ "WHERE lower(p.firstName) LIKE CONCAT('%', lower(?1), '%') OR lower(p.lastName) LIKE CONCAT('%', lower(?1), '%')"),
	@NamedQuery(name = "Professor.findByTag", query = "SELECT p FROM Professor AS p "
			+ "WHERE p.tag LIKE ?1")
})

@Entity
public class Professor extends User {

	private String firstName = "";

	private String lastName = "";

	@ManyToMany(cascade = CascadeType.MERGE)
	private Set<ModuleRun> moduleRuns;

	/**
	 * Empty constructor required by JPA
	 */
	public Professor() {
	}

	/**
	 * Default constructor for the Professor class
	 * @param firstName First name of professor String
	 * @param lastName  Last name of professor  String
	 * @throws InvalidKeySpecException invalidKeySpecException
	 * @throws NoSuchAlgorithmException noSuchALgorithmException
	 */
	public Professor(String firstName, String lastName) throws InvalidKeySpecException, NoSuchAlgorithmException {
		//super(generateTag(firstName, lastName), PasswordGenerator.generatePassword(15), Role.PROFESSOR);
		super(generateTag(firstName, lastName), "hello", Role.PROFESSOR);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Constructor for the Professor class
	 * NOT CURRENTLY USED
	 * @param firstName     First name of professor                                 String
	 * @param lastName      Last name of professor                                  String
	 * @param moduleRuns    Set of ModuleRun objects to assign to this professor    String
	 */
	public Professor(String firstName, String lastName, Set<ModuleRun> moduleRuns) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.moduleRuns = moduleRuns;
	}

	/**
	 * Get ID of User
	 * @return id   Long
	 */
	@Override
	public Long getId() {
		return super.id;
	}

	/**
	 * Get the value of lastName
	 *
	 * @return the value of last name
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * Set the value of lastName
	 *
	 * @param lastName new value of last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the value of firstName
	 *
	 * @return the value of first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set the value of firstName
	 *
	 * @param firstName new value of first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the value of tag
	 *
	 * @return the value of tag
	 */
	@Override
	public String getTag() {
		return tag;
	}

	/**
	 * Get a Set of ModuleRun objects associated with this Professor
	 * @return Set of ModuleRun objects
	 */
	public Set<ModuleRun> getModuleRuns() {
		return moduleRuns;
	}

	/**
	 * Assign ModuleRun objects to this Professor
	 * @param moduleRuns    Set of ModuleRun
	 */
	public void setModuleRuns(Set<ModuleRun> moduleRuns) {
		this.moduleRuns = moduleRuns;
	}

	/**
	 * Returns the user role for this Professor object
	 * @return Role enum
	 */
	@Override
	public Role getRole() {
		return super.role;
	}

	/**
	 * Get professor's email address
	 * @return Email address as String
	 */
	public String getEmail() {
		return super.email;
	}

}
