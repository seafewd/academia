/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.studentsview;

import ch.bfh.ti.soed.academia.backend.controllers.StudentsController;
import ch.bfh.ti.soed.academia.backend.models.Role;
import ch.bfh.ti.soed.academia.backend.models.Student;
import ch.bfh.ti.soed.academia.backend.models.User;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The Student view.
 */
@Route("studentview")
public class StudentView extends FormLayout {

    @Inject
    private StudentsController studentsController;
    private User currentUser;
    private Student currentStudent;

    public StudentView() {
        this.currentUser = (User)VaadinSession.getCurrent().getAttribute("currentUser");
        if (this.currentUser != null && this.currentUser.getRole() == Role.STUDENT){
            H1 title = new H1("StudentView");
            add(title);
        }else {
            Notification.show("You don't have permissions to see this page!");
        }
    }

    /**
     * init needs to be executed after dependency injection is done to perform any initialization
     */
    @PostConstruct
    public void init(){
        if (currentUser != null){
            this.currentStudent = getStudentByTag();
            H1 title = new H1("Hallo! " + this.currentStudent.getFirstName() + " " + this.currentStudent.getLastName());
            add(title);
        }
    }

    /**
     *
     * @return the Student searched by tag
     */
    private Student getStudentByTag(){
        return this.studentsController.getStudentByTag(this.currentUser.getTag());
    }

}

