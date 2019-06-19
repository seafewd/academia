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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class EnrollmentService
 * @see EnrollmentService
 */
public class EnrollmentServiceTest {
    private static EJBContainer container;

    @Inject
    private EnrollmentService service;

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
        Enrollment e = new Enrollment();
        e.setEvaluation(Evaluation.E);
        Enrollment newEnrollment = service.save(e);
        assertNotNull(newEnrollment);
        assertNotNull(newEnrollment.getId());
        assertEquals(Evaluation.E, newEnrollment.getEvaluation());
    }

    /**
     *  Tests: findAll method
     */
    @Test
    public void testFindAll(){
        int count = service.findAll().size();
        service.save(new Enrollment());
        service.save(new Enrollment());
        assertEquals(count + 2, service.findAll().size()); //all new saved Enrollments (so in this case 2) are found
    }


    /**
     *  Tests: findAll(String) method
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    @Test
    public void testFindAllFiltered() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String pattern = ""; //change if tag in enrollment is changed
        int count = service.findAll(pattern).size();
        Enrollment e = new Enrollment(new ModuleRun(), new Student("Testi", "Test", StudentStatus.Enrolled), Evaluation.E);
        service.save(e);
        assertEquals(count + 4, service.findAll().size()); //all new saved Enrollments with Zebra (so in this case 1) should be found

        //assertEquals(service.findAll().size(), service.findAll("").size()); //Pattern is empty -> all items should be found
        //assertEquals(service.findAll().size(), service.findAll(null).size()); //Pattern is null -> all items should be found
    }

    /**
     *  Tests: findByID method
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    @Test
    public void testFindByID() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Enrollment en = new Enrollment(new ModuleRun(), new Student("Tebra", "Maier", StudentStatus.Enrolled), Evaluation.E);
        Enrollment en1 = new Enrollment(new ModuleRun(), new Student("Zebro", "Testo", StudentStatus.Enrolled), Evaluation.E);

        Enrollment e = service.save(en);
        Enrollment e1 = service.save(en1);

       long id1 = e.getId();
       long id2 = e1.getId();

        assertEquals(1, service.findById(id1).size());
        assertEquals(1, service.findById(id2).size());
        assertEquals(service.findAll().size(),service.findById(null).size());
    }

    /**
     *  Tests: testDelete method
     *  requires that save and findAll is working
     */
    @Test
    public void testDelete(){
        Enrollment e = new Enrollment(new ModuleRun(), new Student(), Evaluation.C);
        Enrollment e1 = service.save(e);

        int count = service.findAll().size();

        service.delete(e1);
        assertEquals(count-1, service.findAll().size());
    }
}