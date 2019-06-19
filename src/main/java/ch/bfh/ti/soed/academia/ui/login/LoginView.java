/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.login;

import ch.bfh.ti.soed.academia.backend.controllers.LoginController;
import ch.bfh.ti.soed.academia.backend.models.Role;
import ch.bfh.ti.soed.academia.backend.models.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * The Login view.
 */
@Route("")
public class LoginView extends FormLayout {

    @Inject
    private LoginController loginController;

    private TextField tag = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private Button loginButton = new Button("Login");
    private User user;

    public LoginView() {
        System.out.println("User navigated to LoginView");

        VerticalLayout form = new VerticalLayout();
        H1 mainTitle = new H1("Academia Student Administration System");
        H2 loginHeader = new H2("Log in with your username & password");
        loginButton.getElement().setAttribute("theme", "primary");
        Button studentButton = new Button("Student: wongs1");
        studentButton.addClickListener(e -> setTestValue("wongs1", "pazzo"));
        studentButton.setWidth("300px");

        Button professorButton = new Button("Professor: lovem1");
        professorButton.addClickListener(e -> setTestValue("lovem1", "hello"));
        professorButton.setWidth("300px");

        Button adminButton = new Button("Admin: admin");
        adminButton.addClickListener(e -> setTestValue("admin", "admin"));
        adminButton.setWidth("300px");


        tag.addKeyPressListener(e -> this.formChanged());
        password.addKeyPressListener(e -> this.formChanged());
        loginButton.setEnabled(false);
        loginButton.addClickListener(e ->
            this.login() );

        //tab index and auto focus
        tag.focus();
        tag.setTabIndex(1);
        password.setTabIndex(2);

        form.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        form.add(mainTitle, loginHeader, tag, password, loginButton, professorButton, studentButton,adminButton);
        add(form);
    }

    /**
     * init needs to be executed after dependency injection is done to perform any initialization
     */
    @PostConstruct
    public void init(){}

    /**
     * Check the authentication of the user
     */
    public void login() {
        try {
            this.user = this.loginController.login(tag.getValue(),password.getValue());
            if (user != null) {
                VaadinSession.getCurrent().setAttribute("currentUser", this.user);
                if(user.getRole() == Role.STUDENT)
                    UI.getCurrent().navigate("studentview");
                if(user.getRole() == Role.PROFESSOR)
                    UI.getCurrent().navigate("professorview");
                if(user.getRole() == Role.ADMIN)
                    UI.getCurrent().navigate("adminview");
            }
            else
                Notification.show("Wrong credentials!");
        }catch (Exception ex){
            Notification.show("Wrong credentials!");
        }
    }

    /**
     * Enable the login button
     */
    public void formChanged(){
        loginButton.setEnabled(true);
    }

    /**
     * Sets TextField values for quick testing
     * @param name username String
     * @param pw password   String
     */
    private void setTestValue(String name, String pw){
        tag.setValue(name);
        password.setValue(pw);
        formChanged();
    }
}
