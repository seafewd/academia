/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.backend.models.Module;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class ModuleService
 * @see ModuleService
 */
public class ModuleServiceTest {
    private static EJBContainer container;

    @Inject
    private ModuleService service;

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
     *  Tests: save method
     */
    @Test
    public void testSave() {
        Module m = new Module();
        m.setDescription("CIAONE");
        m.setName("Lineare Algebra");
        m.setCourseOfStudy(DegreeProgramme.ComputerScience);
        m.setModuleType(ModuleType.PE);
        Module newModule = service.save(m);
        assertNotNull(newModule);
        assertNotNull(newModule.getId());
        assertEquals("CIAONE", newModule.getDescription());
        assertEquals("Lineare Algebra", newModule.getName());
        assertEquals(DegreeProgramme.ComputerScience, newModule.getCourseOfStudy());
        assertEquals(ModuleType.PE, newModule.getModuleType());
    }

    /**
     *  Tests: findAll method
     */
    @Test
    public void testFindAll() {
        int count = service.findAll().size();
        service.save(new Module());
        service.save(new Module());
        assertEquals(count + 2, service.findAll().size()); //all new saved Modules (so in this case 2) are found
    }

    /**
     *  Tests: findAll(String) method
     */
    @Test
    public void testFindAllFiltered() {
        int count = service.findAll("").size();
        Module module = new Module("Wissenschaft", ModuleType.PE, DegreeProgramme.ComputerScience, "", null);
        service.save(module);
        assertEquals(count +1,service.findAll("").size());
        assertEquals(service.findAll().size(), service.findAll("").size());
        assertEquals(service.findAll().size(), service.findAll(null).size());
        assertEquals(1, service.findAll("Wissenschaft").size());
    }

    /**
     *  Tests: testDelete method
     *  requires that save and findAll is working
     */
    @Test
    public void testDelete(){
        Module m = new Module();
        Module m1 = service.save(m);

        int count = service.findAll().size();

        service.delete(m1);
        assertEquals(service.findAll().size(), count-1);
    }
}