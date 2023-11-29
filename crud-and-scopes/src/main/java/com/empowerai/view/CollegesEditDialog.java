package com.empowerai.view;

import javax.inject.Inject;

import com.empowerai.control.CollegesGridPager;
import com.empowerai.control.StatesComboBox;
import com.empowerai.entity.College;
import com.empowerai.service.CollegesService;
import com.empowerai.util.Utils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.TextField;

public class CollegesEditDialog extends ListCrudEditDialog {
    @Inject
	CollegesService collegesSvc;
//	
//	CollegesGridPager gridPager;	

//	Colleges selColleges;
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
    private TextField collegeName = new TextField("Name");
    private TextField typeOfSchool = new TextField("Type");
    private TextField street = new TextField("Street");
	private TextField city = new TextField("City");
    private TextField state = new TextField("State");
	private TextField zipCode = new TextField("Zip Code");
	private StatesComboBox stateCombo = new StatesComboBox();

	
	
	
	
	//	private TextField stateName = new TextField("State Name");
//	private TextField stateAbbrev = new TextField("State Abbrev.");
//	private Label messageLabel = new Label("");
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

	public CollegesEditDialog(CollegesService service, CollegesGridPager gridPager) {
		super(service, gridPager);

		collegesSvc = service;
		idField.setEnabled(false);
		fieldsLayout[0].add(idField, collegeName, typeOfSchool);
		fieldsLayout[1].add(city, stateCombo, zipCode);

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
			College college = new College();
			college.setCollegeName(collegeName.getValue());
			college.setTypeOfSchool(typeOfSchool.getValue());
			college.setStreet(street.getValue());
			college.setCity(city.getValue());
			if (stateCombo.getSelectedState() != null) {
				college.setState(stateCombo.getSelectedState().getStateAbbrev());
			} else {
				college.setState("");
			}
			college.setZipCode(zipCode.getValue());

			String idFieldValue = idField.getValue();
			if (idFieldValue.trim().isEmpty()) {
				String newId = Long.toString(collegesSvc.getMaxId() + 1);
				college.setId(Long.parseLong(newId));
			} else {
				college.setId(Long.parseLong(idField.getValue()));
			}

			// Prevent Save from succeeding if any fields are blank or null.
			if (college.isFullyPopulated()) {
				collegesSvc.save(college);
				messageLabel.setText("");
				clearInputFields();
				Utils.refreshCollegesRows(gridPager, collegesSvc);
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

	public void populateInputFields(College selCollege) {
		idField.setValue(Long.toString(selCollege.getId()));
		collegeName.setValue(selCollege.getCollegeName());
		typeOfSchool.setValue(selCollege.getTypeOfSchool());
		street.setValue(selCollege.getStreet());
		city.setValue(selCollege.getCity());
		stateCombo.setSelectedState(selCollege.getState());
		zipCode.setValue(selCollege.getZipCode());
	}

	public void clearInputFields() {
		idField.clear();
		zipCode.clear();
		city.clear();
		stateCombo.setSelectedState(null);
	}

}
