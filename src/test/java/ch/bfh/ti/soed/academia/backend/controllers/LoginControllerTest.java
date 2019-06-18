/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.Student;
import ch.bfh.ti.soed.academia.backend.models.StudentStatus;
import ch.bfh.ti.soed.academia.backend.utilities.PasswordGenerator;
import ch.bfh.ti.soed.academia.backend.utilities.PasswordHash;
import org.junit.jupiter.api.*;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.persistence.NoResultException;

/**
 * TestingClass - Tests all Methods in class LoginController
 * @see LoginController
 */
public class LoginControllerTest {

    private static EJBContainer container;

    @Inject
    private LoginController loginController;

    @Inject
    private StudentsController studentsController;

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
     *  Tests: login method
     *  test the return null if the Username is not correct
     *  test the return null if the password is not correct
     *
     */
    @Test
    public void testLogin() {
        try {
            Student student = new Student("John", "Snow", StudentStatus.Enrolled);
            String psw = PasswordGenerator.generatePassword(5);
            student = this.studentsController.save(student);
            student.setPassword(psw);
            Assertions.assertEquals(null, this.loginController.login("Brata", "cic"));
            Assertions.assertEquals(null, this.loginController.login(student.getTag(), "cic"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
