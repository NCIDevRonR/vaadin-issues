/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.empowerai.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CityInfo extends ListCrudEntity {
//    @Id
//    @GeneratedValue
//    private Integer id;
    private String zipCode;
    private String cityName;
    private String stateName;
    private String stateAbbrev;
    private String county;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public CityInfo() {
    	super();
		this.zipCode = "";
		this.cityName = "";
		this.stateName = "";
		this.stateAbbrev = "";
		this.county = "";
    }
    
    
    public CityInfo(Long id, String zipCode, String cityName, String stateName, String stateAbbrev, String county) {
    	super();
        this.setId(id);
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.stateName = stateName;
        this.stateAbbrev = stateAbbrev;
        this.county = county;
    }
    
    public boolean isFullyPopulated() {
    	boolean result = true;
    	
    	if ((this.zipCode.trim().isEmpty()) || (this.cityName.trim().isEmpty()) || (this.stateName.trim().isEmpty()) || (this.stateAbbrev.trim().isEmpty())) {
    		result = false;
    	}
    	
    	return result;
    }
    
	public boolean isEmpty() {
		boolean result = false;

		if ((this.zipCode.trim().isEmpty()) && (this.cityName.trim().isEmpty()) && (this.stateName.trim().isEmpty())
				&& (this.stateAbbrev.trim().isEmpty())) {
			result = true;
		}

		return result;
	}

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
    
    
    
}
