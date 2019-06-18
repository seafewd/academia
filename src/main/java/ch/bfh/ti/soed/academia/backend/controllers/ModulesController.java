/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.Module;
import ch.bfh.ti.soed.academia.backend.services.ModuleService;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;


/**
 * The modules controller
 */
public class ModulesController {

    @Inject
    private ModuleService moduleService;

    public ModulesController(){}

    /**
     * Initialization method
     */
    @PostConstruct
    public void init(){}

    /**
     * Search for modules with given name
     *
     * @param value the name of the module you search for
     * @return a list of the modules that match the pattern
     */
    public List<Module> getAllModules(String value){
        return this.moduleService.findAll(value);
    }

    /**
     * Delete a module given Module
     *
     * @param module the module you want to delete
     */
    public void deleteModule(Module module){
        this.moduleService.delete(module);
    }


    /**
     * Saves the module
     *
     * @param module the module you want to save
     * @return the saved Module
     */
    public Module save(Module module){
        return this.moduleService.save(module);
    }
}
