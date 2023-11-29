package com.empowerai.view;

import java.util.List;

import javax.inject.Inject;

import com.empowerai.control.CityInfoGridPager;
import com.empowerai.control.StatesComboBox;
import com.empowerai.entity.CityInfo;
import com.empowerai.service.CityInfoService;
import com.empowerai.util.Utils;
import com.vaadin.cdi.annotation.RouteScopeOwner;
import com.vaadin.cdi.annotation.VaadinServiceEnabled;
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

public class CityInfoEditDialog extends ListCrudEditDialog {
    @Inject
    CityInfoService cityInfoSvc;
//	
//	CityInfoGridPager gridPager;	

//	CityInfo selCityInfo;
//	private HorizontalLayout btnLayout = new HorizontalLayout();

//	private VerticalLayout rowLayout = new VerticalLayout();
//	private HorizontalLayout fieldsLayout[] = new HorizontalLayout[] {
//		new HorizontalLayout(),
//		new HorizontalLayout()
//	};

	private String MAX_WIDTH = "400px";
	private String BUTTON_WIDTH = "123px";
//	private Dialog editDialog = new Dialog();
//	private TextField idField = new TextField("ID");
	private TextField zipCode = new TextField("Zip Code");
	private TextField cityName = new TextField("City Name");
	private StatesComboBox stateCombo = new StatesComboBox();
//	private TextField stateName = new TextField("State Name");
//	private TextField stateAbbrev = new TextField("State Abbrev.");
//	HorizontalLayout buttonLayout = new HorizontalLayout();
//	Button cancelBtn = new Button("Cancel");
//	Button saveBtn = new Button("Save");

// *********Edit fields and controls from original example ***********
//	TextField firstNameField = new TextField("First name", "John", "");
//	TextField lastNameField = new TextField("Last name", "Smith", "");
//	EmailField emailField = new EmailField("Email address");
//	emailField.setValue("john.smith@example.com");
//	FormLayout formLayout = new FormLayout(firstNameField, lastNameField,
//	        emailField);
//	formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
//	formLayout.setColspan(emailField, 2);
//
//	Button delete = new Button("Delete");
//	delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
//	delete.getStyle().set("margin-inline-end", "auto");
//
//	Button cancel = new Button("Cancel");
//
//	Button createAccount = new Button("Create account");
//	createAccount.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//
//	HorizontalLayout buttonLayout = new HorizontalLayout(delete, cancel,
//	        createAccount);
//	buttonLayout.getStyle().set("flex-wrap", "wrap");
//	buttonLayout.setJustifyContentMode(JustifyContentMode.END);
// *************** End of original edit fields *****************

	public CityInfoEditDialog(CityInfoService service, CityInfoGridPager gridPager) {
		super(service, gridPager);

		cityInfoSvc = service;
		idField.setEnabled(false);
		fieldsLayout[0].add(idField, zipCode, cityName);
		fieldsLayout[1].add(stateCombo);

		buttonLayout.add(cancelBtn, saveBtn);
		buttonLayout.getStyle().set("flex-wrap", "wrap");
		buttonLayout.setJustifyContentMode(JustifyContentMode.END);

		rowLayout.add(fieldsLayout[0]);
		rowLayout.add(fieldsLayout[1]);
		rowLayout.add(messageLabel);
		rowLayout.add(buttonLayout);
		editDialog.add(rowLayout);

		cancelBtn.addClickListener(e -> {
			editDialog.close();
		});
		saveBtn.addClickListener(e -> {
			CityInfo cityInfo = new CityInfo();
			cityInfo.setZipCode(zipCode.getValue());
			cityInfo.setCityName(cityName.getValue());
			if (stateCombo.getSelectedState() != null) {
				cityInfo.setStateName(stateCombo.getSelectedState().getStateName());
				cityInfo.setStateAbbrev(stateCombo.getSelectedState().getStateAbbrev());
			} else {
				cityInfo.setStateName("");
				cityInfo.setStateAbbrev("");
			}

			String idFieldValue = idField.getValue();
			if (idFieldValue.trim().isEmpty()) {
				String newId = Long.toString(cityInfoSvc.getMaxId() + 1);
				cityInfo.setId(Long.parseLong(newId));
			} else {
				cityInfo.setId(Long.parseLong(idField.getValue()));
			}

			// Prevent Save from succeeding if any fields are blank or null.
			if (cityInfo.isFullyPopulated()) {
				cityInfoSvc.save(cityInfo);
				messageLabel.setText("");
				clearInputFields();
				Utils.refreshCityInfoRows(gridPager, cityInfoSvc);
				editDialog.close();
			}
			else {
				messageLabel.setText("Please populate all fields before attempting to save.");
			}
		});
	}

	public void openDialog() {
		editDialog.open();
	}

	public void populateInputFields(CityInfo selCityInfo) {
		idField.setValue(Long.toString(selCityInfo.getId()));
		zipCode.setValue(selCityInfo.getZipCode());
		cityName.setValue(selCityInfo.getCityName());
		stateCombo.setSelectedState(selCityInfo.getStateName());
	}

	public void clearInputFields() {
		idField.clear();
		zipCode.clear();
		cityName.clear();
		stateCombo.setSelectedState(null);
	}

}
