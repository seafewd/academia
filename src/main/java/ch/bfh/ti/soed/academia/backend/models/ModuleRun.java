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
import java.util.Set;
import java.util.stream.Collectors;

@NamedQueries({ @NamedQuery(name = "ModuleRun.findAll", query = "SELECT m FROM ModuleRun AS m"),
        @NamedQuery(name = "ModuleRun.findByPattern", query = "SELECT m FROM ModuleRun AS m " +
                "WHERE lower(m.name) LIKE CONCAT('%', lower(?1), '%')"),

})
/**
 * The ModuleRun entity.
 */
@Entity
public class ModuleRun implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Module module;

    private Semester semester;

    @OneToMany(mappedBy = "moduleRun")
    private Set<Enrollment> enrollments;

    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<Professor> professors;

    /**
     * Empty constructor required by JPA
     */
    public ModuleRun() {
    }

    /**
     * Constructor for ModuleRun - NOT CURRENTLY USED
     * @param module        Module object
     * @param semester      Semester Enum
     * @param enrollments   Set of Enrollment objects
     * @param professors    Set of Professor objects
     */
    public ModuleRun(Module module, Semester semester, Set<Enrollment> enrollments, Set<Professor> professors) {
        this.module = module;
        this.semester = semester;
        this.enrollments = enrollments;
        this.professors = professors;
        setName();
    }

    /**
     * Constructor for ModuleRun
     * @param module        Module object
     * @param semester      Semester enum
     */
    public ModuleRun(Module module, Semester semester) {
        this.module = module;
        this.semester = semester;
        setName();
    }

    /**
     * Get the value of the id
     *
     * @return the Id of the module
     */
    public long getId() {
        return id;
    }

    /**
     * Get the ID of the ModuleRun as a String
     * @return ModuleRun ID as String
     */
    public String getIdAsString() {
        return String.valueOf(id);
    }

    /**
     * Get the module
     * @return the module of the moduleRun
     */
    public Module getModule() {
        return module;
    }

    /**
     * Set the module of the moduleRun
     *
     * @param module new module
     */
    public void setModule(Module module) {
        this.module = module;
    }


    /**
     * Get the Semester
     *
     * @return the Semester of the moduleRun
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Set the Semester of the moduleRun
     *
     * @param semester new term
     */
    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    /**
     * Get a Set of all Enrollment objects associated with this ModuleRun object
     * @return Set of Enrollments
     */
    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Assigns Enrollments to this ModuleRun object
     * @param enrollments Set of Enrollment objects to assign
     */
    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Get a Set of all Professor objects associated with this ModuleRun object
     * @return a set of professors
     */
    public Set<Professor> getProfessors() {
        return professors;
    }

    /**
     * Assigns Professors to this ModuleRun object
     * @param professors Set of Professor objects to assign
     */
    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }

    /**
     * Get the ID of the Module object associated with this ModuleRun object
     * @return Module ID as Long
     */
    public Long getModuleID() {
        return module.getId();
    }

    /**
     * Get a String of IDs of all Enrollment objects associated with this ModuleRun
     * @return String of IDs (xx, yy, zz, ...)
     */
    public String getEnrollmentIDs(){
        String s = enrollments.stream().map(enrollment -> Long.toString(enrollment.getId())).collect(Collectors.joining(", "));
        return String.format("%s", s);
    }

    /**
     * Get a String of IDs of all Professor objects associated with this ModuleRun
     * @return String of IDs (xx, yy, zz, ...)
     */
    public String getProfessorIDs(){
        String s = professors.stream().map(professor -> Long.toString(professor.getId())).collect(Collectors.joining(", "));
        return String.format("%s", s);
    }

    /**
     * Get the Name of the ModuleRun
     *
     * @return the name of the moduleRun
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the moduleRun
     *
     */
    public void setName() {
        this.name = generateName();
    }

    public String generateName(){
        return module.getName() + "-" + semester.toString();
    }
}
