/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.Enrollment;
import ch.bfh.ti.soed.academia.backend.services.EnrollmentService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

/**
 * Controller class for Enrollment objects
 * Takes care of the handling of Enrollment objects (save, delete etc.)
 */
public class EnrollmentsController {

    @Inject
    private EnrollmentService enrollmentService;

    /**
     * Empty constructor required by JPA
     */
    public EnrollmentsController(){}

    /**
     * Initialization method
     */
    @PostConstruct
    public void init(){}

    /**
     * Queries DB for all Enrollment objects
     *
     * @param value String for filtering Enrollments
     * @return List of Enrollment objects
     */
    public List<Enrollment> getAllEnrollments(String value){
        return this.enrollmentService.findAll(value);
    }

    /**
     * Queries the DB for Enrollment objects by ID
     *
     * @param value Long to query with
     * @return List of Enrollment objects
     * NOT YET USED
     */
    public List<Enrollment> getEnrollmentById(Long value){
        return this.enrollmentService.findById(value);
    }

    /**
     * Deletes an enrollment object from the DB
     *
     * @param enrollment Enrollment object to delete
     */
    public void deleteEnrollment(Enrollment enrollment){
        this.enrollmentService.delete(enrollment);
    }

    /**
     * Saves an Enrollment object in the DB
     *
     * @param enrollment Enrollment objejct to save
     * @return The updated/merged Enrollment object
     */
    public Enrollment save(Enrollment enrollment){
        return this.enrollmentService.save(enrollment);
    }

    /**
     * Get List of Enrollment objects associated with Student's tag attr.
     * @param tag User tag as String
     * @return String
     */
    public List<Enrollment> getAllEnrollmentsByStudentTag(String tag) {
        return this.enrollmentService.getAllEnrollmentsByStudentTag(tag);
    }
}
