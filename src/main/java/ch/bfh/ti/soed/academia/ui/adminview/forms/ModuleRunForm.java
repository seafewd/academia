/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.adminview.forms;

import ch.bfh.ti.soed.academia.backend.models.Module;
import ch.bfh.ti.soed.academia.backend.models.ModuleRun;
import ch.bfh.ti.soed.academia.backend.models.Semester;
import ch.bfh.ti.soed.academia.ui.FormInterface;
import ch.bfh.ti.soed.academia.ui.adminview.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import javax.ejb.EJBTransactionRolledbackException;

/**
 * Formular for entering, edit (and save) or delete ModuleRuns
 */
public class ModuleRunForm extends FormLayout implements FormInterface {
    private TextField id = new TextField("ModuleRun ID");
    private ComboBox<Module> moduleComboBox = new ComboBox<>("Module ID");
    private ComboBox<Semester> semester = new ComboBox<>("Semester");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");

    private ModuleRun moduleRun;
    private AdminView view;

    private Binder<ModuleRun> binder = new Binder<>(ModuleRun.class);

    /**
     * constrcutor
     * sets the Formular of the ModuleRunView
     * @param view ModuleRunview
     */
    public ModuleRunForm(AdminView view) {
        this.view = view;

        save.getElement().setAttribute("theme","primary");
        save.addClickListener(e -> {
            try {
                this.save();
            } catch (Exception ex) {
                Notification.show("Something went wrong! Try again.");
                ex.printStackTrace();
            }
        });
        cancel.addClickListener(e -> this.cancel());
        delete.addClickListener(e -> {
            try {
                this.delete();
            } catch (EJBTransactionRolledbackException sqle) {
                Notification.show("Integrity constraint violation! Try deleting dependencies first.");
                sqle.printStackTrace();
            }
        });

        moduleComboBox.setItems(view.getModulesController().getAllModules(""));
        moduleComboBox.setItemLabelGenerator(Module::getIdAsString);
        semester.setItems(Semester.values());

        id.addKeyPressListener(e -> this.formChanged());
        moduleComboBox.addValueChangeListener(e -> this.formChanged());
        semester.addValueChangeListener(e -> this.formChanged());

        /*
         * This configures the binder to use all the similarly named editor fields in
         * this form to bind their values with their counterpart in the Customer class.
         */
        binder.bind(id, m -> m.getId() + "", null);
        //binder.bind(moduleComboqBox, ModuleRun::getModule, null);
        binder.bindInstanceFields(this);
        binder.setReadOnly(true);
        binder.addValueChangeListener(e -> this.formChanged());

        HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);

        add(moduleComboBox, semester, buttons);
        setModel(null);
    }

    /**
     * sets the model
     * @param moduleRun (moduleRun)
     */
    public void setModel(ModuleRun moduleRun) {
        this.moduleRun = moduleRun;
        binder.setBean(moduleRun);
        boolean enabled = moduleRun != null;
        binder.setReadOnly(!enabled);
        save.setEnabled(false);
        cancel.setEnabled(false);
        delete.setEnabled(enabled);
        if (enabled) {
            moduleComboBox.focus();
        }
    }

    /**
     * deletes the selected ModuleRun
     */
    @Override
    public void delete() {
        view.getModuleRunsController().deleteModuleRun(moduleRun);
        view.updateModuleRunsList();
        setModel(null);
    }

    /**
     * saves the selected (and edited) ModuleRun
     */
    @Override
    public void save() {
        view.getModuleRunsController().save(moduleRun);
        view.updateModuleRunsList();
        setModel(null);
    }

    /**
     * cancels the changes that were made on a ModuleRun
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
