/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.professorsview;

import ch.bfh.ti.soed.academia.backend.controllers.ProfessorController;
import ch.bfh.ti.soed.academia.backend.controllers.ModuleRunsController;
import ch.bfh.ti.soed.academia.backend.controllers.ModulesController;
import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.backend.models.Module;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Professor view.
 */
@Route("professorview")
public class ProfessorView extends VerticalLayout {

    @Inject
    private ProfessorController professorController;

    @Inject
    private ModulesController modulesController;

    @Inject
    private ModuleRunsController moduleRunsController;

    private User currentUser;
    private Professor currentProfessor;

    private TextField email;
    private PasswordField pw1;
    private PasswordField pw2;
    private PasswordField pw;

    /**
     * View for Professors
     */
    public ProfessorView() {
        this.currentUser = (User)VaadinSession.getCurrent().getAttribute("currentUser");
        if (this.currentUser != null && this.currentUser.getRole() == Role.PROFESSOR){
            H1 title = new H1("Professor View");
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
        if (currentUser != null && this.currentUser.getRole() == Role.PROFESSOR){
            this.currentProfessor = getProfessorByTag();
            H1 title = new H1("Logged in as " + this.currentProfessor.getFirstName() + " " + this.currentProfessor.getLastName());
            add(title);
            Button logoutBtn = new Button("Logout");
            logoutBtn.addClickListener(e -> this.logout());
            add(title, logoutBtn);
            this.createTabAndPages();
        }
    }

    /**
     *
     * @return the Professor searched by tag
     */
    private Professor getProfessorByTag(){
        return this.professorController.getProfessorByTag(this.currentUser.getTag());
    }

    /**
     * Creation of the tabs and pages for student
     */
    private void createTabAndPages(){

        Tab tab1 = new Tab("Personal Information");
        Div personalInformationPage = this.createPersonalInformationPage();

        Tab tab2 = new Tab("Managed Modules");
        Div managedModulesPage = this.createManagedModulesPage();

        Tab tab3 = new Tab("Module Runs");
        Div taughtModuleRunsPage = this.createTaughtModuleRunsPage();

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tab1, personalInformationPage);
        tabsToPages.put(tab2, managedModulesPage);
        tabsToPages.put(tab3, taughtModuleRunsPage);

        Tabs tabs = new Tabs(tab1, tab2, tab3);
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.setWidth("75%");

        Div pages = new Div(personalInformationPage, managedModulesPage, taughtModuleRunsPage);
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
     * Create Personal Information tab
     * @return Div with added elements
     */
    private Div createPersonalInformationPage(){
        Div personalInformationPage = new Div();
        personalInformationPage.setSizeFull();

        Label heading = new Label("View or change your personal information");
        TextField firstName = new TextField("First name");
        firstName.setValue(currentProfessor.getFirstName());
        firstName.setReadOnly(true);
        TextField lastName = new TextField("Last name");
        lastName.setValue(currentProfessor.getLastName());
        lastName.setReadOnly(true);

        email = new TextField("E-mail");
        email.setValue(currentProfessor.getEmail());

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
     * Change the Professor's e-mail address
     */
    private void changeEmailAddress() {
        currentProfessor.setEmail(email.getValue());
        professorController.save(currentProfessor);
        Notification.show("Email successfully changed");
    }

    private void changePassword() throws InvalidKeySpecException, NoSuchAlgorithmException {
        if(pw1.getValue().length() != 0) {
            if (pw1.getValue().equals(pw2.getValue()) && pw1.getValue().length() != 0) {
                Notification.show("Password successfully changed!");
                currentProfessor.setPassword(pw1.getValue());
                professorController.save(currentProfessor);
            } else {
                Notification.show("Passwords don't match. Try again!");
                pw1.setValue("");
                pw2.setValue("");
            }
        }
        else {
            Notification.show("Password cannot be empty!");
        }
    }

    /**
     * Create tab view for managed Module objects
     * @return Div containing a grid of Module objects managed by this Professor object
     */
    private Div createManagedModulesPage(){
        Div managedModulesPage = new Div();
        managedModulesPage.setSizeFull();
        managedModulesPage.setVisible(false);

        List<Module> modules = this.modulesController.getAllManagedModulesByProfessorTag(this.currentProfessor.getTag());

        if (modules == null){
            managedModulesPage.setText("You're not yet managing any modules!");
        } else {
            managedModulesPage.setText("Your managed modules");
            Grid<Module> grid = new Grid<>();
            grid.setHeight("50vh");
            grid.setItems(modules);
            grid.addColumn(Module::getDescription).setHeader("Module");
            grid.addColumn(Module::getName).setHeader("Code");
            grid.setColumnReorderingAllowed(true);
            managedModulesPage.add(grid);
        }
        return managedModulesPage;
    }

    /**
     * Create taught ModuleRuns tab
     * @return Div with added elements
     */
    private Div createTaughtModuleRunsPage(){
        Div taughtModuleRunsPage = new Div();
        taughtModuleRunsPage.setSizeFull();
        taughtModuleRunsPage.setVisible(false);

        List<ModuleRun> moduleRuns = this.moduleRunsController.getModulesRunByProfessorTag(currentProfessor.getTag());

        if (moduleRuns.isEmpty()){
            taughtModuleRunsPage.setText("You're not yet teaching any module runs!");
        } else {
            taughtModuleRunsPage.setText("Your taught module runs");
            Grid<ModuleRun> grid = new Grid<>();
            grid.setHeight("50vh");
            grid.setItems(moduleRuns);
            grid.addColumn(moduleRun -> moduleRun.getModule().getDescription()).setHeader("Module");
            grid.addColumn(ModuleRun::getName).setHeader("Module Run");
            grid.addColumn(ModuleRun::getEnrollmentCount).setHeader("Number of enrolled students");
            grid.setColumnReorderingAllowed(true);
            taughtModuleRunsPage.add(grid);
        }
        return taughtModuleRunsPage;
    }

    /**
     * Logs out the user
     */
    private void logout() {
        VaadinSession.getCurrent().setAttribute("currentUser", null);
        UI.getCurrent().navigate("");
    }

}

