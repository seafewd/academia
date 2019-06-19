/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.models;

import ch.bfh.ti.soed.academia.backend.controllers.ModulesController;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import static ch.bfh.ti.soed.academia.backend.models.DegreeProgramme.ComputerScience;
import static ch.bfh.ti.soed.academia.backend.models.ModuleType.PE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class Module
 * @see Module
 */
public class ModuleTest {
    private static EJBContainer container;

    @Inject
    private ModulesController modulesController;

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
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    @Test
    public void testGetSet() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Module module = new Module();

        //Test for method getName / setName:
        String name = "Sozialantropologische Forschung";
        module.setName(name);
        assertEquals(name, module.getName());

        //Test for method getModuleType / setModuleType:
        ModuleType moduleType = PE;
        module.setModuleType(moduleType);
        assertEquals(moduleType, module.getModuleType());

        //Test for method getCourseOfStudy / setCourseOfStudy:
        DegreeProgramme degreeProgramme = ComputerScience;
        module.setCourseOfStudy(degreeProgramme);
        assertEquals(degreeProgramme, module.getCourseOfStudy());

        //Test for method getDescription / setDescription:
        module.setDescription(name);
        assertEquals(name, module.getDescription());

        //Test for method getModuleRuns / setModuleRuns:
        ModuleRun moduleRun1 = new ModuleRun();
        ModuleRun moduleRun2 = new ModuleRun();
        List<ModuleRun> moduleRunList = new ArrayList<>();
        moduleRunList.add(moduleRun1);
        moduleRunList.add(moduleRun2);
        module.setModuleRuns(moduleRunList);
        assertEquals(moduleRunList, module.getModuleRuns());

        //Test for method getModuleManager / setModuleManager:
        Professor professor = new Professor("A","B");
        module.setModuleManager(professor);
        assertEquals(professor, module.getModuleManager());

        //getManagerTag
        assertNotNull(module.getModuleManagerTag());

        module.setProfessor(professor);
        assertEquals(professor, module.getProfessor());
    }

    /**
     *  Tests: getIdAsString method
     */
    @Test
    public void getIdAsStringTest() {
        Module m = new Module();
        Module m1 = modulesController.save(m);
        String mId = Long.toString(m1.getId());
        assertEquals(mId,m1.getIdAsString());
    }
}