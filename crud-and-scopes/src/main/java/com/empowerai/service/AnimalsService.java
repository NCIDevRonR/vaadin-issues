package com.empowerai.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import com.empowerai.entity.Animal;
import com.empowerai.util.Utils;
import com.empowerai.entity.Animal;

@SessionScoped
public class AnimalsService extends ListCrudService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Animal> animalList = null;

	public AnimalsService() {
		super();
		animalList = new ArrayList<>();
	}

	public void save(Animal animal) {
		if (animal.getId() == -1) {
			animal.setId((long) animalList.size() + 1);
			animalList.add(animal);
		} else if (animal.getId() == animalList.size() + 1) {
			animalList.add(animal);
		} else {
			Animal existingAnimal = null;
			int eachPosition = -1;
			int foundPosition = -1;
			for (Animal eachAnimal : animalList) {
				eachPosition++;
				if (eachAnimal.getId().compareTo(animal.getId()) == 0) {
					foundPosition = eachPosition;
					existingAnimal = eachAnimal;
					break;
				}
			}
			if (existingAnimal != null) {
				animalList.remove(existingAnimal);
				animalList.add(foundPosition, animal);
			} else {
				animalList.add(animal);
			}

		}
	}

	public void delete(Animal cityInfo) {
		if (animalList.contains(cityInfo)) {
			animalList.remove(cityInfo);
		}
	}

	public List<Animal> getItems() {
		if (animalList == null) {
			animalList = loadAnimals();
		} else if (animalList.isEmpty()) {
			animalList = loadAnimals();
		}

		return animalList;
	}

	private List<Animal> loadAnimals() {
		List<Animal> listOfAnimals = new ArrayList<>();

		try {

			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/animals.txt");
            List<String> listOfStrings = Utils.readInStream(inputStream);

			long eachId = 0;
			for (String eachLine : listOfStrings) {
				eachId++;
				String nameOfAnimal = eachLine;
				//A bunch of lines in the file contain the word "list".  What's up with that?
				if ((!nameOfAnimal.isEmpty()) && (!nameOfAnimal.matches("list"))) {
					Animal animalToAdd = new Animal(eachId, nameOfAnimal);
					listOfAnimals.add(animalToAdd);
				}
			}
			return listOfAnimals;

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Get animals from a HUGE text file.
		return listOfAnimals;
	}

	@Override
	public List<Animal> getPageItems(int pageNumber, int pageCount, int itemsPerPage) {
		// Skip itemsPerPage * pageNumber items, then add in the next itemsPerPage items
		List<Animal> itemsToReturn = new ArrayList<>();

		int firstItem = (pageNumber - 1) * itemsPerPage;
		int lastItem = firstItem + (itemsPerPage - 1);
		if (lastItem >= animalList.size()) {
			lastItem = animalList.size() - 1;
		}

		int eachItem = -1;
		for (Animal eachAnimal : animalList) {
			++eachItem;
			if ((eachItem >= firstItem) && (eachItem <= lastItem)) {
				itemsToReturn.add(eachAnimal);
			}
		}

		return itemsToReturn;
	}

	@Override
	public int getPageCount(int itemsPerPage) {
		int pageCount = 0;

		if (animalList != null) {
			if (!animalList.isEmpty()) {
				pageCount = animalList.size() / itemsPerPage;
			}
		}
		if (animalList.size() > (pageCount * itemsPerPage)) {
			pageCount++;
		}

		return pageCount;
	}

	@Override
	public int getItemCount() {
		return animalList.size();
	}

	@Override
	public long getMaxId() {
		long maxId = -1;

		for (Animal eachAnimal : animalList) {
			if (eachAnimal.getId() > maxId) {
				maxId = eachAnimal.getId();
			}
		}
		return maxId;

	}

	
}
