package com.empowerai.view;

import javax.inject.Inject;

import com.empowerai.control.CityInfoGridPager;
import com.empowerai.entity.CityInfo;
import com.empowerai.service.CityInfoService;
import com.empowerai.util.Utils;
import com.vaadin.cdi.annotation.RouteScopeOwner;
import com.vaadin.cdi.annotation.VaadinServiceEnabled;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CityInfoDeleteDialog extends ListCrudDeleteDialog {
    @Inject
	CityInfoService cityInfoSvc;
	
	private String id = null;
	private String zipCode = null;
	private String cityName = null;
	private String stateName = null;

	public CityInfoDeleteDialog(CityInfoService service, CityInfoGridPager gridPager) {
		super();
		headerLabel.setText("Delete CityInfo Item?");
		confirmBtn.addClickListener(event -> confirmDelete(service, gridPager));
		
	}
	
	public void populateSelFields(CityInfo selCityInfo) {
		id = Long.toString(selCityInfo.getId());
		zipCode = selCityInfo.getZipCode();
		cityName = selCityInfo.getCityName();
		stateName = selCityInfo.getStateName();
		messageLabel.setText( "Delete #" + id + ", " + zipCode + ", " + cityName + ", " + stateName + ".  Are you sure?");
		
	}
	

	private void confirmDelete(CityInfoService service, CityInfoGridPager gridPager) {
		Grid<CityInfo> grid = gridPager.getGrid();
		service.delete((CityInfo) grid.getSelectedItems().stream().toArray()[0]);
		Utils.refreshCityInfoRows(gridPager, service);	
		deleteDialog.close();
	}
	
	public void openDialog() {
		deleteDialog.open();
	}


}
