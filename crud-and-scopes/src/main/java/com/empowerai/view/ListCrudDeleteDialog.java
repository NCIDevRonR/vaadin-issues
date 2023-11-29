package com.empowerai.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public abstract class ListCrudDeleteDialog {
	protected HorizontalLayout btnLayout = new HorizontalLayout();
	
	protected VerticalLayout rowLayout = new VerticalLayout();

	protected String MAX_WIDTH = "400px";
	protected String BUTTON_WIDTH = "123px";
	
	protected Dialog deleteDialog = new Dialog();
	protected Label headerLabel = new Label("");
	protected Label messageLabel = new Label("");
	protected HorizontalLayout buttonLayout = new HorizontalLayout();
	protected Button cancelBtn = new Button("Cancel");
	protected Button confirmBtn = new Button("Delete");
	
	protected ListCrudDeleteDialog() {

		cancelBtn.addClickListener(event -> deleteDialog.close());
		confirmBtn.setThemeName("error primary");

		buttonLayout.add(cancelBtn, confirmBtn);
		rowLayout.add(headerLabel, messageLabel, buttonLayout);
		deleteDialog.add(rowLayout);		
		
	}

	
}
