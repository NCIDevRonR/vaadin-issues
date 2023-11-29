package com.empowerai.view;

import javax.inject.Inject;

import com.empowerai.control.CollegesGridPager;
import com.empowerai.entity.College;
import com.empowerai.service.CollegesService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;

@Route("colleges")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class CollegesView extends ListCrudView {
	private static final long serialVersionUID = 1L;
	private CollegesEditDialog editDialog = null;
	private CollegesDeleteDialog deleteDialog = null;
//	private Button createBtn = new Button("New");
//	private Button editBtn = new Button("Edit");
//	private Button deleteBtn = new Button("Delete");
//
//	private HorizontalLayout btnLayout = new HorizontalLayout();
//
//	private String MAX_WIDTH = "400px";
//	private String BUTTON_WIDTH = "123px";
	private CollegesGridPager gridPager = null;
	
	
    @Inject
	CollegesService service;

	/**
	 * Construct a new Vaadin view.
	 * <p>
	 * Build the initial UI state for the user accessing the application.
	 *
	 * @param service The message service. Automatically injected Spring managed
	 *                bean.
	 */
	public CollegesView() {
		super();
		header2.setText("Colleges");
		service = new CollegesService();
		service.getItems();
		gridPager = new CollegesGridPager(service);
		Grid<College> grid = gridPager.getGrid();

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
			editDialog = new CollegesEditDialog(service, gridPager);
			
			editDialog.clearInputFields();
			editDialog.openDialog();
		});

		editBtn.addClickListener(click -> {
			//Populate dialog fields from selected row. 
			College selCollege = (College)grid.getSelectedItems().stream().toArray()[0];
			editDialog = new CollegesEditDialog(service, gridPager);
			editDialog.populateInputFields(selCollege);
			editDialog.openDialog();
		});

		deleteBtn.addClickListener(click -> {
			College selCollege = (College)grid.getSelectedItems().stream().toArray()[0];
			deleteDialog = new CollegesDeleteDialog(service, gridPager);
			deleteDialog.populateSelFields(selCollege);
			deleteDialog.openDialog();
//			service.delete((Colleges) grid.getSelectedItems().stream().toArray()[0]);
//			Utils.refreshCollegesRows(gridPager, service);
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
