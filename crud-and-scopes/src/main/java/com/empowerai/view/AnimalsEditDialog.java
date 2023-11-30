package com.empowerai.view;

import java.util.List;

import javax.inject.Inject;

import com.empowerai.control.AnimalsGridPager;
import com.empowerai.entity.Animal;
import com.empowerai.service.AnimalsService;
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

public class AnimalsEditDialog extends ListCrudEditDialog {

	private String MAX_WIDTH = "400px";
	private String BUTTON_WIDTH = "123px";
	private TextField animalName = new TextField("Animal Name");

	public AnimalsEditDialog(AnimalsService service, AnimalsGridPager gridPager) {
		super(service, gridPager);

		AnimalsService animalsSvc = service;
		idField.setEnabled(false);
		fieldsLayout[0].add(idField, animalName);

		buttonLayout.add(cancelBtn, saveBtn);
		buttonLayout.getStyle().set("flex-wrap", "wrap");
		buttonLayout.setJustifyContentMode(JustifyContentMode.END);

		rowLayout.add(fieldsLayout[0]);
		rowLayout.add(messageLabel);
		rowLayout.add(buttonLayout);
		editDialog.add(rowLayout);

		cancelBtn.addClickListener(e -> {
			editDialog.close();
		});
		saveBtn.addClickListener(e -> {
			Animal animal = new Animal();
			animal.setAnimalName(animalName.getValue());

			String idFieldValue = idField.getValue();
			if (idFieldValue.trim().isEmpty()) {
				String newId = Long.toString(animalsSvc.getMaxId() + 1);
				animal.setId(Long.parseLong(newId));
			} else {
				animal.setId(Long.parseLong(idField.getValue()));
			}

			// Prevent Save from succeeding if any fields are blank or null.
			if (animal.isFullyPopulated()) {
				animalsSvc.save(animal);
				messageLabel.setText("");
				clearInputFields();
				Utils.refreshAnimalsRows(gridPager, animalsSvc);
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

	public void populateInputFields(Animal selAnimal) {
		idField.setValue(Long.toString(selAnimal.getId()));
		animalName.setValue(selAnimal.getAnimalName());
	}

	public void clearInputFields() {
		idField.clear();
		animalName.clear();
	}

}
