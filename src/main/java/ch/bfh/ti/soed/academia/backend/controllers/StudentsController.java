/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.controllers;

import ch.bfh.ti.soed.academia.backend.models.Student;
import ch.bfh.ti.soed.academia.backend.services.StudentService;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * The Students controller.
 */
public class StudentsController {

    @Inject
    private StudentService studentService;

    public StudentsController(){}

    /**
     * Initialization method
     */
    @PostConstruct
    public void init(){}

    /**
     * Search for students with given name
     *
     * @param value the name of the student you search for
     * @return a list of students with match the pattern
     */
    public List<Student> getAllStudents(String value){
        return this.studentService.findAll(value);
    }

    /**
     * delete a given student
     *
     * @param student the student you want to delete
     */
    public void deleteStudent(Student student){
        this.studentService.delete(student);
    }

    /**
     * Saves a given student
     *
     * @param student the student you want to save
     * @return the changed student object
     */
    public Student save(Student student){
        return this.studentService.save(student);
    }

    public List<Long> getAllStudentIDs() {
        List<Long> list = new ArrayList<>();
        List<Student> students = studentService.findAll();
        for (Student s : students) {
            list.add(s.getId());
        }
        return list;
    }

    /**
     * Search a Student by tag
     *
     * @param tag of the Student that we want to search
     * @return the Student
     */
    public Student getStudentByTag(String tag) {
        return this.studentService.getStudentByTag(tag);
    }
}
