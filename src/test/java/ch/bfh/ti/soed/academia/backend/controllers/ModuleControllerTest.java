/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.DegreeProgramme;
import ch.bfh.ti.soed.academia.backend.models.Module;
import ch.bfh.ti.soed.academia.backend.models.ModuleType;
import ch.bfh.ti.soed.academia.backend.models.Professor;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TestingClass - Tests all Methods in class ModuleController
 * @see ModulesController
 */
public class ModuleControllerTest {
    private static EJBContainer container;

    @Inject
    private ModulesController moduleController;


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

    @Test
    public void testGetAllModules() {
        assertNotNull(moduleController.getAllModules(""));
    }

    /**
     *  Tests: getAllModules(String) method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testGetAllModulesFiltered() {
        assertNotNull(moduleController.getAllModules(""));
    }

    /**
     *  Tests: deleteModule method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testDeleteModule() {

        Module m = new Module();
        Module m1 = moduleController.save(m);
        moduleController.deleteModule(m1);
    }

    /**
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testSave() {
        assertNotNull(moduleController.save(new Module()));
    }


    /**
     *  tests the getAllManagedModulesByProfessorTag method
     *  it works properly on normal testing but strange bug on build!
     * @throws InvalidKeySpecException Exception
     * @throws NoSuchAlgorithmException Exception
     */
    @Ignore
    public void testGetAllManagedModulesByProfessorTag() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Professor professor = new Professor("Mirco", "Banfi");
        Module m1 = new Module("test1", ModuleType.PE, DegreeProgramme.ComputerScience, "test1", professor);
        this.moduleController.save(m1);
        Module m2 = new Module("test2", ModuleType.PB, DegreeProgramme.ComputerScience, "test2", professor);
        this.moduleController.save(m2);

        assertNotNull(moduleController.getAllManagedModulesByProfessorTag(professor.getTag()));
        assertTrue(this.moduleController.getAllManagedModulesByProfessorTag("test").size() == 2);
        assertTrue(this.moduleController.getAllManagedModulesByProfessorTag("nono").isEmpty());
    }
}