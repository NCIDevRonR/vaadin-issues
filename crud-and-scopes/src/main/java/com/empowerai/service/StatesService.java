package com.empowerai.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.empowerai.entity.State;
import com.empowerai.util.Utils;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class StatesService  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<State> statesList = null;

	public List<State> getStates() {
		if (statesList == null) {
			statesList = loadStates();
		}
		else if (statesList.isEmpty()) {
			statesList = loadStates();
		}
		return statesList;
	}
    
    private List<State> loadStates() {
        List<State> listOfStates = new ArrayList<>();

        try {
        	
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/US States.csv");
            List<String> listOfStrings = Utils.readInStream(inputStream);
            
            int eachId = 0;
            for (String eachLine : listOfStrings) {
                eachId++;
                String fields[] = eachLine.split(",");
                String stateName = fields[0];
                String stateAbbrev = fields[1];
                String stateCapital = fields[2];
                String region = fields[3];
                if (!stateName.isEmpty()) {
                    State cityInfo = new State(eachId, stateName, stateAbbrev, stateCapital, region);
                    listOfStates.add(cityInfo);
                }
            }
            return listOfStates;

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //Get city info from a HUGE text file.
        return listOfStates;
    }


}
