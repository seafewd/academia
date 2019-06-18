/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;


import ch.bfh.ti.soed.academia.backend.models.Enrollment;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class EnrollmentController
 * @see EnrollmentsController
 */
public class EnrollmentControllerTest {
    private static EJBContainer container;

    @Inject
    private EnrollmentsController enrollmentController;

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
     *  Tests: getAllEnrollments method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testGetAllEnrollments() {
        assertNotNull(enrollmentController.getAllEnrollments(""));
    }

    /**
     *  Tests: getEnrollmentsByID method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testGetEnrollmentsByID() {
        assertNotNull(enrollmentController.getEnrollmentById((long)12234));
    }

    /**
     *  Tests: deleteEnrollment method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testDeleteEnrollment() {
        Enrollment e = new Enrollment();
        Enrollment e1 = enrollmentController.save(e);
        enrollmentController.deleteEnrollment(e1);
    }

    /**
     *  Tests: save method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testSave() {
        assertNotNull(enrollmentController.save(new Enrollment()));
    }
}