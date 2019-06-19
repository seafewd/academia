/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.studentsview;

import ch.bfh.ti.soed.academia.backend.controllers.EnrollmentsController;
import ch.bfh.ti.soed.academia.backend.controllers.ModuleRunsController;
import ch.bfh.ti.soed.academia.backend.controllers.StudentsController;
import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.backend.utilities.pdf.PdfPrinter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Student view.
 */
@Route("studentview")
public class StudentView extends VerticalLayout {

    @Inject
    private StudentsController studentsController;

    @Inject
    private EnrollmentsController enrollmentsController;

    @Inject
    private ModuleRunsController moduleRunsController;

    private User currentUser;
    private Student currentStudent;

    private Set<ModuleRun> moduleRunsChecked = new HashSet<>();

    private TextField email;
    private PasswordField pw1;
    private PasswordField pw2;
    private PasswordField pw;

    /**
     * View for Student
     */
    public StudentView() {
        this.currentUser = (User)VaadinSession.getCurrent().getAttribute("currentUser");
        if (this.currentUser != null && this.currentUser.getRole() == Role.STUDENT){
            H1 title = new H1("Student View");
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
        if (currentUser != null && this.currentUser.getRole() == Role.STUDENT){
            this.currentStudent = getStudentByTag();
            H1 title = new H1("Logged in as " + this.currentStudent.getFirstName() + " " + this.currentStudent.getLastName());
            Button logoutBtn = new Button("Logout");
            logoutBtn.addClickListener(e -> this.logout());

            //print confirmation of enrollment button
            Button printButton = new Button("Print confirmation of enrollment");
            printButton.addClickListener(e -> {
                PdfPrinter printer = new PdfPrinter();
                String filePath = printer.printPDF(currentStudent);
                Notification.show("Successfully generated enrollment confirmation PDF at " + filePath);
            });

            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.add(logoutBtn, printButton);

            add(title, horizontalLayout);
            this.createTabAndPages();
        }
    }

    /**
     *
     * @return the Student searched by tag
     */
    private Student getStudentByTag(){
        return this.studentsController.getStudentByTag(this.currentUser.getTag());
    }

    /**
     * Creation of the tabs and pages for student
     */
    private void createTabAndPages(){

        Tab tab1 = new Tab("Personal Information");
        Div personalInformationPage = this.createPersonalInformationPage();

        Tab tab2 = new Tab("Enrollments");
        Div personalModulesPage = this.createModulesPage();

        Tab tab3 = new Tab("Module runs");
        Div moduleRunsPage = this.createModuleRunsPage();

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tab1, personalInformationPage);
        tabsToPages.put(tab2, personalModulesPage);
        tabsToPages.put(tab3, moduleRunsPage);

        Tabs tabs = new Tabs(tab1, tab2,tab3);
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.setWidth("75%");

        Div pages = new Div(personalInformationPage, personalModulesPage, moduleRunsPage);
        Set<Component> pagesShown = Stream.of(personalInformationPage)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        pages.setWidth("75%");
        add(tabs, pages);
    }

    /**
     * Creates a personal information tab
     * @return Div with added elements
     */
    private Div createPersonalInformationPage(){
        Div personalInformationPage = new Div();
        personalInformationPage.setSizeFull();

        Label heading = new Label("View or change your personal information");
        TextField firstName = new TextField("First name");
        firstName.setValue(currentStudent.getFirstName());
        firstName.setReadOnly(true);
        TextField lastName = new TextField("Last name");
        lastName.setValue(currentStudent.getLastName());
        lastName.setReadOnly(true);

        email = new TextField("E-mail");
        email.setValue(currentStudent.getEmail());

        Button save = new Button("save");
        save.setEnabled(false);
        email.addKeyPressListener(e -> save.setEnabled(true));


        Label headingPw = new Label("Change your Password");
        Button changepw = new Button("Change Password");
        changepw.setEnabled(false);
        pw = new PasswordField("Old Password");
        pw.addKeyPressListener(e -> changepw.setEnabled(true));
        pw1 = new PasswordField("New Password");
        pw1.addKeyPressListener(e -> changepw.setEnabled(true));
        changepw.addClickListener(e -> {
            try {
                changePassword();
            } catch (InvalidKeySpecException e1) {
                e1.printStackTrace();
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            }
        });

        pw2 = new PasswordField("Repeat Password");
        save.addClickListener(e -> {
            this.changeEmailAddress();
        });
        firstName.setWidth("500");
        lastName.setWidth("500");
        email.setWidth("500");

        VerticalLayout informationLayout = new VerticalLayout();
        informationLayout.add(heading, firstName, lastName, email, save);
        VerticalLayout pwLayout = new VerticalLayout(headingPw, pw1, pw2, changepw);
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(informationLayout, pwLayout);

        personalInformationPage.add(layout);
        return personalInformationPage;
    }

