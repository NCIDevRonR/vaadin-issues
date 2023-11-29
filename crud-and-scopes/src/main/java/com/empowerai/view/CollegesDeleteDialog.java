package com.empowerai.view;

import javax.inject.Inject;

import com.empowerai.control.CollegesGridPager;
import com.empowerai.entity.College;
import com.empowerai.service.CollegesService;
import com.empowerai.util.Utils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CollegesDeleteDialog extends ListCrudDeleteDialog {
    @Inject
	CollegesService cityInfoSvc;
	
	private String id = null;
    private String collegeName = null;
    private String typeOfSchool = null;
    private String street = null;
    private String city = null;
    private String state = null;
    private String zipCode = null;

	public CollegesDeleteDialog(CollegesService service, CollegesGridPager gridPager) {
		super();
		headerLabel.setText("Delete Colleges Item?");
		confirmBtn.addClickListener(event -> confirmDelete(service, gridPager));
		
	}
	
	public void populateSelFields(College selCollege) {
		id = Long.toString(selCollege.getId());
		collegeName = selCollege.getCollegeName();
		typeOfSchool = selCollege.getTypeOfSchool();
		street = selCollege.getStreet();
		city = selCollege.getCity();
		state = selCollege.getState();
		zipCode = selCollege.getZipCode();
		
		messageLabel.setText( "Delete #" + id + ", " + collegeName + ", " + city + ", " + state + ", " + zipCode + ".  Are you sure?");
		
	}
	

	private void confirmDelete(CollegesService service, CollegesGridPager gridPager) {
		Grid<College> grid = gridPager.getGrid();
		service.delete((College) grid.getSelectedItems().stream().toArray()[0]);
		Utils.refreshCollegesRows(gridPager, service);		
		deleteDialog.close();
	}
	

	public void openDialog() {
		deleteDialog.open();
	}


}
