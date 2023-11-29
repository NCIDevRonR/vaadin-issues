package com.empowerai.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.empowerai.control.CityInfoGridPager;
import com.empowerai.control.CollegesGridPager;
import com.empowerai.entity.CityInfo;
import com.empowerai.entity.College;
import com.empowerai.service.CityInfoService;
import com.empowerai.service.CollegesService;
import com.vaadin.flow.component.grid.Grid;

public abstract class Utils {
	public static void refreshCityInfoRows(CityInfoGridPager gridPager, CityInfoService service) {
		Grid<CityInfo> grid = gridPager.getGrid();
		int currentPage = gridPager.getPageNumber();
		List<CityInfo> rows = gridPager.getPageRows(service, currentPage);
		grid.setItems(rows);
	}
	
	public static void refreshCollegesRows(CollegesGridPager gridPager, CollegesService service) {
		Grid<College> grid = gridPager.getGrid();
		int currentPage = gridPager.getPageNumber();
		List<College> rows = gridPager.getPageRows(service, currentPage);
		grid.setItems(rows);
	}
	
	public enum CITY_INFO_FIELD {
		ZIP_CODE("Zip Code"),
		CITY_NAME("City Name"),
		STATE_NAME("State Name"),
		STATE_ABBREV("State Abbrev"),
		ID("ID");
		
		public String getFieldName() {
			return fieldName;
		}

		final String fieldName;
		
		CITY_INFO_FIELD(String fieldName) {
			this.fieldName = fieldName;
		}
		
	}


	public enum COLLEGE_FIELD {
		COLLEGE_NAME("College Name"),
		TYPE_OF_SCHOOL("Type of School"),
		STREET("Street"),
		STATE("State"),
		ZIP_CODE("Zip Code"),
		MAIN_PHONE("Main Phone"),
		ADMIN_NAME("Admin Name"),
		ADMIN_PHONE("Admin Phone"),
		ADMIN_EMAIL("Admin Email"),
		ID("ID");
		
		final String fieldName;
		
		public String getFieldName() {
			return fieldName;
		}

		COLLEGE_FIELD(String fieldName) {
			this.fieldName = fieldName;
		}
		
	}

	public static List<String> readInStream(InputStream is) throws IOException {
        // list that holds strings of a file
        List<String> listOfStrings
                = new ArrayList<>();

        BufferedReader bf = new BufferedReader(
                new InputStreamReader(is));

        // read entire line as string
        String line = bf.readLine();

        // checking for end of file
        while (line != null) {
            listOfStrings.add(line);
            line = bf.readLine();
        }

        // closing bufferedReader object
        bf.close();

        return listOfStrings;
    }
	
    private static InputStream getFileInputStream(String fileName) throws IOException {
        return new FileInputStream(fileName);
    }

	
}
