/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.ModuleRun;
import ch.bfh.ti.soed.academia.backend.services.ModuleRunService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

/**
 * Controller class for ModuleRun objects
 * Takes care of the handling of ModuleRun objects (save, delete etc.)
 */
public class ModuleRunsController {

    @Inject
    private ModuleRunService moduleRunService;

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
}
