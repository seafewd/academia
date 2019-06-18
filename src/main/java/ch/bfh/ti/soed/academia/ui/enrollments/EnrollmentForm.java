/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui.enrollments;

import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.ui.FormInterface;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

/**
 * Formular for entering, edit (and save) or delete Enrollments
 */
@SuppressWarnings("serial")
public class EnrollmentForm extends FormLayout implements FormInterface {

    private TextField id = new TextField("Enrollment ID");
    private ComboBox<Student> student = new ComboBox<>("Student");
    private ComboBox<Evaluation> evaluation = new ComboBox<>("Evaluation");
    private ComboBox<ModuleRun> moduleRun = new ComboBox<>("ModuleRun");
    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");

    private Enrollment enrollment;
    private EnrollmentsView view;

    private Binder<Enrollment> binder = new Binder<>(Enrollment.class);

    /**
     * constrcutor
     * sets the Formular of the EnrollmentsView
     * @param view EnrollmentView
     */
    public EnrollmentForm(EnrollmentsView view) {
        this.view = view;

        evaluation.setItems(Evaluation.values());
        save.getElement().setAttribute("theme", "primary");
        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.cancel());
        delete.addClickListener(e -> this.delete());

        //get list of all Student IDs
        student.setItems(view.getStudentsController().getAllStudents(""));
        student.setItemLabelGenerator(Student::getIdAsString);

        moduleRun.setItems(view.getModuleRunsController().getAllModuleRuns(""));
        moduleRun.setItemLabelGenerator(ModuleRun::getIdAsString);

        //listeners
        student.addValueChangeListener(e -> this.formChanged());
        evaluation.addValueChangeListener(e -> this.formChanged());
        moduleRun.addValueChangeListener(e -> this.formChanged());

        /*
         * This configures the binder to use all the similarly named editor fields in
         * this form to bind their values with their counterpart in the Customer class.
         */
        binder.bind(id, e -> e.getId() + "", null);
        binder.bindInstanceFields(this);
        binder.setReadOnly(true);
        binder.addValueChangeListener(e -> this.formChanged());

        HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);

        add(student, evaluation, moduleRun, buttons);
        setModel(null);
    }

    /**
     * sets the model
     * @param enrollment (Enrollment)
     */
    public void setModel(Enrollment enrollment) {
        this.enrollment = enrollment;
        binder.setBean(enrollment);
        boolean enabled = enrollment != null;
        binder.setReadOnly(!enabled);
        save.setEnabled(false);
        cancel.setEnabled(false);
        delete.setEnabled(enabled);
        if (enabled) {
            student.focus();
        }
    }

    /**
     * deletes the selected Enrollment
     */
    @Override
    public void delete() {
        view.getEnrollmentsController().deleteEnrollment(enrollment);
        view.updateList();
        setModel(null);
    }

    /**
     * saves the selected (and edited) Enrollment
     */
    @Override
    public void save() {
        view.getEnrollmentsController().save(enrollment);
        view.updateList();
        setModel(null);
    }

    /**
     * cancels the changes that were made on an Enrollment
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
