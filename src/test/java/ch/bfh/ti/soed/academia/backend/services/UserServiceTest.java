/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import ch.bfh.ti.soed.academia.backend.models.Professor;
import ch.bfh.ti.soed.academia.backend.models.Student;
import ch.bfh.ti.soed.academia.backend.models.StudentStatus;
import org.junit.jupiter.api.*;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

/**
 * TestingClass - Tests all Methods in class UserService
 * @see UserService
 */
public class UserServiceTest {
    private static EJBContainer container;

    @Inject
    private UserService userService;
    @Inject
    private StudentService studentService;
    @Inject
    private ProfessorService professorService;

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
     *  Tests: findAll method
     */
    @Test
    public void testFindAll() {
        this.userService.findAll();
    }
    /**
     *  Tests: findByTag method
     */
    @Test
    public void testFindByTag() {
        try {
            Student student = new Student("John", "Snow", StudentStatus.Enrolled);
            Student student1 = this.studentService.save(student);
            Assertions.assertEquals(student1, this.userService.findByTag(student1.getTag()));

            Professor professor = new Professor("Piero", "Pellegrini");
            Professor professor1 = this.professorService.save(professor);
            Assertions.assertEquals(professor1.getTag(), this.userService.findByTag(professor1.getTag()).getTag());

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
