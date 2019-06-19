/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.Professor;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class ProfessorController
 * @see ProfessorController
 */
public class ProfessorControllerTest {
    private static EJBContainer container;

    @Inject
    private ProfessorController professorController;

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
    public void testGetAllProfessors() {
        assertNotNull(professorController.getAllProfessors());
    }

    /**
     *  Tests: getAllProfessors(String) method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testGetAllProfessorsFiltered() {
        assertNotNull(professorController.getAllProfessors("Zebra"));
    }

    /**
     *  Tests: getDeeleteProfessor method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testDeleteProfessor() {
        Professor professor = new Professor();
        Professor p = professorController.save(professor);
        professorController.deleteProfessor(p);
    }

    /**
     *  Tests: save method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testSave() {
        assertNotNull(professorController.save(new Professor()));
    }

    /**
     *  Tests: getProfessorByTag method
     *
     */
    @Test
    public void testGetProfessorByTag() {
        try{
            Professor p = new Professor("Pietro", "Buongiorno");
            Professor p1 = professorController.save(p);
            assertEquals(p1.getTag(),this.professorController.getProfessorByTag(p1.getTag()).getTag());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
