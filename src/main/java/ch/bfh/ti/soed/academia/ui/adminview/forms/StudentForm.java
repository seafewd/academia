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
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import ch.bfh.ti.soed.academia.backend.models.Student;
import ch.bfh.ti.soed.academia.backend.models.StudentStatus;

/**
 * Formular for entering, edit (and save) or delete Students
 */
@SuppressWarnings("serial")
public class StudentForm extends FormLayout implements FormInterface {

	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");
	private ComboBox<StudentStatus> status = new ComboBox<>("Status");
	private TextField email = new TextField("Email");
	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");
	private Button delete = new Button("Delete");

	private Student student;

	private AdminView view;

	private Binder<Student> binder = new Binder<>(Student.class);

	private boolean isNew;

	/**
	 * constrcutor
	 * sets the Formular of the StudentView
	 * @param view StudentView
	 */
	public StudentForm(AdminView view) {
		this.view = view;
		status.setItems(StudentStatus.values());
		save.getElement().setAttribute("theme", "primary");
		save.addClickListener(e -> this.save());
		cancel.addClickListener(e -> this.cancel());
		delete.addClickListener(e -> this.delete());

		firstName.addKeyPressListener(e -> this.formChanged());
		lastName.addKeyPressListener(e -> this.formChanged());

		/*
		 * This configures the binder to use all the similarly named editor fields in
		 * this form to bind their values with their counterpart in the Customer class.
		 */
		binder.bindInstanceFields(this);
		binder.setReadOnly(true);
		binder.addValueChangeListener(e -> this.formChanged());

		HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);
		add(firstName, lastName, status, email, buttons);
		setModel(null, false);
	}

	/**
	 * sets the model
	 * @param student (Student)
	 * @param isNew (boolean)
	 */
	public void setModel(Student student, boolean isNew) {
		this.isNew = isNew;
		this.student = student;
		binder.setBean(student);
		boolean enabled = student != null;
		binder.setReadOnly(!enabled);
		save.setEnabled(false);
		cancel.setEnabled(false);
		delete.setEnabled(enabled);
		if (enabled) {
			firstName.focus();
		}
	}

	/**
	 * deletes the selected Student
	 */
	@Override
	public void delete() {
		view.getStudentsController().deleteStudent(student);
		view.updateStudentsList();
		setModel(null, false);
	}

	/**
	 * saves the selected (and edited) Student
	 */
	@Override
	public void save() {
		if (isNew){
			try {
				student.setPassword(PasswordGenerator.generatePassword(15));
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}
		student.setTag(Student.generateTag(student.getFirstName(), student.getLastName()));
		student.setRole(Role.STUDENT);
		view.getStudentsController().save(student);
		view.updateStudentsList();
		setModel(null, false);
	}

	/**
	 * cancels the changes that were made on a Student
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
