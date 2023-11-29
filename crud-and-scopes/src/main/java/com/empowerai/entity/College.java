package com.empowerai.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class College extends ListCrudEntity {
//    @Id
//    @GeneratedValue
//    private Integer id;
	private String collegeName;
	private String typeOfSchool;
	private String street;
	private String city;
	private String state;
	private String zipCode;
	private String mainPhone;
	private String adminName;
	private String adminPhone;
	private String adminEmail;

	public College() {
		super();
		this.collegeName = "";
		this.typeOfSchool = "";
		this.street = "";
		this.city = "";
		this.state = "";
		this.zipCode = "";
		this.mainPhone = "";
		this.adminName = "";
		this.adminPhone = "";
		this.adminEmail = "";
	}

	public College(Long id, String collegeName, String typeOfSchool, String street, String city, String state,
			String zipCode, String mainPhone, String adminName, String adminPhone, String adminEmail) {
		this.setId(id);
		this.collegeName = collegeName;
		this.typeOfSchool = typeOfSchool;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.mainPhone = mainPhone;
		this.adminName = adminName;
		this.adminPhone = adminPhone;
		this.adminEmail = adminEmail;
	}

	public boolean isFullyPopulated() {
		boolean result = true;

		if ((this.zipCode.trim().isEmpty()) || (this.collegeName.trim().isEmpty()) || (this.typeOfSchool.trim().isEmpty())
				|| (this.city.trim().isEmpty()) || (this.state.trim().isEmpty())) {
			result = false;
		}

		return result;
	}

	public boolean isEmpty() {
		boolean result = false;

		if ((this.zipCode.trim().isEmpty()) && (this.collegeName.trim().isEmpty()) && (this.typeOfSchool.trim().isEmpty())
				&& (this.city.trim().isEmpty()) && (this.state.trim().isEmpty())) {
			result = true;
		}

		return result;
	}

//    public Integer getId() {
//		return id;
//	}
//
//    public void setId(Integer id) {
//		this.id = id;
//	}
	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getTypeOfSchool() {
		return typeOfSchool;
	}

	public void setTypeOfSchool(String typeOfSchool) {
		this.typeOfSchool = typeOfSchool;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMainPhone() {
		return mainPhone;
	}

	public void setMainPhone(String mainPhone) {
		this.mainPhone = mainPhone;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPhone() {
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

}
