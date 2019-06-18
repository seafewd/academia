/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui;


import ch.bfh.ti.soed.academia.ui.enrollments.EnrollmentsView;
import ch.bfh.ti.soed.academia.ui.moduleruns.ModuleRunsView;
import ch.bfh.ti.soed.academia.ui.modules.ModuleView;
import ch.bfh.ti.soed.academia.ui.professors.ProfessorsView;
import ch.bfh.ti.soed.academia.ui.students.StudentsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;


/**
 * Main page that contains a button and a template element.
 */
@Route("")
@SuppressWarnings("serial")
public class MainView extends VerticalLayout {

	/**
	 * constructor
	 * Everything is setted in here
	 */
	public MainView() {

		H1 title = new H1("IS-A");

		//Students
		Button studentsBtn = new Button("Students");
		RouterLink studentsLink = new RouterLink(null, StudentsView.class);
		studentsLink.add(studentsBtn);

		Button professorsButton = new Button("Professors");
		RouterLink professorsLink = new RouterLink(null, ProfessorsView.class);
		professorsLink.add(professorsButton);

		Button modulesButton = new Button("Modules");
		RouterLink modulesLink = new RouterLink(null, ModuleView.class);
		modulesLink.add(modulesButton);

		Button moduleRunBtn = new Button("ModuleRuns");
		RouterLink moduleRunLink = new RouterLink(null, ModuleRunsView.class);
		moduleRunLink.add(moduleRunBtn);

		Button enrollmentsBtn = new Button("Enrollments");
		RouterLink enrollmentsLink = new RouterLink(null, EnrollmentsView.class);
		enrollmentsLink.add(enrollmentsBtn);

		//Help
		Button helpBtn = new Button("Help");
		RouterLink helpLink = new RouterLink(null, HelpView.class);
		helpLink.add(helpBtn);

		add(title,studentsLink, professorsLink, modulesLink, moduleRunLink, enrollmentsLink, helpLink);
	}

}
