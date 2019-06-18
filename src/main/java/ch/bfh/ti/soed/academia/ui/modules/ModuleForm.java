/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.modules;

import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.ui.FormInterface;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

/**
 * Formular for entering, edit (and save) or delete Modules
 */
@SuppressWarnings("serial")
public class ModuleForm extends FormLayout implements FormInterface {

    private TextField name = new TextField("Module name");
    //TextField lastName = new TextField("Last name");
    private ComboBox<ModuleType> moduleType = new ComboBox<>("ModuleType");
    private ComboBox<DegreeProgramme> courseOfStudy = new ComboBox<>("Degree Programme");
    private ComboBox<Professor> moduleManager = new ComboBox<>("Module Manager");
    private TextField description = new TextField("Description");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");

    private Module module;

    private ModuleView view;

    private Binder<Module> binder = new Binder<>(Module.class);

    /**
     * constrcutor
     * sets the Formular of the ModuleView
     * @param view ModuleView
     */
    public ModuleForm(ModuleView view) {
        // Should use CDI; see https://bit.ly/2wwmZd2, end of chap. 6.
        this.view = view;

        moduleType.setItems(ModuleType.values());
        courseOfStudy.setItems(DegreeProgramme.values());
        moduleManager.setItems(view.getProfessorController().getAllProfessors());
        moduleManager.setItemLabelGenerator(Professor::getTag);
        save.getElement().setAttribute("theme", "primary");
        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.cancel());
        delete.addClickListener(e -> this.delete());

        name.addKeyPressListener(e -> this.formChanged());
        //lastName.addKeyPressListener(e -> this.formChanged());

        /*
         * This configures the binder to use all the similarly named editor fields in
         * this form to bind their values with their counterpart in the Customer class.
         */
        binder.bindInstanceFields(this);
        binder.setReadOnly(true);
        binder.addValueChangeListener(e -> this.formChanged());

        HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);
        add(name, moduleType, courseOfStudy, description,moduleManager, buttons);
        setModel(null);
    }

    /**
     * sets the model
     * @param module (Module)
     */
    public void setModel(Module module) {
        this.module = module;
        binder.setBean(module);
        boolean enabled = module != null;
        binder.setReadOnly(!enabled);
        save.setEnabled(false);
        cancel.setEnabled(false);
        delete.setEnabled(enabled);
        if (enabled) {
            name.focus();
        }
    }

    /**
     * deletes the selected Module
     */
    @Override
    public void delete() {
        view.getModulesController().deleteModule(module);
        view.updateList();
        setModel(null);
    }

    /**
     * saves the selected (and edited) Module
     */
    @Override
    public void save() {
        view.getModulesController().save(module);
        view.updateList();
        setModel(null);
    }

    /**
     * cancels the changes that were made
     */
    @Override
    public void cancel() {
        setModel(null);
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
