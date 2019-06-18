/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.ModuleRun;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class ModuleRunController
 * @see ModuleRunsController
 */
public class ModuleRunControllerTest {
    private static EJBContainer container;

    @Inject
    private ModuleRunsController moduleRunsController;


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
     *  Tests: getAllModuleRuns method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testGetAllModuleRuns() {
        moduleRunsController.getAllModuleRuns("");
    }

    /**
     *  Tests: getAllModuleRuns(String) method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testGetAllModuleRunsFiltered() {
        assertNotNull(moduleRunsController.getAllModuleRuns(""));
    }

    /**
     *  Tests: deleteModuleRun method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testDeleteModuleRun() {
        ModuleRun m = new ModuleRun();
        ModuleRun mR1 = moduleRunsController.save(m);
        moduleRunsController.deleteModuleRun(mR1);
    }

    @Test
    public void testSave() {
        assertNotNull(moduleRunsController.save(new ModuleRun()));
    }
}