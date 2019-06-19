/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;
import ch.bfh.ti.soed.academia.backend.models.Professor;
import ch.bfh.ti.soed.academia.backend.services.ProfessorService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;


/**
 * the professor controller
 */
public class ProfessorController {

     @Inject
     private ProfessorService professorService;


     public ProfessorController(){}

     @PostConstruct
     public void init(){}


     /**
      * Search for professors with given name
      *
     * @param value the name of the professor you search for
     * @return a list of the modules that match the pattern
     */
    public List<Professor> getAllProfessors(String value){
        return this.professorService.findAll(value);
    }


    /**
     * Search for all professors
     *
     * @return a list with all professors
     */
    public List<Professor> getAllProfessors(){
        return this.professorService.findAll();
    }

    /**
     * delete a given professor
     * @param professor the professor you want to delete
     */
    public void deleteProfessor(Professor professor) {
        this.professorService.delete(professor);
    }

    /**
     * Saves a professor
     *
     * @param professor the professor you want to save
     * @return the saved professor
     */
    public Professor save(Professor professor){
        return this.professorService.save(professor);
    }

    /**
     * Search a Professor by tag
     *
     * @param tag of the Professor that we want to search
     * @return the Professor
     */
    public Professor getProfessorByTag(String tag) {
        return this.professorService.getProfessorByTag(tag);
    }
}
