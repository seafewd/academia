/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.models;

import ch.bfh.ti.soed.academia.backend.controllers.EnrollmentsController;
import ch.bfh.ti.soed.academia.backend.controllers.ModuleRunsController;
import ch.bfh.ti.soed.academia.backend.controllers.ModulesController;
import ch.bfh.ti.soed.academia.backend.controllers.ProfessorController;

import org.junit.jupiter.api.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;
import static ch.bfh.ti.soed.academia.backend.models.Semester.FS2018;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TestingClass - Tests all Methods in class ModuleRun
 * @see ModuleRun
 */
public class ModuleRunTest {
    private static EJBContainer container;

    @Inject
    private EnrollmentsController enrollmentsController;

    @Inject
    private ModulesController modulesController;

    @Inject
    private ModuleRunsController moduleRunsController;

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

    /**
     * Tests the counting of enrolled students in a module run. Duplicates not allowed
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    void testGetEnrollmentCount() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Professor professor1 = new Professor("Hardy", "Harr");
        Module module1 = new Module("BTI7081", ModuleType.PE, DegreeProgramme.ComputerScience, "Software Engineering & Design", professor1);
        ModuleRun moduleRun = new ModuleRun(module1, Semester.FS2018);
        Student student1 = new Student("Test", "Tester", StudentStatus.Enrolled);
        Student student2 = new Student("Test", "Tester", StudentStatus.Enrolled);
        Student student3 = new Student("Test", "Tester", StudentStatus.Enrolled);
        Student student4 = new Student("Test", "Tester", StudentStatus.Enrolled);
        Student student5 = new Student("Test", "Tester", StudentStatus.Enrolled);
        Enrollment enrollment1 = new Enrollment(moduleRun, student1, Evaluation.A);
        Enrollment enrollment2 = new Enrollment(moduleRun, student2, Evaluation.B);
        Enrollment enrollment3 = new Enrollment(moduleRun, student3, Evaluation.C);
        Enrollment enrollment4 = new Enrollment(moduleRun, student4, Evaluation.F);
        Enrollment enrollment5 = new Enrollment(moduleRun, student5, Evaluation.B);
        Set<Enrollment> enrollments = new HashSet<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        enrollments.add(enrollment4);
        enrollments.add(enrollment5);
        enrollments.add(enrollment5);
        moduleRun.setEnrollments(enrollments);
        assertEquals(5, moduleRun.getEnrollmentCount());
    }

    /**
     * Tests: all getter and setter methods
     */
    @Test
    public void testGetSet() {
        ModuleRun moduleRun = new ModuleRun();
        ModuleRun mR = moduleRunsController.save(moduleRun);
        String mRid = Long.toString(mR.getId());
        //getID as String:
        assertEquals(mRid, mR.getIdAsString());

        //Test for method getModule / setModule:
        Module module = new Module();
        moduleRun.setModule(module);
        assertEquals(module, moduleRun.getModule());

        //Test for method getSemester / setSemester:
        Semester semester = FS2018;
        moduleRun.setSemester(semester);
        assertEquals(semester, moduleRun.getSemester());

        //Test for method getEnrollments / setEnrollments:
        Enrollment e1 = new Enrollment();
        Enrollment e2 = new Enrollment();

        Enrollment enrollment1 = enrollmentsController.save(e1);
        Enrollment enrollment2 = enrollmentsController.save(e2);

        //enrollment1.setId((long) 111);
        //enrollment2.setId((long) 222);
        Set<Enrollment> enrollmentSet = new HashSet<>();
        enrollmentSet.add(enrollment1);
        enrollmentSet.add(enrollment2);
        moduleRun.setEnrollments(enrollmentSet);
        assertEquals(enrollmentSet, moduleRun.getEnrollments());

        //Test for method getProfessors // setProfessors:
        Professor professor1 = new Professor();
        Professor professor2 = new Professor();
        Set<Professor> professorSet = new HashSet<>();
        professorSet.add(professor1);
        professorSet.add(professor2);
        moduleRun.setProfessors(professorSet);
        assertEquals(professorSet, moduleRun.getProfessors());

        //getEnrollmentsIDs
        ModuleRun moduleRun3 = new ModuleRun();
        moduleRun3.setEnrollments(enrollmentSet);
        String test = moduleRun3.getEnrollmentIDs();
        String id1 = Long.toString(enrollment1.getId());
        String id2 = Long.toString(enrollment2.getId());
        assertTrue(test.equals(id1+", "+id2) || test.equals(id2+", "+id1));

        //getModuleId
        Module m = new Module();
        Module m1 = modulesController.save(m);
        long mId = m1.getId();
        m1.setName("A");
        moduleRun.setModule(m1);
        moduleRun.setName();
        assertEquals(mId, (long)moduleRun.getModuleID());

        //getModuleRunName
        String s = Semester.FS2018.toString();
        assertEquals("A-"+s, moduleRun.getName());
    }

    /**
     * Tests: the Constructor
     */
    @Test
    public void ModuleRun() {
        Set<Enrollment> eSet = new HashSet<Enrollment>();
        Set<Professor> pSet = new HashSet<Professor>();

        ModuleRun moduleRun = new ModuleRun(new Module(), Semester.FS2018, eSet, pSet);
        assertNotNull(moduleRun);
    }


    /**
     * @throws InvalidKeySpecException Exception
     * @throws NoSuchAlgorithmException Exception
     * Tests the GetProfessorLastNames and getProfessorIDs methods
     */
    @Test
    public void testGetProfessorsLastNamesAndId() throws InvalidKeySpecException, NoSuchAlgorithmException {

        Professor p2 = new Professor("Hans", "Meier");
        p2 = this.professorController.save(p2);
        Module m1 = new Module("BTI2020", ModuleType.PE, DegreeProgramme.ComputerScience, "Analysis", p2);
        m1 = this.modulesController.save(m1);

        Professor p3 = new Professor("Martin", "Suter");
        p3 = this.professorController.save(p3);
        Professor p4 = new Professor("Sepp", "Hofer");
        p4 = professorController.save(p4);

        Set<Professor> professorSet = new HashSet<>();
        professorSet.add(p3);
        professorSet.add(p4);

        ModuleRun mr1 = new ModuleRun(m1, Semester.FS2018);
        mr1.setProfessors(professorSet);
        mr1 = this.moduleRunsController.save(mr1);

        String as1 = p4.getLastName() + ", " + p3.getLastName();
        String as2 = p3.getLastName() + ", " + p4.getLastName();

        assertTrue(as1.equals(mr1.getProfessorLastNames()) || as2.equals(mr1.getProfessorLastNames()));

        String as3 = p4.getId().toString() + ", " + p3.getId().toString();
        String as4 = p3.getId().toString() + ", " + p4.getId().toString();

        assertTrue(as3.equals(mr1.getProfessorIDs()) || as4.equals(mr1.getProfessorIDs()));


    }
}