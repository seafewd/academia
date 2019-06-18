/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
/**
 * Page for getting help if there are problems. Not much implemented at this moment.
 */
@Route("help")
@SuppressWarnings("serial")
public class HelpView extends VerticalLayout {

	private String loremIpsum = "If you need help ask Alessandro :D";

	/**
	 * constructor
	 * Everything is setted in here
	 */
	public HelpView() {
		TextArea helpText = new TextArea();
		helpText.setSizeFull();
		helpText.setValue(loremIpsum);
		helpText.setReadOnly(true);
		add(helpText);

		Button okBtn = new Button("Ok");
		RouterLink homeLink = new RouterLink(null, MainView.class);
		homeLink.add(okBtn);
		add(homeLink);
	}
}
