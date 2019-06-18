/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.moduleruns;


import ch.bfh.ti.soed.academia.backend.controllers.ModuleRunsController;
import ch.bfh.ti.soed.academia.backend.controllers.ModulesController;
import ch.bfh.ti.soed.academia.backend.models.ModuleRun;
import ch.bfh.ti.soed.academia.ui.HelpView;
import ch.bfh.ti.soed.academia.ui.MainView;
import ch.bfh.ti.soed.academia.ui.enrollments.EnrollmentsView;
import ch.bfh.ti.soed.academia.ui.modules.ModuleView;
import ch.bfh.ti.soed.academia.ui.professors.ProfessorsView;
import ch.bfh.ti.soed.academia.ui.students.StudentsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Page for viewing module runs
 */
@Route("moduleruns")
public class ModuleRunsView extends VerticalLayout {

    @Inject
    private ModuleRunsController moduleRunsController;
    @Inject
    private ModulesController modulesController;

    private Grid<ModuleRun> grid = new Grid<>();
    private TextField filterText = new TextField();
    private ModuleRunForm form;
    private HorizontalLayout toolbar;

    /**
     * Sets the ModuleRunsView, so how the UI is arranged, which buttons are contained and so on
     */
    public ModuleRunsView() {
        filterText.setPlaceholder("Find by name or pattern '%', '_'...");
        filterText.setWidth("20em");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button addModuleRunButton = new Button("Create a new module run");
        addModuleRunButton.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setModel(new ModuleRun());
        });

        grid.asSingleSelect().addValueChangeListener(event -> {
            form.setModel(event.getValue());
        });

        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
        HorizontalLayout filtering = new HorizontalLayout(filterText, clearFilterTextBtn);

        //home button
        Button homeBtn = new Button("Home");
        RouterLink homeLink = new RouterLink(null, MainView.class);
        homeLink.add(homeBtn);

        //professors button
        Button professorsBtn = new Button("Professors");
        RouterLink professorsLink = new RouterLink(null, ProfessorsView.class);
        professorsLink.add(professorsBtn);

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

        //Help
        Button helpBtn = new Button("Help");
        RouterLink helpLink = new RouterLink(null, HelpView.class);
        helpLink.add(helpBtn);

        this.toolbar = new HorizontalLayout(filtering, addModuleRunButton, homeLink, professorsLink, studentsLink, enrollmentsLink, modulesLink, helpLink);

        grid.setSizeFull();
        grid.addColumn(ModuleRun::getId).setHeader("ModuleRun ID");
        grid.addColumn(ModuleRun::getModuleID).setHeader("Module ID");
        grid.addColumn(ModuleRun::getName).setHeader("Name");
        grid.addColumn(ModuleRun::getEnrollmentIDs).setHeader("Enrollments (Student ID)");
        grid.addColumn(ModuleRun::getProfessorIDs).setHeader("Professors (ID)");

        setHeight("100vh");
        //updateList();
    }

    /**
     * init needs to be executed after dependency injection is done to perform any initialization
     */
    @PostConstruct
    public void init() {
        updateList();
        this.form = new ModuleRunForm(this);
        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setAlignItems(FlexComponent.Alignment.START);
        main.setSizeFull();
        add(toolbar, main);
    }

    /**
     * updates the grid according to the moduleRun set in the moduleRun controller
     */
    void updateList() {
        grid.setItems(this.moduleRunsController.getAllModuleRuns(filterText.getValue()));
    }

    /**
     *
     * @return the moduleRuns controller
     */
    ModuleRunsController getModuleRunsController(){
        return this.moduleRunsController;
    }
    /**
     *
     * @return the module controller
     */
    ModulesController getModulesController(){
        return this.modulesController;
    }
}
