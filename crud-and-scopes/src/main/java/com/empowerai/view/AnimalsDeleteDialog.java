package com.empowerai.view;

import javax.inject.Inject;

import com.empowerai.control.AnimalsGridPager;
import com.empowerai.entity.Animal;
import com.empowerai.service.AnimalsService;
import com.empowerai.util.Utils;
import com.vaadin.cdi.annotation.RouteScopeOwner;
import com.vaadin.cdi.annotation.VaadinServiceEnabled;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class AnimalsDeleteDialog extends ListCrudDeleteDialog {
//    @Inject
//	AnimalsService cityInfoSvc;
	
	private String id = null;
	private String animalName = null;

	public AnimalsDeleteDialog(AnimalsService service, AnimalsGridPager gridPager) {
		super();
		headerLabel.setText("Delete Animal Item?");
		confirmBtn.addClickListener(event -> confirmDelete(service, gridPager));
		
	}
	
	public void populateSelFields(Animal selAnimals) {
		id = Long.toString(selAnimals.getId());
		animalName = selAnimals.getAnimalName();
		messageLabel.setText( "Delete #" + id + ", " + animalName + ".  Are you sure?");
		
	}
	

	private void confirmDelete(AnimalsService service, AnimalsGridPager gridPager) {
		Grid<Animal> grid = gridPager.getGrid();
		service.delete((Animal) grid.getSelectedItems().stream().toArray()[0]);
		Utils.refreshAnimalsRows(gridPager, service);	
		deleteDialog.close();
	}
	
	public void openDialog() {
		deleteDialog.open();
	}


}
