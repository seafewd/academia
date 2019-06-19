/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.models;

import ch.bfh.ti.soed.academia.backend.controllers.ProfessorController;
import ch.bfh.ti.soed.academia.backend.controllers.StudentsController;
import org.junit.jupiter.api.*;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TestingClass - Tests all Methods in class User
 * @see User
 */
public class UserTest {
    private static EJBContainer container;

    @Inject
    User user;

    @Inject
    ProfessorController professorsController;

    @Inject
    StudentsController studentsController;

    /**
     * Start method, executed when this class is called
     */
    @BeforeAll
    public static void start() {
        container = EJBContainer.createEJBContainer();
    }

    /**
     * End method, executed when all tests are done
     */
    @AfterAll
    public static void stop() {
        container.close();
    }

    /**
     * StartTest method, executed right before each test
     * @throws NamingException namingException
     */
    // See: http://tomee.apache.org/developer/testing/other/index.html
    @BeforeEach
    public void inject() throws NamingException {
        container.getContext().bind("inject", this);
    }

    /**
     * EndTest method, executed right after each test
     * @throws NamingException namingException
     */
    @AfterEach
    public void reset() throws NamingException {
        container.getContext().unbind("inject");
    }


    /**
     * Tests: all getter and setter methods
     * @throws InvalidKeySpecException Exception
     * @throws NoSuchAlgorithmException Exception
     */
    @Test
    public void testGetSet() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Student student = new Student("Test", "Testy", StudentStatus.Enrolled);

        student.setVersion(123L);
        assertEquals(123L, (long)student.getVersion());

        student.setTag("halloFreund");
        assertEquals("halloFreund", student.getTag());
    }


    /**
     * Tests getRole method
     * @throws InvalidKeySpecException Exception
     * @throws NoSuchAlgorithmException Exception
     */
    @Test
    public void testGetRole() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Professor professor = new Professor("Testy", "McTestFace");
        Student student = new Student("Student", "McStudentFace", StudentStatus.Enrolled);
        Role role1 = professor.getRole();
        Role role2 = student.getRole();

        assertEquals(role1, Role.PROFESSOR);
        assertEquals(role2, Role.STUDENT);

    }
}
