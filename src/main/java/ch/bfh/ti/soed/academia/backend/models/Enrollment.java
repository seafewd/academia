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


@NamedQueries({
        @NamedQuery(name = "Enrollment.findAll",
                query = "SELECT e FROM Enrollment AS e"),
        @NamedQuery(name = "Enrollment.findByPattern",
                query = "SELECT e FROM Enrollment AS e WHERE lower(e.student.firstName) LIKE CONCAT('%', lower(?1), '%')"
                         + "OR lower(e.student.lastName) LIKE CONCAT('%', lower(?1), '%')"),
        @NamedQuery(name = "Enrollment.findById",
                query = "SELECT e FROM Enrollment AS e WHERE e.id LIKE ?1")
})



/**
 * The Enrollment entity.
 */
@Entity
public class Enrollment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ModuleRun moduleRun;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    private Evaluation evaluation;

    /**
     * Empty constructor required by JPA
     */
    public Enrollment() {
    }

    /**
     * Default constructor
     * @param moduleRun     ModuleRun object
     * @param student       Student object
     * @param evaluation    Evaluation Enum
     */
    public Enrollment(ModuleRun moduleRun, Student student, Evaluation evaluation) {
        super();
        this.moduleRun = moduleRun;
        this.student = student;
        this.evaluation = evaluation;
    }

    /**
     * Get the value of the id
     *
     * @return the Id of the enrollment
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the moduleRun
     *
     * @return the moduleRun of the enrollment
     */
    public ModuleRun getModuleRun() {
        return moduleRun;
    }

    /**
     * Set the moduleRun
     *
     * @param moduleRun new moduleRun
     */
    public void setModuleRun(ModuleRun moduleRun) {
        this.moduleRun = moduleRun;
    }

    /**
     * Get the student
     *
     * @return the student of the enrollment
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Set the student
     *
     * @param student new student
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Get the value of the evaluation
     *
     * @return the evaluation of the enrollment
     */
    public Evaluation getEvaluation() {
        return evaluation;
    }

    /**
     * Set the value of the evaluation
     *
     * @param evaluation new value of evaluation
     */
    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    /**
     * Return the ID of the Student object associated with this Enrollment object
     * @return Student ID of this Enrollment
     */
    public Long getStudentID() {
        return student.getId();
    }

    /**
     * Return the first name of the Student object associated with this Enrollment object
     * @return Student's first name
     */
    public String getStudentFirstName() {
        return student.getFirstName();
    }

    /**
     * Return the last name of the Student object associated with this Enrollment object
     * @return Student's last name
     */
    public String getStudentLastName() {
        return student.getLastName();
    }

    /**
     * Return the enrollment status (StudentStatus) of the Student object associated with this Enrollment object
     * @return Student's enrollment status
     */
    public StudentStatus getStudentStatus() {
        return student.getStatus();
    }

    /**
     * Get a Set of all Enrollment objects associated with this Enrollment
     * @return Set of Enrollment objects
     */
    public Set<Enrollment> getEnrollments() {
        return moduleRun.getEnrollments();
    }

}
