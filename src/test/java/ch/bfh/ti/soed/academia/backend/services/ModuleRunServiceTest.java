/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import ch.bfh.ti.soed.academia.backend.models.*;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class ModuleRunService
 * @see ModuleRunService
 */
public class ModuleRunServiceTest {
    private static EJBContainer container;

    @Inject
    private ModuleRunService service;

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
     *  Tests:  save method
     */
    @Test
    public void testSave() {
        //Test for method save:
        ModuleRun m = new ModuleRun();
        m.setSemester(Semester.FS2019);
        ModuleRun newModuleRun = service.save(m);
        assertNotNull(newModuleRun);
        assertNotNull(newModuleRun.getId());
        assertEquals(newModuleRun.getSemester(), Semester.FS2019);
    }

    /**
     *  Tests: findAll method
     */
    @Test
    public void testFindAll() {
        int count = service.findAll().size();
        service.save(new ModuleRun());
        service.save(new ModuleRun());
        assertEquals(count + 2, service.findAll().size()); //all new saved ModuleRuns (so in this case 2) are found
    }

    /**
     *  Tests: findAll(String) method
     */
    @Test
    public void testFindAllFiltered() {
        int count = service.findAll("").size();

        ModuleRun moduleRun = new ModuleRun(new Module(), Semester.FS2018); //add the filter key in this line by defining the attribute that can be filtered
        service.save(moduleRun);
        assertEquals(count+1, service.findAll("").size()); //change the filter key, if its filtering by id make actual to 1

        assertEquals(service.findAll().size(), service.findAll("").size()); //Pattern is empty -> all items should be found
        assertEquals(service.findAll().size(), service.findAll(null).size()); //Pattern is null -> all items should be found

        int count1 = service.findAll().size();
        ModuleRun moduleRun1 = new ModuleRun(new Module(), Semester.FS2018); //add the filter key in this line by defining the attribute that can be filtered
        service.save(moduleRun1);
        assertEquals(count1 + 1, service.findAll().size());
    }

    /**
     *  Tests: testDelete method
     *  requires that save and findAll is working
     */
    @Test
    public void testDelete() {
        ModuleRun mR = new ModuleRun(new Module(), Semester.FS2018);
        ModuleRun mR1 = service.save(mR);

        int count = service.findAll().size();

        service.delete(mR1);
        assertEquals(count-1, service.findAll().size());
     }
}
