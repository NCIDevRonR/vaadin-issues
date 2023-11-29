package com.empowerai.view;

import javax.inject.Inject;

import com.empowerai.control.CityInfoGridPager;
import com.empowerai.entity.CityInfo;
import com.empowerai.service.CityInfoService;
import com.vaadin.cdi.annotation.VaadinServiceEnabled;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean. Use the @PWA
 * annotation make the application installable on phones, tablets and some
 * desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 */
@Route("cityInfo")
@PWA(name = "Project Base for Vaadin Flow with CDI", shortName = "Project Base", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class CityInfoView extends ListCrudView {
	private static final long serialVersionUID = 1L;
	private CityInfoEditDialog editDialog = null;
	private CityInfoDeleteDialog deleteDialog = null;
//	private Button createBtn = new Button("New");
//	private Button editBtn = new Button("Edit");
//	private Button deleteBtn = new Button("Delete");
//
//	private HorizontalLayout btnLayout = new HorizontalLayout();
//
//	private String MAX_WIDTH = "400px";
//	private String BUTTON_WIDTH = "123px";
	private CityInfoGridPager gridPager = null;
	
    @Inject
	CityInfoService service;

	/**
	 * Construct a new Vaadin view.
	 * <p>
	 * Build the initial UI state for the user accessing the application.
	 *
	 * @param service The message service. Automatically injected Spring managed
	 *                bean.
	 */
//	public CityInfoView(CityInfoService service) {
    @Inject
	public CityInfoView() {
		super();
		header2.setText("City Info");
		service = new CityInfoService();
		service.getItems();
		gridPager = new CityInfoGridPager(service);
		Grid<CityInfo> grid = gridPager.getGrid();

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
			editDialog = new CityInfoEditDialog(service, gridPager);
			
			editDialog.clearInputFields();
			editDialog.openDialog();
		});

		editBtn.addClickListener(click -> {
			//Populate dialog fields from selected row. 
			CityInfo selCityInfo = (CityInfo)grid.getSelectedItems().stream().toArray()[0];
			editDialog = new CityInfoEditDialog(service, gridPager);
			editDialog.populateInputFields(selCityInfo);
			editDialog.openDialog();
		});

		deleteBtn.addClickListener(click -> {
			CityInfo selCityInfo = (CityInfo)grid.getSelectedItems().toArray()[0];
			deleteDialog = new CityInfoDeleteDialog(service, gridPager);
			deleteDialog.populateSelFields(selCityInfo);
			deleteDialog.openDialog();
//			service.delete((CityInfo) grid.getSelectedItems().stream().toArray()[0]);
//			Utils.refreshCityInfoRows(gridPager, service);
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
