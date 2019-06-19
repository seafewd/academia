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
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class Professor
 * @see Professor
 */
public class ProfessorTest {
    private static EJBContainer container;

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
        Professor professor = new Professor();

        //Test for method getLastName / setLastName:
        String name = "Sappalotte";
        professor.setLastName(name);
        assertEquals(professor.getLastName(), name);

        //Test for method getFirstName / setFirstName:
        professor.setFirstName(name);
        assertEquals(professor.getFirstName(), name);

        //Test for getTag/setTag:
        professor.setTag(name);
        assertEquals(professor.getTag(), name);

        //Test for method getModuleRuns / setModuleRuns:
        ModuleRun moduleRun1 = new ModuleRun();
        ModuleRun moduleRun2 = new ModuleRun();
        Set<ModuleRun> moduleRunSet = new HashSet<>();
        moduleRunSet.add(moduleRun1);
        moduleRunSet.add(moduleRun2);
        professor.setModuleRuns(moduleRunSet);
        assertEquals(professor.getModuleRuns(), moduleRunSet);

        //getSetRole
        professor.setRole(Role.PROFESSOR);
        assertEquals(Role.PROFESSOR, professor.getRole());
    }

    /**
     * Tests: the Constructor
     */
    @Test
    public void Professor() {
        Set<ModuleRun> sMR = new HashSet<>();
        Professor p = new Professor("Tom","Tennemann", sMR);
        assertNotNull(p);
    }
}