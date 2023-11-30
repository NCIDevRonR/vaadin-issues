package com.empowerai.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.empowerai.control.AnimalsGridPager;
import com.empowerai.entity.Animal;
import com.empowerai.service.AnimalsService;
import com.vaadin.flow.component.grid.Grid;

public abstract class Utils {

	public static void refreshAnimalsRows(AnimalsGridPager gridPager, AnimalsService service) {
		Grid<Animal> grid = gridPager.getGrid();
		int currentPage = gridPager.getPageNumber();
		List<Animal> rows = gridPager.getPageRows(service, currentPage);
		grid.setItems(rows);
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
