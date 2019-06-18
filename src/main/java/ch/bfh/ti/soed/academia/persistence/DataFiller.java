/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.persistence;


import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.backend.services.*;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;

/**
 * This class acts as a database filler
 */
@Startup
@Singleton
@DependsOn({ "StudentService", "EnrollmentService", "ModuleRunService", "ProfessorService", "ModuleService" })
public class DataFiller {
    @Inject
    private StudentService studentService;

    @Inject
    private EnrollmentService enrollmentService;

    @Inject
    private ModuleRunService moduleRunService;

    @Inject
    private ModuleService moduleService;

    @Inject
    private ProfessorService professorService;

    private Repository repository;

    @PostConstruct
    /**
     * Initialization method
     */
    public void init() throws InvalidKeySpecException, NoSuchAlgorithmException {
        testDataCreation();
    }

    /**
     * Fills database with dummy data if DB is not empty
     */
    private void testDataCreation() throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.repository = new Repository();

        //Professors
        Professor professor1 = repository.createProfessor("Hardy", "Harr");
        Professor professor2 = repository.createProfessor("Bear", "Trapp");
        Professor professor3 = repository.createProfessor("Mister", "Love");
        Professor professor4 = repository.createProfessor("Ken", "Hurt");
        Professor professor5 = repository.createProfessor("Adam", "Bawmb");


        //Modules
        Module module1 = repository.createModule("BTI7081", ModuleType.PE, DegreeProgramme.ComputerScience, "Software Engineering & Design",professor1);
        Module module2 = repository.createModule("BZG1153", ModuleType.PE, DegreeProgramme.ComputerScience, "Analysis",professor3);
        Module module3 = repository.createModule("BTI7056", ModuleType.PB, DegreeProgramme.ComputerScience, "Databases",professor2);
        Module module4 = repository.createModule("BZG2312", ModuleType.PE, DegreeProgramme.Microtechnology, "Astronomy",professor5);

        //ModuleRuns
        ModuleRun moduleRun1 = repository.createModuleRun(module1, Semester.FS2018);
        ModuleRun moduleRun2 = repository.createModuleRun(module1, Semester.HS2018);
        ModuleRun moduleRun3 = repository.createModuleRun(module1, Semester.FS2019);
        ModuleRun moduleRun4 = repository.createModuleRun(module1, Semester.HS2018);

        //students
        Student student1 = repository.createStudent("Coco","McCocoFace",StudentStatus.Enrolled);
        Student student2 = repository.createStudent("Momi","Momidoomi",StudentStatus.Enrolled);
        Student student3 = repository.createStudent("Boaty","McBoatFace",StudentStatus.Registered);
        Student student4 = repository.createStudent("Michael","Hunt",StudentStatus.Exmatriculated);
        Student student5 = repository.createStudent("Sum-Ting","Wong",StudentStatus.Exmatriculated);
        Student student6 = repository.createStudent("Bang-Ding","Ow",StudentStatus.Enrolled);
        Student student7 = repository.createStudent("Ho-Lee","Fuk",StudentStatus.Enrolled);
        Student student8 = repository.createStudent("Jolly","Rollo-Coaster",StudentStatus.Enrolled);

        //Enrollments
        Enrollment enrollment1 = repository.createEnrollment(moduleRun1, student1, Evaluation.A);
        Enrollment enrollment2 = repository.createEnrollment(moduleRun2, student2, Evaluation.B);
        Enrollment enrollment3 = repository.createEnrollment(moduleRun4, student3, Evaluation.C);
        Enrollment enrollment4 = repository.createEnrollment(moduleRun2, student4, Evaluation.B);
        Enrollment enrollment5 = repository.createEnrollment(moduleRun2, student5, Evaluation.F);
        Enrollment enrollment6 = repository.createEnrollment(moduleRun2, student6, Evaluation.D);
        Enrollment enrollment7 = repository.createEnrollment(moduleRun3, student7, Evaluation.B);
        Enrollment enrollment8 = repository.createEnrollment(moduleRun1, student8, Evaluation.F);
        Enrollment enrollment9 = repository.createEnrollment(moduleRun2, student1, Evaluation.C);
        Enrollment enrollment10 = repository.createEnrollment(moduleRun3, student1, Evaluation.C);
        Enrollment enrollment11 = repository.createEnrollment(moduleRun3, student2, Evaluation.C);
        Enrollment enrollment12 = repository.createEnrollment(moduleRun4, student5, Evaluation.B);


