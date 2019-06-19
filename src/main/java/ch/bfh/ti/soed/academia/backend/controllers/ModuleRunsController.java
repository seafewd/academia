/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.backend.services.EnrollmentService;
import ch.bfh.ti.soed.academia.backend.services.ModuleRunService;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Controller class for ModuleRun objects
 * Takes care of the handling of ModuleRun objects (save, delete etc.)
 */
public class ModuleRunsController {

    @Inject
    private ModuleRunService moduleRunService;

    @Inject
    private EnrollmentService enrollmentService;

    /**
     * Empty constructor required by JPA
     */
    public ModuleRunsController(){}

    /**
     * Initialization method
     */
    @PostConstruct
    public void init(){}

    /**
     * Queries DB for all ModuleRun objects
     *
     * @param value String for filtering ModuleRuns
     * @return List of ModuleRun objects
     */
    public List<ModuleRun> getAllModuleRuns(String value){
        return this.moduleRunService.findAll(value);
    }

    /**
     * Deletes an ModuleRun object from the DB
     *
     * @param moduleRun ModuleRun object to delete
     */
    public void deleteModuleRun(ModuleRun moduleRun){
        this.moduleRunService.delete(moduleRun);
    }

    /**
     * Saves an ModuleRun object in the DB
     *
     * @param moduleRun ModuleRun objejct to save
     * @return The updated/merged ModuleRun object
     */
    public ModuleRun save(ModuleRun moduleRun){
        return this.moduleRunService.save(moduleRun);
    }

    /**
     * Get a List of ModuleRun objects to which the Student is -not yet- subscribed
     * @param tag User tag as String
     * @return List of ModuleRun
     */
    public List<ModuleRun> getModulesRunNotSubscribed(String tag) {
        List<ModuleRun> moduleRuns = this.moduleRunService.findAll();
        List<Enrollment> enrollments =  this.enrollmentService.getAllEnrollmentsByStudentTag(tag);
        for (Enrollment enrollment: enrollments) {
            moduleRuns.removeIf( moduleRun -> moduleRun.getId() == enrollment.getModuleRun().getId());
        }
        return moduleRuns;
    }

    /**
     * Get a list of ModuleRuns in which the Professor with this tag is a teacher
     * @param tag Professor tag name
     * @return List of ModuleRun objects associated with the Professor tag
     */
    public List<ModuleRun> getModulesRunByProfessorTag(String tag) {
        List<ModuleRun> moduleRuns = this.moduleRunService.findAll();
        List<ModuleRun> moduleRunsByProfessor = new ArrayList<>();
        for (ModuleRun moduleRun: moduleRuns){
            Set<Professor> professorSet = moduleRun.getProfessors();
            boolean found = false;
            for (Professor professor: professorSet){
                if (professor.getTag().equals(tag)){
                    found = true;
                }
            }
            if (found)
                moduleRunsByProfessor.add(moduleRun);
        }
        return moduleRunsByProfessor;
    }

    public void subscribe(Student currentStudent, ModuleRun moduleRun) {
        Enrollment enrollment = new Enrollment(moduleRun, currentStudent, Evaluation.NYE);
        this.moduleRunService.save(moduleRun,enrollment);
    }
}
