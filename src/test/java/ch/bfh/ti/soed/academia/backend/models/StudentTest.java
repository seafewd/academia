/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.models;

import ch.bfh.ti.soed.academia.backend.controllers.EnrollmentsController;
import ch.bfh.ti.soed.academia.backend.controllers.StudentsController;
import ch.bfh.ti.soed.academia.backend.models.Student;
import ch.bfh.ti.soed.academia.backend.services.StudentService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;
import static ch.bfh.ti.soed.academia.backend.models.StudentStatus.Exmatriculated;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * TestingClass - Tests all Methods in class Student
 * @see Student
 */
public class StudentTest {
    private static EJBContainer container;

    @Inject
    private StudentsController studentController;

    @Inject
    private EnrollmentsController enrollmentsController;

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
     */
    @Test
    public void testGetSet() {
        Student student = new Student();

        //Test for method getStatus / setStatus:
        StudentStatus studentStatus = Exmatriculated;
        student.setStatus(studentStatus);
        assertEquals(studentStatus, student.getStatus());

        //Test for method getLastName / setLastName:
        String name = "Sappalotte";
        student.setLastName(name);
        assertEquals(name, student.getLastName());

        //Test for method getFirstName / setFirstName:
        student.setFirstName(name);
        assertEquals(name, student.getFirstName());

        //Test for method getEnrollments / Test for method setEnrollments:
        Enrollment enrollment1 = new Enrollment();
        Enrollment enrollment2 = new Enrollment();
        Set<Enrollment> enrollmentSet = new HashSet<>();
        enrollmentSet.add(enrollment1);
        enrollmentSet.add(enrollment2);
        student.setEnrollments(enrollmentSet);
        assertEquals(enrollmentSet, student.getEnrollments());

        //getSetRole
        student.setRole(Role.STUDENT);
        assertEquals(Role.STUDENT, student.getRole());
    }

    /**
     *  Tests: isManaged method
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    @Test
    public void testIsManaged() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Student student = new Student("Ron","Wisel",StudentStatus.Exmatriculated);
        Student student1 = studentController.save(student);
        assertFalse(student.isManaged());
        assertTrue(student1.isManaged());
    }

    /**
     *  Tests: equals method
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    @Ignore
    public void testEquals() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Student student = new Student();
        Student student1 = new Student("Tom", "Sawyer", StudentStatus.Exmatriculated);
        Student student2 = student1;

        Student s2 = studentController.save(student1);
        Student s3 = studentController.save(student2);

        assertEquals(true, student1.equals(student1));
        assertNotEquals(true, student.equals(student1));
        assertFalse(student.equals(new Module())); //testing giving a not equal object without an id
        assertFalse(s2.equals(new Module())); //testing giving a not equal object with an id

        System.out.println(s2.getId());
        System.out.println(s3.getId());
        assertFalse(s3.equals(s2)); //ids not equal
    }

    /**
     *  Tests: hashCode method
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    @Test
    public void testHashCode() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Student student = new Student();
        assertEquals(43 * 5, student.hashCode());

        Student student1 = new Student("Tom", "Sawyer", StudentStatus.Exmatriculated);
        Student s2 = studentController.save(student1);
        assertEquals(43 * 5, s2.hashCode()-s2.getId());
    }

    /**
     * Tests: getIdAsString method
     */
    @Test
    public void getIdAsStringTest(){
        Student student = new Student();
        assertEquals(String.valueOf(student.getId()), student.getIdAsString());
    }

    /**
     * Tests: toString method
     * @throws InvalidKeySpecException invalidKeySpecException
     * @throws NoSuchAlgorithmException noSuchALgorithmException
     */
    @Test
    public void toStringTest() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Student student = new Student("Tom", "Sawyer", StudentStatus.Enrolled);
        assertEquals(student.toString(),"Student [id=null, firstName=Tom, lastName=Sawyer]");
    }

    /**
     *  Tests: getEnrollments method
     */
    @Test
    public void testGetEnrollmentsNameTest(){
        Student student = new Student();
        Enrollment enrollment1 = new Enrollment(new ModuleRun(), new Student(), Evaluation.F);
        Enrollment enrollment2 = new Enrollment(new ModuleRun(), new Student(), Evaluation.F);
        Enrollment e1 = enrollmentsController.save(enrollment1);
        Enrollment e2 = enrollmentsController.save(enrollment2);
        String id1 = Long.toString(e1.getId());
        String id2 = Long.toString(e2.getId());
        Set<Enrollment> enrollmentSet = new HashSet<>();
        enrollmentSet.add(e1);
        enrollmentSet.add(e2);
        student.setEnrollments(enrollmentSet);
        String test = student.getEnrollmentsName();
        assertTrue(test.equals("["+id1+","+id2+"]") || test.equals("["+id2+","+id1+"]"));
    }
}