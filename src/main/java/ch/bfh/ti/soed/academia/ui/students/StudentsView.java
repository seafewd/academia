/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.students;

import ch.bfh.ti.soed.academia.backend.controllers.StudentsController;
import ch.bfh.ti.soed.academia.backend.models.Student;
import ch.bfh.ti.soed.academia.ui.HelpView;
import ch.bfh.ti.soed.academia.ui.MainView;
import ch.bfh.ti.soed.academia.ui.enrollments.EnrollmentsView;
import ch.bfh.ti.soed.academia.ui.moduleruns.ModuleRunsView;
import ch.bfh.ti.soed.academia.ui.modules.ModuleView;
import ch.bfh.ti.soed.academia.ui.professors.ProfessorsView;
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
 * The Students view.
 */
@Route("students")
public class StudentsView extends VerticalLayout {

    @Inject
    private StudentsController studentsController;

    private Grid<Student> grid = new Grid<>();
    private TextField filterText = new TextField();
    private StudentForm form;
    private HorizontalLayout toolbar;

    /**
     * Sets the StudentsView, so how the UI is arranged, which buttons are contained and so on
     */
    public StudentsView() {
        filterText.setPlaceholder("Find by name or pattern '%', '_'...");
        filterText.setWidth("20em");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        Button addCustomerBtn = new Button("Add new student");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setModel(new Student(), true);
        });

        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
        HorizontalLayout filtering = new HorizontalLayout(filterText, clearFilterTextBtn);

        //home button
        Button homeBtn = new Button("Home");
        RouterLink homeLink = new RouterLink(null, MainView.class);
        homeLink.add(homeBtn);
        this.toolbar = new HorizontalLayout(filtering, addCustomerBtn, homeLink);

        //professors button
        Button professorsBtn = new Button("Professors");
        RouterLink professorsLink = new RouterLink(null, ProfessorsView.class);
        professorsLink.add(professorsBtn);

        //enrollments button
        Button enrollmentsBtn = new Button("Enrollments");
        RouterLink enrollmentsLink = new RouterLink(null, EnrollmentsView.class);
        enrollmentsLink.add(enrollmentsBtn);

        //Modules
        Button modulesBtn = new Button("Modules");
        RouterLink modulesLink = new RouterLink(null, ModuleView.class);
        modulesLink.add(modulesBtn);

        //ModuleRuns
        Button moduleRunBtn = new Button("Module runs");
        RouterLink moduleRunLink = new RouterLink(null, ModuleRunsView.class);
        moduleRunLink.add(moduleRunBtn);

        //Help
        Button helpBtn = new Button("Help");
        RouterLink helpLink = new RouterLink(null, HelpView.class);
        helpLink.add(helpBtn);


        toolbar = new HorizontalLayout(filtering, addCustomerBtn, homeLink, professorsLink, enrollmentsLink, modulesLink, moduleRunLink, helpLink);

        grid.asSingleSelect().addValueChangeListener(event -> {
            form.setModel(event.getValue(), false);
        });

        grid.setSizeFull();
        grid.addColumn(Student::getId).setHeader("ID").setFlexGrow(0);
        grid.addColumn(Student::getFirstName).setHeader("First name");
        grid.addColumn(Student::getLastName).setHeader("Last name");
        grid.addColumn(Student::getTag).setHeader("Tag");
        grid.addColumn(Student::getPassword).setHeader("Password");
        grid.addColumn(Student::getEmail).setHeader("Email");
        grid.addColumn(Student::getRole).setHeader("User role");
        grid.addColumn(Student::getStatus).setHeader("Status");

        grid.setColumnReorderingAllowed(true);
        setHeight("100vh");
        //updateList();
    }

    /**
     * init needs to be executed after dependency injection is done to perform any initialization
     */
    @PostConstruct
    public void init() {
        updateList();
        this.form = new StudentForm(this);
        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();
        add(toolbar, main);
    }

    /**
     * updates the grid according to the student set in the student controller
     */
    public void updateList() {
        grid.setItems(this.studentsController.getAllStudents(filterText.getValue()));
    }

    /**
     *
     * @return the student controller
     */
    public StudentsController getStudentsController(){
        return this.studentsController;
    }
}
