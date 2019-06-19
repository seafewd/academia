/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.adminview.forms;

import ch.bfh.ti.soed.academia.backend.models.Role;
import ch.bfh.ti.soed.academia.backend.utilities.password.PasswordGenerator;
import ch.bfh.ti.soed.academia.ui.FormInterface;
import ch.bfh.ti.soed.academia.ui.adminview.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import ch.bfh.ti.soed.academia.backend.models.Professor;

/**
 * Formular for entering, edit (and save) or delete Professors
 */
@SuppressWarnings("serial")
public class ProfessorForm extends FormLayout implements FormInterface {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("E-mail");
    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");

    private Professor professor;

    private AdminView view;

    private Binder<Professor> binder = new Binder<>(Professor.class);

    private boolean isNew;

    /**
     * constrcutor
     * sets the Formular of the ProfessorView
     * @param view ProfesorView
     */
    public ProfessorForm(AdminView view) {
        this.view = view;

        save.getElement().setAttribute("theme", "primary");
        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.cancel());
        delete.addClickListener(e -> this.delete());

        firstName.addKeyPressListener(e -> this.formChanged());
        lastName.addKeyPressListener(e -> this.formChanged());
        email.addKeyPressListener(e -> this.formChanged());

        /*
         * This configures the binder to use all the similarly named editor fields in
         * this form to bind their values with their counterpart in the Customer class.
         */
        binder.bindInstanceFields(this);
        binder.setReadOnly(true);
        binder.addValueChangeListener(e -> this.formChanged());

        HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);
        add(firstName, lastName, email, buttons);
        setModel(null, false);
    }

    /**
     * sets the model
     * @param professor (Professor)
     * @param isNew (bbolean)
     */
    public void setModel(Professor professor, boolean isNew) {
        this.isNew = isNew;
        this.professor = professor;
        binder.setBean(professor);
        boolean enabled = professor != null;
        binder.setReadOnly(!enabled);
        save.setEnabled(false);
        cancel.setEnabled(false);
        delete.setEnabled(enabled);
        if (enabled) {
            firstName.focus();
        }
    }

    /**
     * deletes the selected Professor
     */
    @Override
    public void delete() {
        view.getProfessorController().deleteProfessor(professor);
        view.updateProfessorsList();
        setModel(null, false);
    }

    /**
     * saves the selected (and edited) Professor
     */
    @Override
    public void save() {
        if (isNew){
            try {
                professor.setPassword(PasswordGenerator.generatePassword(15));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        professor.setTag(Professor.generateTag(professor.getFirstName(), professor.getLastName()));
        professor.setRole(Role.PROFESSOR);
        view.getProfessorController().save(professor);
        view.updateProfessorsList();
        setModel(null, false);
    }

    /**
     * cancels the changes that were made on a Professor
     */
    @Override
    public void cancel() {
        setModel(null, false);
    }

    /**
     * method thats called when the form was changed, edites a few parameters
     */
    @Override
    public void formChanged() {
        save.setEnabled(true);
        cancel.setEnabled(true);
    }
}
