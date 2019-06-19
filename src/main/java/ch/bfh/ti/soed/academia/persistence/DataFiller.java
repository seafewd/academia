/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.persistence;

import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.backend.models.Module;
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

    @Inject
    private UserService userService;

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

        //admin user
        User admin = repository.createAdmin("admin", "admin", Role.ADMIN);

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
        Module module5 = repository.createModule("TST0001", ModuleType.PE, DegreeProgramme.Microtechnology, "Labor Techniques",professor5);
        Module module6 = repository.createModule("TST0002", ModuleType.PB, DegreeProgramme.Microtechnology, "Micro-Mechanics",professor3);
        Module module7 = repository.createModule("TST0003", ModuleType.PE, DegreeProgramme.MechanicalEngineering, "Robotic",professor2);
        Module module8 = repository.createModule("TST0004", ModuleType.PE, DegreeProgramme.MechanicalEngineering, "Engine Work",professor3);
        Module module9 = repository.createModule("TST0005", ModuleType.PB, DegreeProgramme.MechanicalEngineering, "Physics",professor4);
        Module module10 = repository.createModule("TST0006", ModuleType.PE, DegreeProgramme.Nursing, "Biology",professor1);
        Module module11 = repository.createModule("TST0007", ModuleType.PE, DegreeProgramme.Nursing, "Psychological Basics",professor2);
        Module module12 = repository.createModule("TST0008", ModuleType.PE, DegreeProgramme.Nursing, "Drug Lore",professor1);
        Module module13 = repository.createModule("TST0009", ModuleType.PB, DegreeProgramme.Nursing, "Statistics",professor5);
        Module module14 = repository.createModule("TST0010", ModuleType.PE, DegreeProgramme.Nursing, "Logistics",professor5);
        Module module15 = repository.createModule("TST0011", ModuleType.PE, DegreeProgramme.Microtechnology, "Sensors",professor3);
        Module module16 = repository.createModule("TST0012", ModuleType.PE, DegreeProgramme.Microtechnology, "IT Basics",professor2);
        Module module17 = repository.createModule("TST0013", ModuleType.PE, DegreeProgramme.MechanicalEngineering, "CAD",professor2);
        Module module18 = repository.createModule("TST0014", ModuleType.PE, DegreeProgramme.MechanicalEngineering, "Cancer Design",professor5);
        Module module19 = repository.createModule("TST0015", ModuleType.PE, DegreeProgramme.ComputerScience, "Algorithms",professor1);
        Module module20 = repository.createModule("TST0016", ModuleType.PE, DegreeProgramme.ComputerScience, "Web-Programming",professor4);

        //ModuleRuns
        ModuleRun moduleRun1 = repository.createModuleRun(module1, Semester.FS2018);
        ModuleRun moduleRun2 = repository.createModuleRun(module1, Semester.HS2018);
        ModuleRun moduleRun3 = repository.createModuleRun(module1, Semester.FS2019);
        ModuleRun moduleRun4 = repository.createModuleRun(module1, Semester.HS2018);
        ModuleRun moduleRun5 = repository.createModuleRun(module2, Semester.HS2018);
        ModuleRun moduleRun6 = repository.createModuleRun(module3, Semester.FS2019);
        ModuleRun moduleRun7 = repository.createModuleRun(module4, Semester.HS2018);
        ModuleRun moduleRun8 = repository.createModuleRun(module5, Semester.HS2019);
        ModuleRun moduleRun9 = repository.createModuleRun(module6, Semester.HS2018);
        ModuleRun moduleRun10 = repository.createModuleRun(module7, Semester.HS2018);
        ModuleRun moduleRun11 = repository.createModuleRun(module8, Semester.FS2018);
        ModuleRun moduleRun12 = repository.createModuleRun(module9, Semester.HS2019);
        ModuleRun moduleRun13 = repository.createModuleRun(module10, Semester.HS2018);
        ModuleRun moduleRun14 = repository.createModuleRun(module11, Semester.HS2018);
        ModuleRun moduleRun15 = repository.createModuleRun(module12, Semester.HS2018);
        ModuleRun moduleRun16 = repository.createModuleRun(module13, Semester.HS2019);
        ModuleRun moduleRun17 = repository.createModuleRun(module14, Semester.FS2019);
        ModuleRun moduleRun18 = repository.createModuleRun(module15, Semester.HS2018);
        ModuleRun moduleRun19 = repository.createModuleRun(module16, Semester.HS2018);
        ModuleRun moduleRun20 = repository.createModuleRun(module17, Semester.HS2018);
        ModuleRun moduleRun21 = repository.createModuleRun(module18, Semester.HS2018);
        ModuleRun moduleRun22 = repository.createModuleRun(module19, Semester.HS2018);
        ModuleRun moduleRun23 = repository.createModuleRun(module20, Semester.HS2018);

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

        Set<ModuleRun> moduleRuns2 = new HashSet<>();
        moduleRuns2.add(moduleRun2);

        Set<ModuleRun> moduleRuns3 = new HashSet<>();
        moduleRuns3.add(moduleRun3);

        Set<ModuleRun> moduleRuns4 = new HashSet<>();
        moduleRuns4.add(moduleRun4);

        Set<ModuleRun> moduleRuns5 = new HashSet<>();
        moduleRuns5.add(moduleRun5);

        Set<ModuleRun> moduleRuns6 = new HashSet<>();
        moduleRuns6.add(moduleRun6);

        Set<ModuleRun> moduleRuns7 = new HashSet<>();
        moduleRuns7.add(moduleRun7);

        Set<ModuleRun> moduleRuns8 = new HashSet<>();
        moduleRuns8.add(moduleRun8);

        Set<ModuleRun> moduleRuns9 = new HashSet<>();
        moduleRuns9.add(moduleRun9);

        Set<ModuleRun> moduleRuns10 = new HashSet<>();
        moduleRuns10.add(moduleRun10);

        Set<ModuleRun> moduleRuns11 = new HashSet<>();
        moduleRuns11.add(moduleRun11);

        Set<ModuleRun> moduleRuns12 = new HashSet<>();
        moduleRuns12.add(moduleRun12);

        Set<ModuleRun> moduleRuns13 = new HashSet<>();
        moduleRuns13.add(moduleRun13);

        Set<ModuleRun> moduleRuns14 = new HashSet<>();
        moduleRuns14.add(moduleRun14);

        Set<ModuleRun> moduleRuns15 = new HashSet<>();
        moduleRuns15.add(moduleRun15);

        Set<ModuleRun> moduleRuns16 = new HashSet<>();
        moduleRuns16.add(moduleRun16);

        Set<ModuleRun> moduleRuns17 = new HashSet<>();
        moduleRuns17.add(moduleRun17);

        Set<ModuleRun> moduleRuns18 = new HashSet<>();
        moduleRuns18.add(moduleRun18);

        Set<ModuleRun> moduleRuns19 = new HashSet<>();
        moduleRuns19.add(moduleRun19);

        Set<ModuleRun> moduleRuns20 = new HashSet<>();
        moduleRuns20.add(moduleRun20);

        Set<ModuleRun> moduleRuns21 = new HashSet<>();
        moduleRuns21.add(moduleRun21);

        Set<ModuleRun> moduleRuns22 = new HashSet<>();
        moduleRuns22.add(moduleRun22);

        Set<ModuleRun> moduleRuns23 = new HashSet<>();
        moduleRuns23.add(moduleRun23);


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

        Set<Professor> professors01 = new HashSet<>();
        professors01.add(professor1);
        Set<Professor> professors02 = new HashSet<>();
        professors02.add(professor2);
        Set<Professor> professors03 = new HashSet<>();
        professors03.add(professor3);
        Set<Professor> professors04 = new HashSet<>();
        professors04.add(professor4);
        Set<Professor> professors05 = new HashSet<>();
        professors05.add(professor5);

        moduleRun1.setProfessors(professors1);
        moduleRun2.setProfessors(professors2);
        moduleRun3.setProfessors(professors3);
        moduleRun4.setProfessors(professors4);
        moduleRun5.setProfessors(professors01);
        moduleRun6.setProfessors(professors01);
        moduleRun7.setProfessors(professors02);
        moduleRun8.setProfessors(professors03);
        moduleRun9.setProfessors(professors04);
        moduleRun10.setProfessors(professors05);
        moduleRun11.setProfessors(professors01);
        moduleRun12.setProfessors(professors02);
        moduleRun13.setProfessors(professors03);
        moduleRun14.setProfessors(professors04);
        moduleRun15.setProfessors(professors05);
        moduleRun16.setProfessors(professors01);
        moduleRun17.setProfessors(professors02);
        moduleRun18.setProfessors(professors03);
        moduleRun19.setProfessors(professors04);
        moduleRun20.setProfessors(professors05);
        moduleRun21.setProfessors(professors01);
        moduleRun22.setProfessors(professors02);
        moduleRun23.setProfessors(professors03);

        professor1.setModuleRuns(moduleRuns1);
        professor2.setModuleRuns(moduleRuns2);
        professor3.setModuleRuns(moduleRuns3);
        professor4.setModuleRuns(moduleRuns4);
        professor5.setModuleRuns(moduleRuns5);
        professor1.setModuleRuns(moduleRuns6);
        professor2.setModuleRuns(moduleRuns7);
        professor3.setModuleRuns(moduleRuns8);
        professor4.setModuleRuns(moduleRuns9);
        professor5.setModuleRuns(moduleRuns10);
        professor1.setModuleRuns(moduleRuns11);
        professor2.setModuleRuns(moduleRuns12);
        professor3.setModuleRuns(moduleRuns13);
        professor4.setModuleRuns(moduleRuns14);
        professor5.setModuleRuns(moduleRuns15);
        professor1.setModuleRuns(moduleRuns16);
        professor2.setModuleRuns(moduleRuns17);
        professor3.setModuleRuns(moduleRuns18);
        professor4.setModuleRuns(moduleRuns19);
        professor5.setModuleRuns(moduleRuns20);
        professor1.setModuleRuns(moduleRuns21);
        professor2.setModuleRuns(moduleRuns22);
        professor3.setModuleRuns(moduleRuns23);

        userService.save(admin);

        professorService.save(professor1);
        professorService.save(professor2);
        professorService.save(professor3);
        professorService.save(professor4);
        professorService.save(professor5);

        moduleRunService.save(moduleRun1);
        moduleRunService.save(moduleRun2);
        moduleRunService.save(moduleRun3);
        moduleRunService.save(moduleRun4);
        moduleRunService.save(moduleRun5);
        moduleRunService.save(moduleRun6);
        moduleRunService.save(moduleRun7);
        moduleRunService.save(moduleRun8);
        moduleRunService.save(moduleRun9);
        moduleRunService.save(moduleRun10);
        moduleRunService.save(moduleRun11);
        moduleRunService.save(moduleRun12);
        moduleRunService.save(moduleRun13);
        moduleRunService.save(moduleRun14);
        moduleRunService.save(moduleRun15);
        moduleRunService.save(moduleRun16);
        moduleRunService.save(moduleRun17);
        moduleRunService.save(moduleRun18);
        moduleRunService.save(moduleRun19);
        moduleRunService.save(moduleRun20);
        moduleRunService.save(moduleRun21);
        moduleRunService.save(moduleRun22);
        moduleRunService.save(moduleRun23);

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
        moduleService.save(module5);
        moduleService.save(module6);
        moduleService.save(module7);
        moduleService.save(module8);
        moduleService.save(module9);
        moduleService.save(module10);
        moduleService.save(module11);
        moduleService.save(module12);
        moduleService.save(module13);
        moduleService.save(module14);
        moduleService.save(module15);
        moduleService.save(module16);
        moduleService.save(module17);
        moduleService.save(module18);
        moduleService.save(module19);
        moduleService.save(module20);
    }
}
