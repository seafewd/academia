/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.backend.models.Module;
import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests all Methods in class ModuleRunController
 * @see ModuleRunsController
 */
public class ModuleRunControllerTest {
    private static EJBContainer container;

    @Inject
    private ModuleRunsController moduleRunsController;

    @Inject
    private StudentsController studentsController;

    @Inject
    private ProfessorController professorController;

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
     *  Tests: getAllModuleRuns method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testGetAllModuleRuns() {
        moduleRunsController.getAllModuleRuns("");
    }

    /**
     *  Tests: getAllModuleRuns(String) method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testGetAllModuleRunsFiltered() {
        assertNotNull(moduleRunsController.getAllModuleRuns(""));
    }

    /**
     *  Tests: deleteModuleRun method
     *  only tests if the method works without errors,
     *  deeper tests in logic are made in ServiceTest-classes
     */
    @Test
    public void testDeleteModuleRun() {
        ModuleRun m = new ModuleRun();
        ModuleRun mR1 = moduleRunsController.save(m);
        moduleRunsController.deleteModuleRun(mR1);
    }

    @Test
    public void testSave() {
        assertNotNull(moduleRunsController.save(new ModuleRun()));
    }

    @Test
    public void testGetModulesRunNotSubscribed() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Student student = new Student("Erik", "Misko", StudentStatus.Enrolled);
        student = this.studentsController.save(student);
        ModuleRun moduleRun1 = new ModuleRun(new Module(), Semester.FS2019);
        this.moduleRunsController.save(moduleRun1);
        ModuleRun moduleRun2 = new ModuleRun(new Module(), Semester.FS2019);
        this.moduleRunsController.save(moduleRun2);

        Enrollment e1 = new Enrollment(moduleRun2, student, Evaluation.NYE);
        Set<Enrollment> eset = new HashSet<>();
        eset.add(e1);
        //this.moduleRunsController.subscribe(student, moduleRun1);
        moduleRun2.setEnrollments(eset);
        this.moduleRunsController.save(moduleRun2);
        assertNotNull(this.moduleRunsController.getModulesRunNotSubscribed(student.getTag()));
    }

    @Test
    public void testGetModulesRunByProfessorTag() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Professor professor = new Professor("Mich", "benassi");
        professor = this.professorController.save(professor);
        ModuleRun moduleRun1 = new ModuleRun(new Module(), Semester.FS2019);
        moduleRun1.addProfessor(professor);
        this.moduleRunsController.save(moduleRun1);
        ModuleRun moduleRun2 = new ModuleRun(new Module(), Semester.FS2019);
        moduleRun2.addProfessor(professor);
        this.moduleRunsController.save(moduleRun2);

        this.moduleRunsController.getModulesRunByProfessorTag(professor.getTag());

    }
    @Test
    public void testSubscribe() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Student student = new Student("Erik", "Meier", StudentStatus.Enrolled);
        student = this.studentsController.save(student);
        ModuleRun moduleRun = new ModuleRun(new Module(), Semester.FS2019);
        moduleRun = this.moduleRunsController.save(moduleRun);
        this.moduleRunsController.subscribe(student,moduleRun);
        assertNotNull(this.enrollmentsController.getAllEnrollmentsByStudentTag(student.getTag()));

    }
}