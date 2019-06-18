/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.moduleruns;

import ch.bfh.ti.soed.academia.backend.models.Module;
import ch.bfh.ti.soed.academia.backend.models.ModuleRun;
import ch.bfh.ti.soed.academia.backend.models.Semester;
import ch.bfh.ti.soed.academia.ui.FormInterface;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

/**
 * Formular for entering, edit (and save) or delete ModuleRuns
 */
public class ModuleRunForm extends FormLayout implements FormInterface {
    private TextField id = new TextField("ModuleRun ID");
    private ComboBox<Module> module = new ComboBox<>("Module ID");
    private ComboBox<Semester> semester = new ComboBox<>("Semester");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");

    private ModuleRun moduleRun;
    private ModuleRunsView view;

    private Binder<ModuleRun> binder = new Binder<>(ModuleRun.class);

    /**
     * constrcutor
     * sets the Formular of the ModuleRunView
     * @param view ModuleRunview
     */
    public ModuleRunForm(ModuleRunsView view) {
        this.view = view;

        save.getElement().setAttribute("theme","primary");
        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.cancel());
        delete.addClickListener(e -> this.delete());

        //get list of all module run IDs TODO: CHANGE TO MODULE IDs
        this.module.setItems(view.getModulesController().getAllModules(""));
        module.setItemLabelGenerator(Module::getIdAsString);
        semester.setItems(Semester.values());

        id.addKeyPressListener(e -> this.formChanged());
        module.addValueChangeListener(e -> this.formChanged());

        /*
         * This configures the binder to use all the similarly named editor fields in
         * this form to bind their values with their counterpart in the Customer class.
         */
        binder.bind(id, m -> m.getId() + "", null);
        //binder.bind(module, m -> m.getModule().getId() + "", null);
        binder.bindInstanceFields(this);
        binder.setReadOnly(true);
        binder.addValueChangeListener(e -> this.formChanged());

        HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);

        add(module, semester, buttons);
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
            module.focus();
        }
    }

    /**
     * deletes the selected ModuleRun
     */
    @Override
    public void delete() {
        view.getModuleRunsController().deleteModuleRun(moduleRun);
        view.updateList();
        setModel(null);
    }

    /**
     * saves the selected (and edited) ModuleRun
     */
    @Override
    public void save() {
        view.getModuleRunsController().save(moduleRun);
        view.updateList();
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
