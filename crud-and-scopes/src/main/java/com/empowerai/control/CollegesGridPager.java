package com.empowerai.control;

import java.util.List;

import com.empowerai.entity.College;
import com.empowerai.service.CollegesService;
import com.empowerai.util.Utils;
import com.empowerai.util.Utils.COLLEGE_FIELD;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

public class CollegesGridPager extends ListCrudGridPager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid<College> grid = new Grid<>(College.class);
	College collegeFilter = new College();

	public int getPageCount() {
		return pageCount;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public CollegesGridPager(CollegesService service) {
		super(service);
		collegeFilter.setCollegeName("");
		collegeFilter.setTypeOfSchool("");
		collegeFilter.setState("");
		collegeFilter.setZipCode("");
		
		comboBox.setWidth("8%");
		comboBox.setItems(rowsPerPage);
		comboBox.setRequired(true);
		add(comboBox);

		placeColSrtCtrls(service);
		pageCount = service.getPageCount(itemsPerPage);
		grid.setPageSize(itemsPerPage);
		grid.removeAllColumns();
		grid.addColumn(College::getId).setHeader("ID").setSortable(false).setWidth("8%");
		grid.addColumn(College::getCollegeName).setHeader("College Name").setSortable(false).setWidth("25%");
		grid.addColumn(College::getTypeOfSchool).setHeader("Type of School").setSortable(false).setWidth("10%");
		grid.addColumn(College::getCity).setHeader("City").setSortable(false).setWidth("10%");
		grid.addColumn(College::getState).setHeader("State").setSortable(false).setWidth("10%");
		grid.addColumn(College::getZipCode).setHeader("Zip Code").setSortable(false).setWidth("10%");

		grid.setItems(getPageRows(service, 1));

		firstButton.addClickListener(e -> {
			if (pageNumber <= 1) {
				return;
			}
			updateRows(service);
		});
		previousButton.addClickListener(e -> {
			if (pageNumber <= 1) {
				return;
			}
			List<College> prevPageItems = service.getPageItems(--pageNumber, pageCount, itemsPerPage);
			grid.setItems(prevPageItems);
			placeNavButtons(service);
		});
		nextButton.addClickListener(e -> {
			if (pageNumber >= pageCount) {
				return;
			}
			List<College> nextPageItems = service.getPageItems(++pageNumber, pageCount, itemsPerPage);
			grid.setItems(nextPageItems);
			placeNavButtons(service);
		});
		lastButton.addClickListener(e -> {
			if (pageNumber >= pageCount) {
				return;
			}
			pageNumber = pageCount;
			List<College> lastPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
			grid.setItems(lastPageItems);
			placeNavButtons(service);
		});
		comboBox.addValueChangeListener(e -> {
			String newItemsPerPage = e.getSource().getValue();
			if (!newItemsPerPage.isEmpty()) {
				int intNewItemsPerPage = Integer.parseInt(newItemsPerPage);
				itemsPerPage = intNewItemsPerPage;
				repopulateGrid(service);
			}

		});
		add(sortHLayout, grid, navHLayout);
		placeNavButtons(service);
	}

	private void placeColSrtCtrls(CollegesService service) {
		sortHLayout.removeAll();
		if (comboBox.getValue() == null) {
			comboBox.setValue("10");
		}
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
				colFilterField[whichCol].setLabel(COLLEGE_FIELD.COLLEGE_NAME.getFieldName());
				colFilterField[whichCol].addValueChangeListener(e -> {
					collegeFilter.setCollegeName(e.getValue());
					service.filterRows(collegeFilter);
					repopulateGrid(service);
					});
				break;
			case 1:
				colFilterField[whichCol].setLabel(COLLEGE_FIELD.TYPE_OF_SCHOOL.getFieldName());
				colFilterField[whichCol].addValueChangeListener(e -> {
					collegeFilter.setTypeOfSchool(e.getValue());
					service.filterRows(collegeFilter);
					repopulateGrid(service);
					});
				break;
			case 2:
				colFilterField[whichCol].setLabel(COLLEGE_FIELD.STATE.getFieldName());
				colFilterField[whichCol].addValueChangeListener(e -> {
					collegeFilter.setState(e.getValue());
					service.filterRows(collegeFilter);
					repopulateGrid(service);
					});
				break;
			case 3:
				colFilterField[whichCol].setLabel(COLLEGE_FIELD.ZIP_CODE.getFieldName());
				colFilterField[whichCol].addValueChangeListener(e -> {
					collegeFilter.setZipCode(e.getValue());
					service.filterRows(collegeFilter);
					repopulateGrid(service);
					});
				break;
			}

		}

	}

	
	private void repopulateGrid(CollegesService service) {
		pageCount = service.getPageCount(itemsPerPage);
		updateRows(service);
	}
	
	
	
	
	private void updateSortIcon(Button sortBtn, CollegesService service) {
		for (int whichCol = 0; whichCol < 4; whichCol++) {
			Utils.COLLEGE_FIELD eachInfoField = Utils.COLLEGE_FIELD.ID;
			switch (whichCol) {
			case 0:
				eachInfoField = Utils.COLLEGE_FIELD.COLLEGE_NAME;
				break;
			case 1:
				eachInfoField = Utils.COLLEGE_FIELD.TYPE_OF_SCHOOL;
				break;
			case 2:
				eachInfoField = Utils.COLLEGE_FIELD.STATE;
				break;
			case 3:
				eachInfoField = Utils.COLLEGE_FIELD.ZIP_CODE;
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

	private void updateRows(CollegesService service) {
		pageNumber = 1;
		List<College> firstPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
		grid.setItems(firstPageItems);
		placeNavButtons(service);
	}
	
	private void placeNavButtons(CollegesService service) {
		navHLayout.removeAll();
		navHLayout.add(firstButton, previousButton);
		int eachBtnNumber = 0;
		List<Integer> numBtnValues = getPageNumbers(pageNumber);
		for (Button eachNumberBtn : numberButtons) {
			eachNumberBtn = new Button(Integer.toString(numBtnValues.get(eachBtnNumber++)), e -> {
				pageNumber = Integer.parseInt(e.getSource().getText());
				List<College> numberPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
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

	public Grid<College> getGrid() {
		return grid;
	}

	public List<College> getPageRows(CollegesService service, int pageNumber) {
		this.pageNumber = pageNumber;
		return service.getPageItems(pageNumber, pageCount, itemsPerPage);
	}

}

