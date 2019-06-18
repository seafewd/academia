/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import ch.bfh.ti.soed.academia.backend.models.ModuleRun;
import ch.bfh.ti.soed.academia.backend.models.Professor;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class ProfessorService
 * @see ProfessorService
 */
public class ProfessorServiceTest {
    private static EJBContainer container;

    @Inject
    private ProfessorService service;

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
        Professor p = new Professor();
        p.setFirstName("John");
        p.setLastName("Wayne");

        ModuleRun mr1 = new ModuleRun();
        ModuleRun mr2 = new ModuleRun();
        HashSet<ModuleRun> moduleRuns = new HashSet<>();
        moduleRuns.add(mr1);
        moduleRuns.add(mr2);

        p.setModuleRuns(moduleRuns);

        Professor newProfessor = service.save(p);
        assertNotNull(newProfessor);
        assertNotNull(newProfessor.getId());
        assertEquals("John", newProfessor.getFirstName());
        assertEquals("Wayne", newProfessor.getLastName());
    }

    /**
     *  Tests: findAll method
     */
    @Test
    public void testFindAll() {
        int count = service.findAll().size();
        service.save(new Professor());
        service.save(new Professor());
        assertEquals(count + 2, service.findAll().size()); //all new saved Professors (so in this case 2) are found
        assertEquals(service.findAll().size(), service.findAll("").size()); //Pattern is empty -> all items should be found
        assertEquals(service.findAll().size(), service.findAll(null).size()); //Pattern is null -> all items should be found


    }

    /**
     *  Tests: findAll(String) method
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    @Test
    public void testFindAllFiltered() throws InvalidKeySpecException, NoSuchAlgorithmException {
        int count2 = service.findAll("Zebra").size();
        service.save(new Professor("Zebra","Test", null));
        assertEquals(count2 + 1, service.findAll("Zebra").size()); //all new saved Students with Zebra (so in this case 1) should be found
    }


    /**
     *  Tests: testDelete method
     *  requires that save and findAll is working
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    @Test
    public void testDelete() throws InvalidKeySpecException, NoSuchAlgorithmException {

        Professor professor = new Professor("Monu", "Menthe");
        Professor p = service.save(professor);

        int count = service.findAll().size();

        service.delete(p);
        assertEquals(count-1, service.findAll().size());
    }
}