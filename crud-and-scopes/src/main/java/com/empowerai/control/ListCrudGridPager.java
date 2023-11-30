package com.empowerai.control;

import java.util.ArrayList;
import java.util.List;

import com.empowerai.entity.ListCrudEntity;
import com.empowerai.service.ListCrudService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public abstract class ListCrudGridPager extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	protected int pageCount;
	protected int itemsPerPage = 10;
	protected int pageNumber = 1;
	protected String SORT_UP = "lumo:chevron-up";
	protected String SORT_DOWN = "lumo:chevron-down";
	protected String SORT = "vaadin:sort";
	protected Icon sortIcon[] = new Icon[] { new Icon(SORT), new Icon(SORT), new Icon(SORT), new Icon(SORT) };
	protected Icon sortUpIcon[] = new Icon[] { new Icon(SORT_UP), new Icon(SORT_UP), new Icon(SORT_UP),
			new Icon(SORT_UP) };
	protected Icon sortDownIcon[] = new Icon[] { new Icon(SORT_DOWN), new Icon(SORT_DOWN), new Icon(SORT_DOWN),
			new Icon(SORT_DOWN) };

	protected Grid<? extends ListCrudEntity> grid = new Grid<>();
	protected HorizontalLayout colNamesHLayout = new HorizontalLayout(); // for column labels.
	protected HorizontalLayout sortHLayout = new HorizontalLayout(); // for colSortLayouts.
	protected HorizontalLayout navHLayout = new HorizontalLayout();

	// Each field will have its own horiz layout. I think they can be bordered.
	protected HorizontalLayout colSortLayout[] = new HorizontalLayout[] { new HorizontalLayout(), new HorizontalLayout(),
			new HorizontalLayout(), new HorizontalLayout() }; // for each column's colSortButton and colFilterField.
	protected Button colSortButton[] = new Button[] { new Button(), new Button(), new Button(), new Button() };
	protected final String rowsPerPage[] = new String[] { "10", "20", "30" };
	protected ComboBox<String> comboBox = new ComboBox<>("Grid Rows");
	protected TextField colFilterField[] = new TextField[] { new TextField(), new TextField(), new TextField(), new TextField() };

	protected Button numberButtons[] = new Button[] { new Button("1"), new Button("2"), new Button("3"), new Button("4"),
			new Button("5"), new Button("6"), new Button("7"), new Button("8"), new Button("9"), new Button("10") };
	protected Button firstButton = new Button("|<<");
	protected Button previousButton = new Button("<");
	protected Button nextButton = new Button(">");
	protected Button lastButton = new Button(">>|");

	public int getPageCount() {
		return pageCount;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public ListCrudGridPager(ListCrudService service) {
		comboBox.setWidth("8%");
		comboBox.setItems(rowsPerPage);
		comboBox.setRequired(true);
		add(comboBox);

		pageCount = service.getPageCount(itemsPerPage);
		grid.setPageSize(itemsPerPage);
		grid.removeAllColumns();

	}


	public abstract Grid<? extends ListCrudEntity> getGrid();

//	public abstract <T extends ListCrudService> List<? extends ListCrudEntity> getPageRows(Class<T> service, int pageNumber);

	//Issue:  The last set of pages may end before the tenth page, this method still returns ten values, resulting in the creation of ten buttons.
	protected List<Integer> getPageNumbers(int currentPage) {
		List<Integer> result = new ArrayList<>();

		// Return the set of ten numbers of which the currentPage is a member.
		int tensPlace = (currentPage - 1) / 10;
		int startOfSet = (tensPlace * 10) + 1;
		int endOfSet = startOfSet + 9;

		for (int eachValue = startOfSet; eachValue <= endOfSet; eachValue++) {
			result.add(eachValue);
		}

		return result;
	}

}
