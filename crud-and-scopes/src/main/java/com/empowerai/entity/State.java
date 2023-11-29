package com.empowerai.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class State {
    @Id
    @GeneratedValue
    private Integer id;
    private String stateName;
    private String stateAbbrev;
    private String stateCapital;
    private String region;
    
    public State(Integer id, String stateName, String stateAbbrev, String stateCapital, String region) {
    	this.id = id;
    	this.stateName = stateName;
    	this.stateAbbrev = stateAbbrev;
    	this.stateCapital = stateCapital;
    	this.region = region;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateAbbrev() {
		return stateAbbrev;
	}

	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}

	public String getStateCapital() {
		return stateCapital;
	}

	public void setStateCapital(String stateCapital) {
		this.stateCapital = stateCapital;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

    
    
	
}
