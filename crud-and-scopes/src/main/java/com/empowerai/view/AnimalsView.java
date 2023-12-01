package com.empowerai.view;

import javax.inject.Inject;

import com.empowerai.view.ListCrudView;
import com.empowerai.control.AnimalsGridPager;
import com.empowerai.entity.Animal;
import com.empowerai.service.AnimalsService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route("animals")
@PreserveOnRefresh
@PWA(name = "Project Base for Vaadin Flow with CDI", shortName = "Project Base", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")public class AnimalsView extends ListCrudView {
	private static final long serialVersionUID = 1L;
	private AnimalsEditDialog editDialog = null;
	private AnimalsDeleteDialog deleteDialog = null;
	private AnimalsGridPager gridPager = null;
	
    @Inject
	AnimalsService service;

	/**
	 * Construct a new Vaadin view.
	 * <p>
	 * Build the initial UI state for the user accessing the application.
	 *
	 * @param service The message service. Automatically injected Spring managed
	 *                bean.
	 */
    @Inject
	public AnimalsView() {
		super();
		header2.setText("Animals");
		service = new AnimalsService();
		service.getItems();
		gridPager = new AnimalsGridPager(service);
		Grid<Animal> grid = gridPager.getGrid();

		content.add(gridPager);
		
		createBtn.setWidth(BUTTON_WIDTH);
		editBtn.setEnabled(false);
		editBtn.setWidth(BUTTON_WIDTH);
		deleteBtn.setEnabled(false);
		deleteBtn.setWidth(BUTTON_WIDTH);

		btnLayout.add(createBtn, editBtn, deleteBtn);
		btnLayout.setMaxWidth(MAX_WIDTH);

		content.add(btnLayout);

		createBtn.addClickListener(click -> {
			grid.select(null);
			editDialog = new AnimalsEditDialog(service, gridPager);
			
			editDialog.clearInputFields();
			editDialog.openDialog();
		});

		editBtn.addClickListener(click -> {
			//Populate dialog fields from selected row. 
			Animal selAnimals = (Animal)grid.getSelectedItems().stream().toArray()[0];
			editDialog = new AnimalsEditDialog(service, gridPager);
			editDialog.populateInputFields(selAnimals);
			editDialog.openDialog();
		});

		deleteBtn.addClickListener(click -> {
			Animal selAnimal = (Animal)grid.getSelectedItems().toArray()[0];
			deleteDialog = new AnimalsDeleteDialog(service, gridPager);
			deleteDialog.populateSelFields(selAnimal);
			deleteDialog.openDialog();
		});

		grid.addSelectionListener(selected -> {
			if (selected.isFromClient() != false) {
				deleteBtn.setEnabled(true);
				editBtn.setEnabled(true);

			} else {
				deleteBtn.setEnabled(false);
				editBtn.setEnabled(false);
			}
		});
	}
}
