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

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.util.HashSet;
import java.util.Set;
import static ch.bfh.ti.soed.academia.backend.models.Evaluation.C;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TestingClass - Tests all Methods in class Enrollment
 * @see Enrollment
 */
//Unused Imports may occur because some methods we will need in the next sprint are commented at the moment
public class EnrollmentTest {
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
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    @Test
    public void testGetSet() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Enrollment enrollment = new Enrollment();

        //Test for method getModuleRun / setModuleRun:
        ModuleRun moduleRun = new ModuleRun();
        enrollment.setModuleRun(moduleRun);
        assertEquals(moduleRun,enrollment.getModuleRun());

        //Test for method getStudent / setStudent:
        Student student = new Student("Christian","Berg",StudentStatus.Enrolled);
        enrollment.setStudent(student);
        assertEquals(student, enrollment.getStudent());

        //Test for method getEvaluation / setEvaluation:
        Evaluation evaluation = C;
        enrollment.setEvaluation(evaluation);
        assertEquals(evaluation, enrollment.getEvaluation());

        //Test for method getStudentID cant be tested (no setting for id possible)
        assertEquals(null, enrollment.getStudentID());

        //Specific Student getter Tests
        assertEquals("Christian", enrollment.getStudentFirstName());
        assertEquals("Berg", enrollment.getStudentLastName());
        assertEquals(StudentStatus.Enrolled, enrollment.getStudentStatus());

        //Test for getEnrollments method
        Set<Enrollment> sE = new HashSet<Enrollment>();
        moduleRun.setEnrollments(sE);
        enrollment.setModuleRun(moduleRun);
        assertEquals(sE, enrollment.getEnrollments());

        //Test getModuleRunName
        Module m1 = new Module("BBBB", ModuleType.PE, DegreeProgramme.ComputerScience, "Physics", new Professor());
        ModuleRun mr1 = new ModuleRun(m1, Semester.HS2018);
        Student s1 = new Student("Fritz", "Fischer", StudentStatus.Enrolled);
        Enrollment e1 = new Enrollment(mr1, s1, Evaluation.NYE);
        assertEquals("BBBB-HS2018", e1.getModuleRunName());
    }
}