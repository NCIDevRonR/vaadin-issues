package com.empowerai.control;

import java.util.ArrayList;
import java.util.List;

import com.empowerai.entity.CityInfo;
import com.empowerai.service.CityInfoService;
import com.empowerai.util.Utils;
import com.empowerai.util.Utils.CITY_INFO_FIELD;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CityInfoGridPager extends ListCrudGridPager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid<CityInfo> grid = new Grid<>(CityInfo.class);
	CityInfo cityInfoFilter = new CityInfo();

	public int getPageCount() {
		return pageCount;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public CityInfoGridPager(CityInfoService service) {
		super(service);
		comboBox.setWidth("8%");
		comboBox.setItems(rowsPerPage);
		comboBox.setRequired(true);
		add(comboBox);

		placeColSrtCtrls(service);
		pageCount = service.getPageCount(itemsPerPage);
		grid.setPageSize(itemsPerPage);
		grid.removeAllColumns();
		grid.addColumn(CityInfo::getId).setHeader("ID").setSortable(false).setWidth("5%");
		grid.addColumn(CityInfo::getZipCode).setHeader("Zip Code").setSortable(false).setWidth("10%");
		grid.addColumn(CityInfo::getCityName).setHeader("City Name").setSortable(false).setWidth("25%");
		grid.addColumn(CityInfo::getStateName).setHeader("State Name").setSortable(false).setWidth("25%");
		grid.addColumn(CityInfo::getStateAbbrev).setHeader("State Abbrev.").setSortable(false).setWidth("10%");

		grid.setItems(getPageRows(service, 1));

		firstButton.addClickListener(e -> {
			if (pageNumber <= 1) {
				return;
			}
			updateRows(service);
//			pageNumber = 1;
//			List<CityInfo> firstPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
//			grid.setItems(firstPageItems);
//			placeNavButtons(service);
		});
		previousButton.addClickListener(e -> {
			if (pageNumber <= 1) {
				return;
			}
			List<CityInfo> prevPageItems = service.getPageItems(--pageNumber, pageCount, itemsPerPage);
			grid.setItems(prevPageItems);
			placeNavButtons(service);
		});
		nextButton.addClickListener(e -> {
			if (pageNumber >= pageCount) {
				return;
			}
			List<CityInfo> nextPageItems = service.getPageItems(++pageNumber, pageCount, itemsPerPage);
			grid.setItems(nextPageItems);
			placeNavButtons(service);
		});
		lastButton.addClickListener(e -> {
			if (pageNumber >= pageCount) {
				return;
			}
			pageNumber = pageCount;
			List<CityInfo> lastPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
			grid.setItems(lastPageItems);
			placeNavButtons(service);
		});
		comboBox.addValueChangeListener(e -> {
			String newItemsPerPage = e.getSource().getValue();
			if (!newItemsPerPage.isEmpty()) {
				int intNewItemsPerPage = Integer.parseInt(newItemsPerPage);
				itemsPerPage = intNewItemsPerPage;
				repopulateGrid(service);
//				pageCount = service.getPageCount(itemsPerPage);
//				pageNumber = 1;
//				List<CityInfo> firstPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
//				grid.setItems(firstPageItems);
//				placeNavButtons(service);
			}

		});
		add(colNamesHLayout, sortHLayout, grid, navHLayout);
		placeNavButtons(service);
	}

	private void placeColSrtCtrls(CityInfoService service) {
		colNamesHLayout.removeAll();
		sortHLayout.removeAll();
		if (comboBox.getValue() == null) {
			comboBox.setValue("10");
		}
		colNamesHLayout.add(new Label("Rows per Page"));
		sortHLayout.add(comboBox);
		for (int whichCol = 0; whichCol < 4; whichCol++) {
			colSortButton[whichCol].setIcon(sortIcon[whichCol]);
			colSortButton[whichCol].setWidth("25%");
			colSortButton[whichCol].addClickListener(e -> {
				updateSortIcon(e.getSource(), service);
			});
			sortHLayout.add(colSortLayout[whichCol]);
			colSortLayout[whichCol].add(colSortButton[whichCol]);
			colSortLayout[whichCol].add(colFilterField[whichCol]);
			switch (whichCol) {
			case 0:
				colNamesHLayout.add(new Label(CITY_INFO_FIELD.ZIP_CODE.getFieldName()));
//				colFilterField[whichCol].setLabel(CITY_INFO_FIELD.ZIP_CODE.getFieldName());
				colFilterField[whichCol].addValueChangeListener(e -> {
					cityInfoFilter.setZipCode(e.getValue());
					service.filterRows(cityInfoFilter);
					repopulateGrid(service);
					});
				break;
			case 1:
				colNamesHLayout.add(new Label(CITY_INFO_FIELD.CITY_NAME.getFieldName()));
//				colFilterField[whichCol].setLabel(CITY_INFO_FIELD.CITY_NAME.getFieldName());
				colFilterField[whichCol].addValueChangeListener(e -> {
					cityInfoFilter.setCityName(e.getValue());
					service.filterRows(cityInfoFilter);
					repopulateGrid(service);
					});
				break;
			case 2:
				colNamesHLayout.add(new Label(CITY_INFO_FIELD.STATE_NAME.getFieldName()));
//				colFilterField[whichCol].setLabel(CITY_INFO_FIELD.STATE_NAME.getFieldName());
				colFilterField[whichCol].addValueChangeListener(e -> {
					cityInfoFilter.setStateName(e.getValue());
					service.filterRows(cityInfoFilter);
					repopulateGrid(service);
					});
				break;
			case 3:
				colNamesHLayout.add(new Label(CITY_INFO_FIELD.STATE_ABBREV.getFieldName()));
//				colFilterField[whichCol].setLabel(CITY_INFO_FIELD.STATE_ABBREV.getFieldName());
				colFilterField[whichCol].addValueChangeListener(e -> {
					cityInfoFilter.setStateAbbrev(e.getValue());
					service.filterRows(cityInfoFilter);
					repopulateGrid(service);
					});
				break;
			}
		}

	}

	private void repopulateGrid(CityInfoService service) {
		pageCount = service.getPageCount(itemsPerPage);
		updateRows(service);
	}
	
	private void updateSortIcon(Button sortBtn, CityInfoService service) {
		for (int whichCol = 0; whichCol < 4; whichCol++) {
			Utils.CITY_INFO_FIELD eachInfoField = Utils.CITY_INFO_FIELD.ID;
			switch (whichCol) {
			case 0:
				eachInfoField = Utils.CITY_INFO_FIELD.ZIP_CODE;
				break;
			case 1:
				eachInfoField = Utils.CITY_INFO_FIELD.CITY_NAME;
				break;
			case 2:
				eachInfoField = Utils.CITY_INFO_FIELD.STATE_NAME;
				break;
			case 3:
				eachInfoField = Utils.CITY_INFO_FIELD.STATE_ABBREV;
				break;
			}

			if (sortBtn == colSortButton[whichCol]) {
				if (sortBtn.getIcon() == sortIcon[whichCol]) {
					sortBtn.setIcon(sortUpIcon[whichCol]);
					service.sortRowsBy(eachInfoField.getFieldName(), true);
				} else if (sortBtn.getIcon() == sortUpIcon[whichCol]) {
					sortBtn.setIcon(sortDownIcon[whichCol]);
					service.sortRowsBy(eachInfoField.getFieldName(), false);
				} else if (sortBtn.getIcon() == sortDownIcon[whichCol]) {
					sortBtn.setIcon(sortIcon[whichCol]);
					service.sortRowsBy(Utils.CITY_INFO_FIELD.ID.getFieldName(), true);
				}
				updateRows(service);

			} else {
				colSortButton[whichCol].setIcon(sortIcon[whichCol]);
			}
		}
	}

	private void updateRows(CityInfoService service) {
		pageNumber = 1;
		List<CityInfo> firstPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
		grid.setItems(firstPageItems);
		placeNavButtons(service);
	}

	private void placeNavButtons(CityInfoService service) {
		navHLayout.removeAll();
		navHLayout.add(firstButton, previousButton);
		int eachBtnNumber = 0;
		List<Integer> numBtnValues = getPageNumbers(pageNumber);
		for (Button eachNumberBtn : numberButtons) {
			eachNumberBtn = new Button(Integer.toString(numBtnValues.get(eachBtnNumber++)), e -> {
				pageNumber = Integer.parseInt(e.getSource().getText());
				List<CityInfo> numberPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
				grid.setItems(numberPageItems);
				placeNavButtons(service);
			});

			boolean onFirstPage = (pageNumber == 1);
			boolean onLastPage = (pageNumber == pageCount);
			boolean notCurrentPageNbr = (Integer.parseInt(eachNumberBtn.getText()) != pageNumber);
			boolean pageInRange = (Integer.parseInt(eachNumberBtn.getText()) <= pageCount);
			eachNumberBtn.setEnabled(notCurrentPageNbr && pageInRange);
			eachNumberBtn.setVisible(pageInRange);
			firstButton.setEnabled(!onFirstPage);
			previousButton.setEnabled(!onFirstPage);
			nextButton.setEnabled(!onLastPage);
			lastButton.setEnabled(!onLastPage);
			navHLayout.add(eachNumberBtn);
		}
		navHLayout.add(nextButton, lastButton);
	}

	public Grid<CityInfo> getGrid() {
		return grid;
	}

	public List<CityInfo> getPageRows(CityInfoService service, int pageNumber) {
		this.pageNumber = pageNumber;
		return service.getPageItems(pageNumber, pageCount, itemsPerPage);
	}

}
