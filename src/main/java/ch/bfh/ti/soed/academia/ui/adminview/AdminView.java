/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.adminview;

import ch.bfh.ti.soed.academia.backend.controllers.*;
import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.backend.models.Module;
import ch.bfh.ti.soed.academia.ui.adminview.forms.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Admin view.
 */
@Route("adminview")
public class AdminView extends VerticalLayout {

    @Inject
    private StudentsController studentsController;
    @Inject
    private ProfessorController professorController;
    @Inject
    private EnrollmentsController enrollmentsController;
    @Inject
    private ModuleRunsController moduleRunsController;
    @Inject
    private ModulesController modulesController;

    private TextField studentFilter = new TextField();
    private TextField professorFilter = new TextField();
    private TextField enrollmentFilter = new TextField();
    private TextField moduleRunFilter = new TextField();
    private TextField moduleFilter = new TextField();
    private Grid<Student> studentsGrid = new Grid<>();
    private Grid<Professor> professorGrid = new Grid<>();
    private Grid<Enrollment> enrollmentGrid = new Grid<>();
    private Grid<ModuleRun> moduleRunGrid = new Grid<>();
    private Grid<Module> moduleGrid = new Grid<>();


    private User currentUser;

    public AdminView() {
        this.currentUser = (User)VaadinSession.getCurrent().getAttribute("currentUser");
        if (this.currentUser != null && this.currentUser.getRole() == Role.ADMIN){
            H1 title = new H1("Admin View");
            add(title);
        }else {
            Notification.show("You don't have permission to see this page!");
            UI.getCurrent().navigate("");
        }
    }

    /**
     * init needs to be executed after dependency injection is done to perform any initialization
     */
    @PostConstruct
    public void init(){
        if (currentUser != null && this.currentUser.getRole() == Role.ADMIN){
            H1 title = new H1("Logged in as " + this.currentUser.getTag());
            add(title);
            Button logoutBtn = new Button("Logout");
            logoutBtn.addClickListener(e -> this.logout());
            add(title, logoutBtn);
            this.createTabAndPages();
        }
    }

    private void createTabAndPages(){
        Tab tab1 = new Tab("Students");
        Div studentsManagerPanel = this.createStudentsPanel();

        Tab tab2 = new Tab("Professors");
        Div professorsManagerPanel = this.createProfessorsPanel();

        Tab tab3 = new Tab("Enrollments");
        Div enrollmentsManagerPanel = this.createEnrollmentsPanel();

        Tab tab4 = new Tab("Module Run");
        Div moduleRunManagerPanel = this.createModuleRunsPanel();

        Tab tab5 = new Tab("Module");
        Div moduleManagerPanel = this.createModulePanel();

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tab1, studentsManagerPanel);
        tabsToPages.put(tab2, professorsManagerPanel);
        tabsToPages.put(tab3, enrollmentsManagerPanel);
        tabsToPages.put(tab4, moduleRunManagerPanel);
        tabsToPages.put(tab5, moduleManagerPanel);

        Tabs tabs = new Tabs(tab1, tab2, tab3,tab4, tab5);
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.setWidth("100%");

