/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.modules;

import ch.bfh.ti.soed.academia.backend.controllers.ModulesController;
import ch.bfh.ti.soed.academia.backend.controllers.ProfessorController;
import ch.bfh.ti.soed.academia.backend.models.Module;
import ch.bfh.ti.soed.academia.ui.HelpView;
import ch.bfh.ti.soed.academia.ui.MainView;
import ch.bfh.ti.soed.academia.ui.enrollments.EnrollmentsView;
import ch.bfh.ti.soed.academia.ui.moduleruns.ModuleRunsView;
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

@Route("modules")
public class ModuleView extends VerticalLayout {

    @Inject
    private ModulesController modulesController;
    @Inject
    private ProfessorController professorController;

    private Grid<Module> grid = new Grid<>();
    private TextField filterText = new TextField();
    private ModuleForm form;
    private HorizontalLayout toolbar;

    /**
     * Sets the ModuleView, so how the UI is arranged, which buttons are contained and so on
     */
    public ModuleView() {
        filterText.setPlaceholder("Find by name or pattern '%', '_'...");
        filterText.setWidth("20em");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
        Button addCustomerBtn = new Button("Add new module");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setModel(new Module());
        });
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

        //moduleruns button
        Button moduleRunsBtn = new Button("Module runs");
        RouterLink moduleRunsLink = new RouterLink(null, ModuleRunsView.class);
        moduleRunsLink.add(moduleRunsBtn);

        //Help
        Button helpBtn = new Button("Help");
        RouterLink helpLink = new RouterLink(null, HelpView.class);
        helpLink.add(helpBtn);

        this.toolbar = new HorizontalLayout(filtering, addCustomerBtn, homeLink, professorsLink, studentsLink, enrollmentsLink, moduleRunsLink, helpLink);


        grid.setSizeFull();

        grid.addColumn(Module::getName).setHeader("ModuleName");
        grid.addColumn(Module::getModuleType).setHeader("ModuleType");
        grid.addColumn(Module::getCourseOfStudy).setHeader("Degree Programme");
        grid.addColumn(Module::getDescription).setHeader("Description");
        grid.addColumn(m -> m.getModuleManager().getTag()).setHeader("Module Manager");
        //grid.addColumn(ModuleType::ge).setHeader("Status");
        //grid.addColumn(Student::getEnrollmentsName).setHeader("Enrollments");

        grid.asSingleSelect().addValueChangeListener(event -> {
            form.setModel(event.getValue());
        });

        setHeight("100vh");
        //updateList();
    }

    /**
     * init needs to be executed after dependency injection is done to perform any initialization
     */
    @PostConstruct
    public void init() {

        updateList();
        this.form = new ModuleForm(this);
        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();
        add(toolbar, main);

    }

    /**
     * updates the grid according to the module set in the module controller
     */
    public void updateList() {
        grid.setItems(this.modulesController.getAllModules(filterText.getValue()));
    }

    /**
     *
     * @return the module controller
     */
    public ModulesController getModulesController(){
        return this.modulesController;
    }

    /**
     *
     * @return the professor controller
     */
    public ProfessorController getProfessorController(){
        return this.professorController;
    }
}
