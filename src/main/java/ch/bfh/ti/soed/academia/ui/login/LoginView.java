/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.login;

import ch.bfh.ti.soed.academia.backend.controllers.LoginController;
import ch.bfh.ti.soed.academia.backend.models.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * The Login view.
 */
@Route("login")
public class LoginView extends FormLayout {

    @Inject
    private LoginController loginController;

    private TextField tag = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private Button loginButton = new Button("Login");
    private User user;

    public LoginView() {
        H1 title = new H1("Login");
        loginButton.getElement().setAttribute("theme", "primary");
        tag.addKeyPressListener(e -> this.formChanged());
        password.addKeyPressListener(e -> this.formChanged());
        loginButton.setEnabled(false);
        loginButton.addClickListener(e -> this.login());
        add(title, tag, password, loginButton);
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

            }
            else
                Notification.show("Wrong credentials!");
        }catch (Exception ex){
            Notification.show("Wrong credentials!");
        }
    }
    public void formChanged(){
        loginButton.setEnabled(true);
    }
}