        Div pages = new Div(studentsManagerPanel, professorsManagerPanel, enrollmentsManagerPanel,moduleRunManagerPanel,moduleManagerPanel);
        Set<Component> pagesShown = Stream.of(studentsManagerPanel)
                .collect(Collectors.toSet());
        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        pages.setWidth("100%");
        add(tabs, pages);
    }

    /**
     * Create the Modules tab
     * @return Div with added elements
     */
    private Div createModulePanel() {
        Div modulePanel = new Div();
        modulePanel.setSizeFull();
        modulePanel.setVisible(false);
        ModuleForm form = new ModuleForm(this);

        moduleFilter.setPlaceholder("Find by name or pattern '%', '_'...");
        moduleFilter.setWidth("20em");
        moduleFilter.setValueChangeMode(ValueChangeMode.EAGER);
        moduleFilter.addValueChangeListener(e -> updateModulesList());
        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> moduleFilter.clear());

        Button addModuleButton = new Button("Add new module");
        addModuleButton.addClickListener(e -> {
            moduleGrid.asSingleSelect().clear();
            form.setModel(new Module());
        });
        HorizontalLayout filtering = new HorizontalLayout(moduleFilter, clearFilterTextBtn, addModuleButton);
        this.updateModulesList();

        moduleGrid.addColumn(Module::getName).setHeader("ModuleName");
        moduleGrid.addColumn(Module::getModuleType).setHeader("ModuleType");
        moduleGrid.addColumn(Module::getCourseOfStudy).setHeader("Degree Programme");
        moduleGrid.addColumn(Module::getDescription).setHeader("Description");
        moduleGrid.addColumn(m -> m.getModuleManager().getTag()).setHeader("Module Manager");

        moduleGrid.asSingleSelect().addValueChangeListener(event -> {
            form.setModel(event.getValue());
        });
        setHeight("100vh");
        HorizontalLayout main = new HorizontalLayout(moduleGrid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();
        modulePanel.add(filtering, main);

        return modulePanel;
    }

    /**
     * Create the ModuleRuns tab
     * @return Div with added elements
     */
    private Div createModuleRunsPanel() {
        Div moduleRunsPanel = new Div();
        moduleRunsPanel.setSizeFull();
        moduleRunsPanel.setVisible(false);
        ModuleRunForm form = new ModuleRunForm(this);

        moduleRunFilter.setPlaceholder("Find by name or pattern '%', '_'...");
        moduleRunFilter.setWidth("20em");
        moduleRunFilter.setValueChangeMode(ValueChangeMode.EAGER);
        moduleRunFilter.addValueChangeListener(e -> updateModuleRunsList());

        Button addModuleRunButton = new Button("Create a new module run");
        addModuleRunButton.addClickListener(e -> {
            moduleRunGrid.asSingleSelect().clear();
            form.setModel(new ModuleRun());
        });

        moduleRunGrid.asSingleSelect().addValueChangeListener(event -> {
            form.setModel(event.getValue());
        });

        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> moduleRunFilter.clear());
        HorizontalLayout filtering = new HorizontalLayout(moduleRunFilter, clearFilterTextBtn, addModuleRunButton);

        updateModuleRunsList();
        moduleRunGrid.addColumn(ModuleRun::getId).setHeader("ModuleRun ID");
        moduleRunGrid.addColumn(ModuleRun::getModuleID).setHeader("Module ID");
        moduleRunGrid.addColumn(ModuleRun::getName).setHeader("Name");
        moduleRunGrid.addColumn(ModuleRun::getEnrollmentIDs).setHeader("Enrollments (Student ID)");
        moduleRunGrid.addColumn(ModuleRun::getProfessorIDs).setHeader("Professors (ID)");
        setHeight("100vh");

        HorizontalLayout main = new HorizontalLayout(moduleRunGrid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();
        moduleRunsPanel.add(filtering, main);


        return moduleRunsPanel;
    }

    /**
     * Create the Enrollments tab
     * @return Div with added elements
     */
    private Div createEnrollmentsPanel() {
        Div enrollmentsPanel = new Div();
        enrollmentsPanel.setSizeFull();
        enrollmentsPanel.setVisible(false);

        EnrollmentForm form = new EnrollmentForm(this);

        enrollmentFilter.setPlaceholder("Find by name or pattern '%', '_'...");
        enrollmentFilter.setWidth("20em");
        enrollmentFilter.setValueChangeMode(ValueChangeMode.EAGER);
        enrollmentFilter.addValueChangeListener(e -> updateEnrollmentList());

        Button addEnrollmentButton = new Button("Create a new enrollment");
        addEnrollmentButton.addClickListener(e -> {
            enrollmentGrid.asSingleSelect().clear();
            form.setModel(new Enrollment());
        });

        enrollmentGrid.asSingleSelect().addValueChangeListener(event -> form.setModel(event.getValue()));
        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> enrollmentFilter.clear());
        HorizontalLayout filtering = new HorizontalLayout(enrollmentFilter, clearFilterTextBtn, addEnrollmentButton);
        this.updateEnrollmentList();
        enrollmentGrid.addColumn(Enrollment::getId).setHeader("Enrollment ID");
        enrollmentGrid.addColumn(Enrollment::getStudentID).setHeader("Student ID");
        enrollmentGrid.addColumn(Enrollment::getModuleRunName).setHeader("Module run");
        enrollmentGrid.addColumn(Enrollment::getEvaluation).setHeader("Evaluation");
        enrollmentGrid.addColumn(Enrollment::getStudentLastName).setHeader("Last name");
        enrollmentGrid.addColumn(Enrollment::getStudentStatus).setHeader("Status");
        setHeight("100vh");
        HorizontalLayout main = new HorizontalLayout(enrollmentGrid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();
        enrollmentsPanel.add(filtering, main);

        return enrollmentsPanel;
    }

    /**
     * Create the Professors tab
     * @return Div with added elements
     */
    private Div createProfessorsPanel() {
        Div professorsPanel = new Div();
        professorsPanel.setSizeFull();
        professorsPanel.setVisible(false);

        ProfessorForm form = new ProfessorForm(this);

        professorFilter.setPlaceholder("Find by name or pattern '%', '_'...");
        professorFilter.setWidth("20em");
        professorFilter.setValueChangeMode(ValueChangeMode.EAGER);
        professorFilter.addValueChangeListener(e -> updateProfessorsList());
        Button addCustomerBtn = new Button("Add new professor");
        addCustomerBtn.addClickListener(e -> {
            professorGrid.asSingleSelect().clear();
            form.setModel(new Professor(), true);
        });

        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> professorFilter.clear());
        HorizontalLayout filtering = new HorizontalLayout(professorFilter, clearFilterTextBtn, addCustomerBtn);

        professorGrid.asSingleSelect().addValueChangeListener(event -> {
            form.setModel(event.getValue(), false);
        });
        this.updateProfessorsList();
        professorGrid.addColumn(Professor::getId).setHeader("ID").setFlexGrow(0);
        professorGrid.addColumn(Professor::getFirstName).setHeader("First name");
        professorGrid.addColumn(Professor::getLastName).setHeader("Last name");
        professorGrid.addColumn(Professor::getTag).setHeader("Tag");
        professorGrid.addColumn(Professor::getPassword).setHeader("Password");
        professorGrid.addColumn(Professor::getEmail).setHeader("E-mail");
        professorGrid.addColumn(Professor::getRole).setHeader("User role");

        professorGrid.setColumnReorderingAllowed(true);
        setHeight("100vh");
        HorizontalLayout main = new HorizontalLayout(professorGrid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();
        professorsPanel.add(filtering, main);

        return professorsPanel;
    }

    /**
     * Create the Students tab
     * @return Div with added elements
     */
    private Div createStudentsPanel() {
        Div studentsPanel = new Div();
        studentsPanel.setSizeFull();

        StudentForm form = new StudentForm(this);

        studentFilter.setPlaceholder("Find by name or pattern '%', '_'...");
        studentFilter.setWidth("20em");
        studentFilter.setValueChangeMode(ValueChangeMode.EAGER);
        studentFilter.addValueChangeListener(e -> {
            updateStudentsList();
        });
        Button addCustomerBtn = new Button("Add new student");
        addCustomerBtn.addClickListener(e -> {
            studentsGrid.asSingleSelect().clear();
            form.setModel(new Student(), true);
        });

        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> studentFilter.clear());
        HorizontalLayout filtering = new HorizontalLayout(studentFilter, clearFilterTextBtn, addCustomerBtn);

        studentsGrid.asSingleSelect().addValueChangeListener(event -> {
            form.setModel(event.getValue(), false);
        });
        this.updateStudentsList();
        studentsGrid.addColumn(Student::getId).setHeader("ID").setFlexGrow(0);
        studentsGrid.addColumn(Student::getFirstName).setHeader("First name");
        studentsGrid.addColumn(Student::getLastName).setHeader("Last name");
        studentsGrid.addColumn(Student::getTag).setHeader("Tag");
        studentsGrid.addColumn(Student::getPassword).setHeader("Password");
        studentsGrid.addColumn(Student::getEmail).setHeader("Email");
        studentsGrid.addColumn(Student::getRole).setHeader("User role");
        studentsGrid.addColumn(Student::getStatus).setHeader("Status");

        studentsGrid.setColumnReorderingAllowed(true);
        setHeight("100vh");
        HorizontalLayout main = new HorizontalLayout(studentsGrid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();
        studentsPanel.add(filtering, main);
        return studentsPanel;
    }

    /**
     * updates the grid according to the student set in the student controller
     */
    public void updateStudentsList() {
        studentsGrid.setItems(this.studentsController.getAllStudents(studentFilter.getValue()));
    }

    /**
     *
     * @return the student controller
     */
    public StudentsController getStudentsController(){
        return this.studentsController;
    }

    /**
     * updates the grid according to the professor set in the professor controller
     */
    public void updateProfessorsList() {

       professorGrid.setItems(this.professorController.getAllProfessors(professorFilter.getValue()));
    }

    /**
     *
     * @return the professor controller
     */
    public ProfessorController getProfessorController(){
        return this.professorController;
    }


    /**
     * updates the grid according to the enrollment set in the enrollment controller
     */
    public void updateEnrollmentList() {
        enrollmentGrid.setItems(this.enrollmentsController.getAllEnrollments(enrollmentFilter.getValue()));
    }

    /**
     *
     * @return the enrollment controller
     */
    public EnrollmentsController getEnrollmentsController(){
        return this.enrollmentsController;
    }

    /**
     * updates the grid according to the moduleRun set in the moduleRun controller
     */
    public void updateModuleRunsList() {
        moduleRunGrid.setItems(this.moduleRunsController.getAllModuleRuns(moduleRunFilter.getValue()));
    }

    /**
     *
     * @return the moduleRuns controller
     */
    public ModuleRunsController getModuleRunsController(){
        return this.moduleRunsController;
    }

    /**
     * updates the grid according to the module set in the module controller
     */
    public void updateModulesList() {
        moduleGrid.setItems(this.modulesController.getAllModules(moduleFilter.getValue()));
    }

    /**
     *
     * @return the module controller
     */
    public ModulesController getModulesController(){
        return this.modulesController;
    }


    /**
     * Logs out the user
     * Sets session to null
     */
    private void logout() {
        VaadinSession.getCurrent().setAttribute("currentUser", null);
        UI.getCurrent().navigate("");
    }

}

