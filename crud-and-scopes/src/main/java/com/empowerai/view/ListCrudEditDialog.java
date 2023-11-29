package com.empowerai.view;

import java.util.List;

import javax.inject.Inject;
import com.empowerai.control.ListCrudGridPager;
import com.empowerai.service.ListCrudService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
//import com.vaadin.flow.component.
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

public abstract class ListCrudEditDialog {

    @Inject
//	ListCrudService mainService;
	
	ListCrudGridPager gridPager;	

//	protected HorizontalLayout btnLayout = new HorizontalLayout();
	
	protected VerticalLayout rowLayout = new VerticalLayout();
	protected HorizontalLayout fieldsLayout[] = new HorizontalLayout[] {
		new HorizontalLayout(),
		new HorizontalLayout()
	};

	protected String MAX_WIDTH = "400px";
	protected String BUTTON_WIDTH = "123px";
	protected Dialog editDialog = new Dialog();
	protected TextField idField = new TextField("ID");
	protected Label messageLabel = new Label("");

	protected HorizontalLayout buttonLayout = new HorizontalLayout();
	protected Button cancelBtn = new Button("Cancel");
	protected Button saveBtn = new Button("Save");
	
	
	public ListCrudEditDialog(ListCrudService service, ListCrudGridPager gridPager) {

	}

	public abstract void openDialog();

	public abstract void clearInputFields();

}

