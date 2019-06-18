/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.professors;

import ch.bfh.ti.soed.academia.backend.controllers.ProfessorController;
import ch.bfh.ti.soed.academia.backend.models.Professor;
import ch.bfh.ti.soed.academia.ui.HelpView;
import ch.bfh.ti.soed.academia.ui.MainView;
import ch.bfh.ti.soed.academia.ui.enrollments.EnrollmentsView;
import ch.bfh.ti.soed.academia.ui.moduleruns.ModuleRunsView;
import ch.bfh.ti.soed.academia.ui.modules.ModuleView;
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

@Route("professors")
public class ProfessorsView extends VerticalLayout {

    @Inject
    private ProfessorController professorController;

    private Grid<Professor> grid = new Grid<>();
    private TextField filterText = new TextField();
    private ProfessorForm form;
    private HorizontalLayout toolbar;

    /**
     * Sets the ProfessorView, so how the UI is arranged, which buttons are contained and so on
     */
    public ProfessorsView() {
        filterText.setPlaceholder("Find by name or pattern '%', '_'...");
        filterText.setWidth("20em");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        Button addCustomerBtn = new Button("Add new professor");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setModel(new Professor(), true);
        });

        grid.asSingleSelect().addValueChangeListener(event -> {
            form.setModel(event.getValue(), false);
        });

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

        //enrollments button
        Button enrollmentsBtn = new Button("Enrollments");
        RouterLink enrollmentsLink = new RouterLink(null, EnrollmentsView.class);
        enrollmentsLink.add(enrollmentsBtn);

        //modules button
        Button modulesBtn = new Button("Modules");
        RouterLink modulesLink = new RouterLink(null, ModuleView.class);
        modulesLink.add(modulesBtn);

        //ModuleRuns button
        Button moduleRunsBtn = new Button("Module runs");
        RouterLink moduleRunsLink = new RouterLink(null, ModuleRunsView.class);
        moduleRunsLink.add(moduleRunsBtn);

        //Help
        Button helpBtn = new Button("Help");
        RouterLink helpLink = new RouterLink(null, HelpView.class);
        helpLink.add(helpBtn);

        this.toolbar = new HorizontalLayout(filtering, addCustomerBtn, homeLink, studentsLink, enrollmentsLink, modulesLink, moduleRunsLink, helpLink);

        grid.setSizeFull();
        grid.addColumn(Professor::getId).setHeader("ID").setFlexGrow(0);
        grid.addColumn(Professor::getFirstName).setHeader("First name");
        grid.addColumn(Professor::getLastName).setHeader("Last name");
        grid.addColumn(Professor::getTag).setHeader("Tag");
        grid.addColumn(Professor::getPassword).setHeader("Password");
        grid.addColumn(Professor::getRole).setHeader("User role");

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
        this.form = new ProfessorForm(this);
        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();
        add(toolbar, main);
    }

    /**
     * updates the grid according to the professor set in the professor controller
     */
    public void updateList() {
        grid.setItems(this.professorController.getAllProfessors(filterText.getValue()));
    }

    /**
     *
     * @return the professor controller
     */
    public ProfessorController getProfessorController(){
        return this.professorController;
    }
}
