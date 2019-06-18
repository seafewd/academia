/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.models;

import ch.bfh.ti.soed.academia.backend.utilities.PasswordGenerator;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

/**
 * The student entity.
 */
@SuppressWarnings("serial")
@NamedQueries({ @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student AS s"),
		@NamedQuery(name = "Student.findByPattern", query = "SELECT s FROM Student AS s "
				+ "WHERE s.firstName LIKE ?1 OR s.lastName LIKE ?1"),
		@NamedQuery(name = "Student.findByTag", query = "SELECT s FROM Student AS s "
				+ "WHERE s.tag LIKE ?1 ")})
@Entity
public class Student extends User implements Serializable {

	private String firstName = "";

	private String lastName = "";

	private StudentStatus status;


	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	private Set<Enrollment> enrollments;

	/**
	 * JPA-required no-argument constructor. Do not use.
	 */
	public Student() {
		super();
	}

	/**
	 * Creates student instance. This instance is not JPA-managed in its first
	 * place, i.e., its id is null. Only when managed is its id not null.
	 *
	 * @param firstName the first name of a student
	 * @param lastName  the last name of a student
	 * @param status    the student's status
	 * @throws InvalidKeySpecException invalidKeySpecException
	 * @throws NoSuchAlgorithmException noSuchAlgorithmException
	 */
	public Student(String firstName, String lastName, StudentStatus status) throws InvalidKeySpecException, NoSuchAlgorithmException {
		//super(generateTag(firstName, lastName), "pazzo", Role.STUDENT);
		super(generateTag(firstName, lastName), PasswordGenerator.generatePassword(15), Role.STUDENT);
        this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
    }

	/**
	 * Get the value of status
	 *
	 * @return the value of status
	 */
	public StudentStatus getStatus() {
		return status;
	}

	/**
	 * Set the value of status
	 *
	 * @param status new value of status
	 */
	public void setStatus(StudentStatus status) {
		this.status = status;
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

	public boolean isManaged() {
		return this.getId() != null;
	}

	/**
	 * Checks if the object "obj" is an object of the class Student
	 * @param obj any possible object
	 * @return true if obj is an object of the class student
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.getId() == null) {
			return false;
		}

		if (obj instanceof Student && obj.getClass().equals(getClass())) {
			return this.getId().equals(((Student) obj).getId());
		}

		return false;
	}

	/**
	 * Creates a hashcode out of the id
	 * @return the created hashcode
	 */
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (getId() == null ? 0 : getId().hashCode());
		return hash;
	}

	/**
	 * Creates a String out of some attributes of Student
	 * Example: [id=12, firstName=Jonas, lastName=Dover]
	 * @return a String out of some attributes of Student
	 */
	@Override
	public String toString() {
		return "Student [id=" + this.getId() + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	/**
	 * getIdAsString method
	 * @return id as String
	 */
	public String getIdAsString() {
		return String.valueOf(this.getId());
	}

	/**
	 *
	 * @return Set of all enrollments of the student
	 */
	public Set<Enrollment> getEnrollments() {
		return enrollments;
	}

	/**
	 * a given enrollment set is assigned to the student
	 * @param enrollments - a set of enrollments
	 */
	public void setEnrollments(Set<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	/**
	 *	getter for the name of an Enrollment
	 * @return the names of the enrollment set of the Student
	 */
	public String getEnrollmentsName(){
		String s = enrollments.stream().map(enrollment -> Long.toString(enrollment.getId())).collect(Collectors.joining(","));
		return String.format("[%s]", s);
	}

    /**
     * Returns the user role for this Student object
     * @return Role enum
     */
	public Role getRole() {
	    return super.role;
    }

    /**
     * Get student's email address
     * @return email address as String
     */
    public String getEmail() {
        return super.email;
    }
}
