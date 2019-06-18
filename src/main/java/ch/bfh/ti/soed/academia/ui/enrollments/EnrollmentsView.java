/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.enrollments;

import ch.bfh.ti.soed.academia.backend.controllers.EnrollmentsController;
import ch.bfh.ti.soed.academia.backend.controllers.ModuleRunsController;
import ch.bfh.ti.soed.academia.backend.controllers.StudentsController;
import ch.bfh.ti.soed.academia.backend.models.Enrollment;
import ch.bfh.ti.soed.academia.ui.HelpView;
import ch.bfh.ti.soed.academia.ui.MainView;
import ch.bfh.ti.soed.academia.ui.moduleruns.ModuleRunsView;
import ch.bfh.ti.soed.academia.ui.modules.ModuleView;
import ch.bfh.ti.soed.academia.ui.professors.ProfessorsView;
import ch.bfh.ti.soed.academia.ui.students.StudentsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Page for viewing student enrollments
 */
@Route("enrollments")
public class EnrollmentsView extends VerticalLayout {

    @Inject
    private EnrollmentsController enrollmentsController;
    @Inject
    private StudentsController studentsController;
    @Inject
    private ModuleRunsController moduleRunsController;

    private Grid<Enrollment> grid = new Grid<>();
    private TextField filterText = new TextField();
    private EnrollmentForm form;
    private HorizontalLayout toolbar;

    /**
     * Sets the EnrollmentsView, so how the UI is arranged, which buttons are contained and so on
     */
     public EnrollmentsView() {
         filterText.setPlaceholder("Find by name or pattern '%', '_'...");
         filterText.setWidth("20em");
         filterText.setValueChangeMode(ValueChangeMode.EAGER);
         filterText.addValueChangeListener(e -> updateList());

         Button addEnrollmentButton = new Button("Create a new enrollment");
         addEnrollmentButton.addClickListener(e -> {
             grid.asSingleSelect().clear();
             form.setModel(new Enrollment());
         });

         grid.asSingleSelect().addValueChangeListener(event -> form.setModel(event.getValue()));
         Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
         clearFilterTextBtn.addClickListener(e -> filterText.clear());

         HorizontalLayout filtering = new HorizontalLayout(filterText, clearFilterTextBtn);

         //home button
         Button homeBtn = new Button("Home");
         RouterLink homeLink = new RouterLink(null, MainView.class);
         homeLink.add(homeBtn);

         //students button
         Button studentsBtn = new Button("Students");
         RouterLink studentsLink = new RouterLink(null, StudentsView.class);
         studentsLink.add(studentsBtn);

         //professors button
         Button professorsBtn = new Button("Professors");
         RouterLink professorsLink = new RouterLink(null, ProfessorsView.class);
         professorsLink.add(professorsBtn);

         //Modules button
         Button modulesBtn = new Button("Modules");
         RouterLink modulesLink = new RouterLink(null, ModuleView.class);
         modulesLink.add(modulesBtn);

         //ModuleRuns button
         Button moduleRunsBtn = new Button("Module runs");
         RouterLink moduleRunsLink = new RouterLink(null, ModuleRunsView.class);
         moduleRunsLink.add(moduleRunsBtn);

         //HelpView button
         Button helpViewBtn = new Button("Help");
         RouterLink helpViewLink = new RouterLink(null, HelpView.class);
         helpViewLink.add(helpViewBtn);

         this.toolbar = new HorizontalLayout(filtering, addEnrollmentButton, homeLink, studentsLink, professorsLink, modulesLink, moduleRunsLink, helpViewLink);

         grid.setSizeFull();
         grid.addColumn(Enrollment::getId).setHeader("Enrollment ID");
         grid.addColumn(Enrollment::getStudentID).setHeader("Student ID");
         grid.addColumn(Enrollment::getModuleRun).setHeader("Module run");
         grid.addColumn(Enrollment::getEvaluation).setHeader("Evaluation");
         grid.addColumn(Enrollment::getStudentLastName).setHeader("Last name");
         grid.addColumn(Enrollment::getStudentStatus).setHeader("Status");

         setHeight("100vh");
         //updateList();
     }

    /**
     * init needs to be executed after dependency injection is done to perform any initialization
     */
    @PostConstruct
    public void init() {
        updateList();
        this.form = new EnrollmentForm(this);
        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();
        add(toolbar, main);

    }

    /**
     * updates the grid according to the enrollment set in the enrollment controller
     */
    void updateList() {
        grid.setItems(this.enrollmentsController.getAllEnrollments(filterText.getValue()));
    }

    /**
     *
     * @return the enrollment controller
     */
    EnrollmentsController getEnrollmentsController(){
        return this.enrollmentsController;
    }

    /**
     *
     * @return the student controller
     */
    StudentsController getStudentsController() {
         return this.studentsController;
     }

     /**
      * @return the moduleRun controller
      */
    ModuleRunsController getModuleRunsController() {
        return this.moduleRunsController;
     }

}
