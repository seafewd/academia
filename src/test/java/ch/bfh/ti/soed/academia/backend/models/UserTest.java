/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.models;

import org.junit.jupiter.api.*;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TestingClass - Tests all Methods in class User
 * @see User
 */
public class UserTest {
    private static EJBContainer container;

    @Inject
    private User user;
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
     *  Tests: all getter and setter methods
     */
    @Test
    public void testGetSet() {
        User u = new Student();

        u.setVersion((long)123);
        assertEquals((long)123, (long)u.getVersion());


        u.setTag("halloFreund");
        assertEquals("halloFreund", u.getTag());
    }
}
