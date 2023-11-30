package com.empowerai.control;

import java.util.List;

import com.empowerai.entity.Animal;
import com.empowerai.service.AnimalsService;
import com.empowerai.util.Utils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;

public class AnimalsGridPager extends ListCrudGridPager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid<Animal> grid = new Grid<>(Animal.class);

	public int getPageCount() {
		return pageCount;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public AnimalsGridPager(AnimalsService service) {
		super(service);
		comboBox.setWidth("8%");
		comboBox.setItems(rowsPerPage);
		comboBox.setRequired(true);
		add(comboBox);

		pageCount = service.getPageCount(itemsPerPage);
		grid.setPageSize(itemsPerPage);
		grid.removeAllColumns();
		grid.addColumn(Animal::getId).setHeader("ID").setSortable(false).setWidth("5%");
		grid.addColumn(Animal::getAnimalName).setHeader("Animal Name").setSortable(false).setWidth("25%");

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
			List<Animal> prevPageItems = service.getPageItems(--pageNumber, pageCount, itemsPerPage);
			grid.setItems(prevPageItems);
			placeNavButtons(service);
		});
		nextButton.addClickListener(e -> {
			if (pageNumber >= pageCount) {
				return;
			}
			List<Animal> nextPageItems = service.getPageItems(++pageNumber, pageCount, itemsPerPage);
			grid.setItems(nextPageItems);
			placeNavButtons(service);
		});
		lastButton.addClickListener(e -> {
			if (pageNumber >= pageCount) {
				return;
			}
			pageNumber = pageCount;
			List<Animal> lastPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
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
		add(colNamesHLayout, sortHLayout, grid, navHLayout);
		placeNavButtons(service);
	}

	private void repopulateGrid(AnimalsService service) {
		pageCount = service.getPageCount(itemsPerPage);
		updateRows(service);
	}
	
	private void updateRows(AnimalsService service) {
		pageNumber = 1;
		List<Animal> firstPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
		grid.setItems(firstPageItems);
		placeNavButtons(service);
	}

	private void placeNavButtons(AnimalsService service) {
		navHLayout.removeAll();
		navHLayout.add(firstButton, previousButton);
		int eachBtnNumber = 0;
		List<Integer> numBtnValues = getPageNumbers(pageNumber);
		for (Button eachNumberBtn : numberButtons) {
			eachNumberBtn = new Button(Integer.toString(numBtnValues.get(eachBtnNumber++)), e -> {
				pageNumber = Integer.parseInt(e.getSource().getText());
				List<Animal> numberPageItems = service.getPageItems(pageNumber, pageCount, itemsPerPage);
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

	public Grid<Animal> getGrid() {
		return grid;
	}

	public List<Animal> getPageRows(AnimalsService service, int pageNumber) {
		this.pageNumber = pageNumber;
		return service.getPageItems(pageNumber, pageCount, itemsPerPage);
	}


}
