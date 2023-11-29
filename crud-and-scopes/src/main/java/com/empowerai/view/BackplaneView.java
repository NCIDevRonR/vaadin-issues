package com.empowerai.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/*
 * Backplane View, with menus, toolbars, and very little else.
 * All other screens will inherit from this one ultimately.
 * 
 */

public class BackplaneView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Header header1 = new Header();
	Header header2 = new Header();
	Div content = new Div();
	MenuBar menuBar;
	MenuItem view;
	MenuItem cityInfo;
	MenuItem colleges;
	
	Text selected = new Text("");
	ComponentEventListener<ClickEvent<MenuItem>> listener;
	Div message = new Div(new Text("Clicked item: "), selected);




	public BackplaneView() {
		menuBar = new MenuBar();
		selected = new Text("");
		ComponentEventListener<ClickEvent<MenuItem>> listener = e -> selected
		        .setText(e.getSource().getText());
		message = new Div(new Text("Clicked item: "), selected);
		
		view = menuBar.addItem("View", listener);
		SubMenu viewMenu = view.getSubMenu();
		cityInfo = viewMenu.addItem("City Info");
		colleges = viewMenu.addItem("Colleges");
		cityInfo.addClickListener(listener = e -> {
			cityInfo.getUI().ifPresent(ui -> {
				ui.navigate("cityInfo");
			});
		});

		colleges.addClickListener(listener = e -> {
			cityInfo.getUI().ifPresent(ui -> {
				ui.navigate("colleges");
			});
		});

		content.setHeight("100px");
		content.addClassName("scrollable-dialog-content");
		header1.setClassName(null);
		header1.setText("MyApp");
		header2.setText("MyPage");
		add(header1, header2, content);
		header1.add(menuBar);
	}
	
	private void gotoPage(String pageURL) {
		
	}
}