        Set<Enrollment> enrollments1 = new HashSet<>();
        enrollments1.add(enrollment1);
        enrollments1.add(enrollment9);
        enrollments1.add(enrollment10);

        Set<Enrollment> enrollments2 = new HashSet<>();
        enrollments2.add(enrollment2);
        enrollments2.add(enrollment11);


        Set<Enrollment> enrollments3 = new HashSet<>();
        enrollments3.add(enrollment3);

        Set<Enrollment> enrollments4 = new HashSet<>();
        enrollments4.add(enrollment4);

        Set<Enrollment> enrollments5 = new HashSet<>();
        enrollments5.add(enrollment5);
        enrollments5.add(enrollment12);

        Set<Enrollment> enrollments6 = new HashSet<>();
        enrollments6.add(enrollment6);

        Set<Enrollment> enrollments7 = new HashSet<>();
        enrollments7.add(enrollment7);

        Set<Enrollment> enrollments8 = new HashSet<>();
        enrollments8.add(enrollment8);

        moduleRun1.setEnrollments(enrollments1);
        moduleRun2.setEnrollments(enrollments2);
        moduleRun3.setEnrollments(enrollments4);
        moduleRun4.setEnrollments(enrollments3);

        student1.setEnrollments(enrollments1);
        student2.setEnrollments(enrollments2);
        student3.setEnrollments(enrollments3);
        student4.setEnrollments(enrollments4);
        student5.setEnrollments(enrollments5);
        student6.setEnrollments(enrollments6);
        student7.setEnrollments(enrollments7);
        student8.setEnrollments(enrollments8);

        Set<ModuleRun> moduleRuns1 = new HashSet<>();
        moduleRuns1.add(moduleRun1);
        moduleRuns1.add(moduleRun2);
        moduleRuns1.add(moduleRun4);


        Set<ModuleRun> moduleRuns2 = new HashSet<>();
        moduleRuns2.add(moduleRun1);

        Set<ModuleRun> moduleRuns3 = new HashSet<>();
        moduleRuns3.add(moduleRun4);

        Set<ModuleRun> moduleRuns4 = new HashSet<>();
        moduleRuns4.add(moduleRun3);

        Set<ModuleRun> moduleRuns5 = new HashSet<>();
        moduleRuns5.add(moduleRun3);


        Set<Professor> professors1 = new HashSet<>();
        professors1.add(professor1);
        professors1.add(professor2);


        Set<Professor> professors2 = new HashSet<>();
        professors2.add(professor1);

        Set<Professor> professors3 = new HashSet<>();
        professors3.add(professor4);
        professors3.add(professor5);

        Set<Professor> professors4 = new HashSet<>();
        professors4.add(professor1);
        professors4.add(professor3);

        moduleRun1.setProfessors(professors1);
        moduleRun2.setProfessors(professors2);
        moduleRun3.setProfessors(professors3);
        moduleRun4.setProfessors(professors4);

        professor1.setModuleRuns(moduleRuns1);
        professor2.setModuleRuns(moduleRuns2);
        professor3.setModuleRuns(moduleRuns3);
        professor4.setModuleRuns(moduleRuns4);
        professor5.setModuleRuns(moduleRuns5);

        professorService.save(professor1);
        professorService.save(professor2);
        professorService.save(professor3);
        professorService.save(professor4);
        professorService.save(professor5);

        moduleRunService.save(moduleRun1);
        moduleRunService.save(moduleRun2);
        moduleRunService.save(moduleRun3);
        moduleRunService.save(moduleRun4);

        enrollmentService.save(enrollment1);
        enrollmentService.save(enrollment2);
        enrollmentService.save(enrollment3);
        enrollmentService.save(enrollment4);
        enrollmentService.save(enrollment5);
        enrollmentService.save(enrollment6);
        enrollmentService.save(enrollment7);
        enrollmentService.save(enrollment8);

        studentService.save(student1);
        studentService.save(student2);
        studentService.save(student3);
        studentService.save(student4);
        studentService.save(student5);
        studentService.save(student6);
        studentService.save(student7);
        studentService.save(student8);

        moduleService.save(module1);
        moduleService.save(module2);
        moduleService.save(module3);
        moduleService.save(module4);
    }
}
