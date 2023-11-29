package com.empowerai.view;

import javax.inject.Inject;
import com.empowerai.service.ListCrudService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public abstract class ListCrudView extends BackplaneView {
//	protected ListCrudEditDialog editDialog = null;
//	protected CityInfoDeleteDialog deleteDialog = null;
	protected Button createBtn = new Button("New");
	protected Button editBtn = new Button("Edit");
	protected Button deleteBtn = new Button("Delete");

	protected HorizontalLayout btnLayout = new HorizontalLayout();

	protected String MAX_WIDTH = "400px";
	protected String BUTTON_WIDTH = "123px";
//	protected ListCrudGridPager gridPager = null;
	
	
	ListCrudService service;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ListCrudView() {
		
	}

}
