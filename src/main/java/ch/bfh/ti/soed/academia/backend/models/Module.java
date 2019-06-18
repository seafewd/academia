/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NamedQueries({ @NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module AS m"),
        //@NamedQuery(name = "Module.findByPattern", query = "SELECT m FROM Module AS m WHERE m.id LIKE ?1"),
        @NamedQuery(name = "Module.findByPattern", query = "SELECT m FROM Module AS m " +
                "WHERE lower(m.name) LIKE CONCAT('%', lower(?1), '%')"),

})

/**
 * The Module entity.
 */
@Entity
public class Module implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ModuleType moduleType;

    private DegreeProgramme courseOfStudy;

    private String description;

    private String name = "12345";

    @OneToMany(mappedBy = "module")
    private List<ModuleRun> moduleRuns;

    @ManyToOne()
    private Professor moduleManager;

    /**
     * Empty constructor required by JPA
     */
    public Module() {
    }

    /**
     * Default constructor for Module
     * @param name          Name of Module                          String
     * @param moduleType    Type of Module                          ModuleType
     * @param courseOfStudy Degree program of this Module           DegreeProgramme
     * @param description   Description of Module                   String
     * @param moduleManager Professor responsible for this Module   Professor
     */
    public Module(String name, ModuleType moduleType, DegreeProgramme courseOfStudy, String description, Professor moduleManager) {
        super();
        this.name = name;
        this.moduleType = moduleType;
        this.courseOfStudy = courseOfStudy;
        this.description = description;
        this.moduleManager = moduleManager;

    }

    /**
     * Get the value of the id
     *
     * @return the Id of the module
     */
    public Long getId() {
        return id;
    }

    /**
     * @return id as String
     */
    public String getIdAsString() {
        return String.valueOf(id);
    }

    /**
     * Get the value of name
     *
     * @return the name of the module
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of the name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of moduleType
     *
     * @return the moduleType of the module
     */
    public ModuleType getModuleType() {
        return moduleType;
    }

    /**
     * Set the value of moduleType
     *
     * @param moduleType new value of moduleType
     */
    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    /**
     * Get the value of courseOfStudy
     *
     * @return the courseOfStudy value
     */
    public DegreeProgramme getCourseOfStudy() {
        return courseOfStudy;
    }

    /**
     * Set the value of courseStudy
     *
     * @param courseOfStudy new value of courseStudy
     */
    public void setCourseOfStudy(DegreeProgramme courseOfStudy) {
        this.courseOfStudy = courseOfStudy;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get a list of all ModuleRun objects associated with this Module
     * @return List of ModuleRun objects
     */
    List<ModuleRun> getModuleRuns() {
        return moduleRuns;
    }

    /**
     * Associate ModuleRun objects with this Module
     * @param moduleRuns List of ModuleRun objects to associate with this Module
     */
    void setModuleRuns(List<ModuleRun> moduleRuns) {
        this.moduleRuns = moduleRuns;
    }

    /**
     * Get Professor responsible for this Module
     * @return Professor object associated with this Module
     */
    public Professor getModuleManager() {
        return moduleManager;
    }

    /**
     * Get the tag of the professor responsible for this Module
     * @return String of Professor's tag
     */
    public String getModuleManagerTag(){
        return getModuleManager().getTag();
    }

    /**
     * Assigns a module manager to this Module object
     * @param moduleManager Professor object to associate with this Module
     */
    public void setModuleManager(Professor moduleManager) {
        this.moduleManager = moduleManager;
    }


}
