package com.empowerai.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Animal extends ListCrudEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
    private String animalName;

    public Animal() {
    	super();
		this.animalName = "";
    }
    
    public Animal(Long id, String nameOfAnimal) {
        this.setId(id);
        this.animalName = nameOfAnimal;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnimalName() {
		return animalName;
	}

	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}

    public boolean isFullyPopulated() {
    	boolean result = true;
    	
    	if (this.animalName.trim().isEmpty()) {
    		result = false;
    	}
    	
    	return result;
    }
    
	public boolean isEmpty() {
		boolean result = false;

		if (this.animalName.trim().isEmpty()) {
			result = true;
		}

		return result;
	}

    
    
}
