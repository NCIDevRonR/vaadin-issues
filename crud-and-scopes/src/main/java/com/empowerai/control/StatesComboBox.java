package com.empowerai.control;

import java.util.List;

import com.empowerai.entity.State;
import com.empowerai.service.StatesService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class StatesComboBox extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComboBox<State> comboBox = new ComboBox<>("State");
	private StatesService statesService = new StatesService();
	private State selectedState = null;

	public StatesComboBox() {
		comboBox.setItems(statesService.getStates());
		comboBox.setItemLabelGenerator(State::getStateName);
		add(comboBox);
		comboBox.addValueChangeListener(e -> {
			selectedState = e.getSource().getValue();
		});
	}

	public State getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(String stateName) {
		List<State> listOfStates = statesService.getStates();
		if (stateName != null) {
			for (State eachState : listOfStates) {
				// A two-byte state name is likely an abbreviation. Find the state value with
				// the matching state abbreviation.
				if (stateName.length() == 2) {
					if (eachState.getStateAbbrev().equals(stateName)) {
						comboBox.setValue(eachState);
						break;
					}
				}
				// If the state name is longer than two bytes, it's likely the full state name.
				// Find the state value with the matching state name.
				else {
					if (eachState.getStateName().equals(stateName)) {
						comboBox.setValue(eachState);
						break;
					}
				}

			}
		} else {
			comboBox.setValue(null);
		}
	}

}