    /**
     * Changes the Student's e-mail address
     */
    private void changeEmailAddress() {
        currentStudent.setEmail(email.getValue());
        studentsController.save(currentStudent);
        Notification.show("Email successfully changed");
    }

    /**
     * Changes the Student's password after a check
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    private void changePassword() throws InvalidKeySpecException, NoSuchAlgorithmException {
        if(pw1.getValue().length() != 0) {
            if (pw1.getValue().equals(pw2.getValue()) && pw1.getValue().length() != 0) {
                Notification.show("Password successfully changed!");
                currentStudent.setPassword(pw1.getValue());
                studentsController.save(currentStudent);
            } else {
                Notification.show("The passwords don't match. Try again!");
                pw1.setValue("");
                pw2.setValue("");
            }
        }
        else {
            Notification.show("Password can not be empty");
        }

    }

    /**
     * Create the Modules tab
     * @return Div with added elements
     */
    private Div createModulesPage(){
        Div personalModulesPage = new Div();
        personalModulesPage.setSizeFull();
        personalModulesPage.setVisible(false);
        List<Enrollment> enrollments = this.enrollmentsController.getAllEnrollmentsByStudentTag(this.currentStudent.getTag());
        if (enrollments == null){
            personalModulesPage.setText("You are not enrolled to any module runs yet.");
        }else {
            Grid<Enrollment> grid = new Grid<>();
            grid.setHeight("50vh");
            grid.setItems(enrollments);
            grid.addColumn(enrollment -> enrollment.getModuleRun().getModule().getDescription()).setHeader("Module");
            grid.addColumn(Enrollment::getModuleRunName).setHeader("Module run");
            grid.addColumn(enrollment -> enrollment.getModuleRun().getSemester()).setHeader("Semester");
            grid.addColumn(Enrollment::getEvaluation).setHeader("Evaluation");
            grid.setColumnReorderingAllowed(true);
            personalModulesPage.add(grid);
        }
        return personalModulesPage;
    }

    /**
     * Create the ModuleRuns tab
     * @return Div with added elements
     */
    private Div createModuleRunsPage(){
        Div moduleRunsPage = new Div();
        moduleRunsPage.setSizeFull();
        moduleRunsPage.setVisible(false);
        List<ModuleRun> moduleRuns = this.moduleRunsController.getModulesRunNotSubscribed(this.currentStudent.getTag());
        Label gridTitle = new Label("Subscribe to module runs here. Use the save button at the bottom of the page to register the subscriptions.");
        Button saveButton = new Button("Save subscriptions");
        saveButton.setEnabled(false);

        if (moduleRuns == null){
            moduleRunsPage.setText("You are not enrolled to any module runs yet.");
        }else {
            Grid<ModuleRun> grid = new Grid<>();
            grid.setHeight("50vh");
            grid.setItems(moduleRuns);
            grid.addColumn(moduleRun -> moduleRun.getModule().getDescription()).setHeader("Module");
            grid.addColumn(ModuleRun::getName).setHeader("Module run name");
            grid.addColumn(ModuleRun::getSemester).setHeader("Semester");
            grid.addColumn(ModuleRun::getProfessorLastNames).setHeader("Professors");
           // grid.addColumn(moduleRun -> moduleRun.getModule().getName()).setHeader("Module Name");

            //button to handle subscriptions
            grid.addComponentColumn(moduleRun -> {
               Button subscribeToModuleRunButton = new Button("Subscribe");
               subscribeToModuleRunButton.setClassName("subscribeToModuleRunButton");
               subscribeToModuleRunButton.setWidth("150px");
               subscribeToModuleRunButton.addClickListener(e -> {
                   if (moduleRunsChecked.contains(moduleRun)) {
                       moduleRunsChecked.remove(moduleRun);
                       subscribeToModuleRunButton.setText("Subscribe");
                   } else {
                       moduleRunsChecked.add(moduleRun);
                       subscribeToModuleRunButton.setText("✔ Added");
                   }
                   if (moduleRunsChecked.isEmpty()) {
                       saveButton.setEnabled(false);
                   } else
                       saveButton.setEnabled(true);
               });
               return subscribeToModuleRunButton;
            }).setHeader("Subscribe to module runs");
            grid.setColumnReorderingAllowed(true);

            //button to save the subscriptions to Student object
            saveButton.addClickListener(e -> {
                for (ModuleRun moduleRun : moduleRunsChecked) {
                    this.moduleRunsController.subscribe(currentStudent, moduleRun);
                }
                try {
                    Notification.show("Successfully subscribed!");
                    Thread.sleep(2000);
                } catch(InterruptedException ie) {
                    ie.printStackTrace();
                } finally {
                    UI.getCurrent().getPage().reload();
                }
            });

            moduleRunsPage.add(gridTitle, grid, saveButton);
        }
        return moduleRunsPage;
    }

    /**
     * Logs out the user
     */
    private void logout() {
        VaadinSession.getCurrent().setAttribute("currentUser", null);
        UI.getCurrent().navigate("");
    }
}

