/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.Student;
import ch.bfh.ti.soed.academia.backend.models.StudentStatus;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class StudentController
 * @see StudentsController
 */
public class StudentControllerTest {
    private static EJBContainer container;

    @Inject
    private StudentsController studentController;

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

    //method thats tested is not implemented yet
    /*
    @Test
    public void testGetAllStudents() {

        //assertNotNull(studentController.getAllStudents());
    }
    */

    /**
     *  Tests: getAllStudents(String) method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testGetAllStudentsFiltered() {
        assertNotNull(studentController.getAllStudents("Zebra"));
    }

    /**
     *  Tests: deleteStudent method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testDeleteStudent() {
        Student s = new Student();
        Student s1 = studentController.save(s);
        studentController.deleteStudent(s1);
    }

    /**
     *  Tests: getStudentByTag method
     *
     */
    @Test
    public void testGetStudentByTag() {
        try{
        Student s = new Student("Pietro", "Buongiorno", StudentStatus.Enrolled);
        Student s1 = studentController.save(s);
        assertEquals(s1,this.studentController.getStudentByTag(s1.getTag()));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     *  Tests: save method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testSave() {
        assertNotNull(studentController.save(new Student()));
    }

    /**
     *  Tests: getAllStudentsID method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testGetAllStudentIDs(){ assertNotNull(studentController.getAllStudentIDs()); }
}