package com.empowerai.service;

import java.io.Serializable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.empowerai.entity.College;
import com.empowerai.util.Utils;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class CollegesService extends ListCrudService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<College> collegeList = null;
	private List<College> collegeFltrdList = null;
	College collegesFilter = new College();

	public CollegesService() {
		super();
		collegeList = new ArrayList<>();
		collegeFltrdList = new ArrayList<>();
	}

	public void save(College college) {
		if (college.getId() == -1) {
			college.setId((long) collegeList.size() + 1);
			collegeList.add(college);
		} else if (college.getId() == collegeList.size() + 1) {
			collegeList.add(college);
		} else {
			College existingCollege = null;
			int eachPosition = -1;
			int foundPosition = -1;
			for (College eachCollege : collegeList) {
				eachPosition++;
				if (eachCollege.getId().compareTo(college.getId()) == 0) {
					foundPosition = eachPosition;
					existingCollege = eachCollege;
					break;
				}
			}
			if (existingCollege != null) {
				collegeList.remove(existingCollege);
				collegeList.add(foundPosition, college);
			} else {
				collegeList.add(college);
			}

		}

		filterRows(collegesFilter);
	}

	public void delete(College college) {
		if (collegeList.contains(college)) {
			collegeList.remove(college);
		}
		filterRows(collegesFilter);
	}

	@Override
	public List<College> getItems() {
		if (collegeList == null) {
			collegeList = loadColleges();
		} else if (collegeList.isEmpty()) {
			collegeList = loadColleges();
		}

		filterRows(collegesFilter);

		return collegeFltrdList;
	}

	public List<College> resetFilter() {
		College emptyFilter = new College();

		filterRows(emptyFilter);
		return collegeFltrdList;
	}

	public void filterRows(College newCollFilter) {
		collegeFltrdList = new ArrayList<>();

		if (newCollFilter != null) {
			collegesFilter = newCollFilter;
		}

		if (collegesFilter.isEmpty()) {
			collegeFltrdList = collegeList;
		} else {
			for (College eachCollege : collegeList) {
				boolean includeCollege = true;

				if (!collegesFilter.getCollegeName().isEmpty()) {
					// Add college name to filter criteria.
					if (!eachCollege.getCollegeName().toLowerCase().contains(collegesFilter.getCollegeName().toLowerCase())) {
						includeCollege = false;
					}
				}
				if (!collegesFilter.getTypeOfSchool().isEmpty()) {
					// Add type of school to filter criteria.
					if (!eachCollege.getTypeOfSchool().toLowerCase().contains(collegesFilter.getTypeOfSchool().toLowerCase())) {
						includeCollege = false;
					}
				}
				if (!collegesFilter.getState().isEmpty()) {
					// Add state to filter criteria.
					if (!eachCollege.getState().toLowerCase().contains(collegesFilter.getState().toLowerCase())) {
						includeCollege = false;
					}
				}
				if (!collegesFilter.getZipCode().isEmpty()) {
					// Add zip code to filter criteria.
					if (!eachCollege.getZipCode().toLowerCase().contains(collegesFilter.getZipCode().toLowerCase())) {
						includeCollege = false;
					}
				}

				if (includeCollege) {
					collegeFltrdList.add(eachCollege);
				}
			}
		}
	}

	@Override
	public List<College> getPageItems(int pageNumber, int pageCount, int itemsPerPage) {
		// Skip itemsPerPage * pageNumber items, then add in the next itemsPerPage items
		List<College> itemsToReturn = new ArrayList<>();

		int firstItem = (pageNumber - 1) * itemsPerPage;
		int lastItem = firstItem + (itemsPerPage - 1);
		if (lastItem >= collegeFltrdList.size()) {
			lastItem = collegeFltrdList.size() - 1;
		}

		int eachItem = -1;
		for (College eachCollege : collegeFltrdList) {
			++eachItem;
			if ((eachItem >= firstItem) && (eachItem <= lastItem)) {
				itemsToReturn.add(eachCollege);
			}
		}

		return itemsToReturn;
	}

	@Override
	public int getPageCount(int itemsPerPage) {
		int pageCount = 0;

		if (collegeFltrdList != null) {
			if (!collegeFltrdList.isEmpty()) {
				pageCount = collegeFltrdList.size() / itemsPerPage;
			}
		}
		if (collegeFltrdList.size() > (pageCount * itemsPerPage)) {
			pageCount++;
		}

		return pageCount;
	}

	@Override
	public int getItemCount() {
		return collegeFltrdList.size();
	}

	@Override
	public long getMaxId() {
		long maxId = -1;

		for (College eachCollege : collegeList) {
			if (eachCollege.getId() > maxId) {
				maxId = eachCollege.getId();
			}
		}
		return maxId;

	}

	private List<College> loadColleges() {
		List<College> listOfColleges = new ArrayList<>();

		try {

			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/InstitutionCampus.csv");
            List<String> listOfStrings = Utils.readInStream(inputStream);
			
			long eachId = -1;
			for (String eachLine : listOfStrings) {
				eachId++;
				if (eachId > 0) {
					String fields[] = eachLine.split("\",\"");
					String collegeName = fields[3];
					String typeOfSchool = fields[6];
					String address = fields[7];
					String street = "";
					String city = "";
					String state = "";
					String zipCode = "";
					if (address.contains(", ")) {
						String section[] = address.split(", ");
						if (section.length > 1) {
							street = section[0];
							city = section[section.length - 2];
							String lastSection = section[section.length - 1];
							if (lastSection.contains(" ")) {
								String stateAndZip[] = lastSection.split(" ");
								if (stateAndZip.length == 2) {
									state = stateAndZip[0];
									zipCode = stateAndZip[1];
								}
							} else {
								state = lastSection;
							}
						}
					}
					String mainPhone = fields[8];
					String adminName = fields[9];
					String adminPhone = fields[10];
					String adminEmail = fields[11];
					if (!collegeName.isEmpty()) {
						College college = new College(eachId, collegeName, typeOfSchool, street, city, state, zipCode,
								mainPhone, adminName, adminPhone, adminEmail);
						listOfColleges.add(college);
					}
				}
			}
			return listOfColleges;

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Get city info from a HUGE text file.
		return listOfColleges;
	}

	// We're sorting on College Name, Type of School, State and Zip Code (one at a
	// time).
	public void sortRowsBy(String fieldName, boolean ascendingOrder) {
		if (fieldName.equals(Utils.COLLEGE_FIELD.ID.getFieldName())) {
			// Default sort order, for when user clicks button back to "no sort".
			collegeList.sort(new CompId());
		} else if (fieldName.equals(Utils.COLLEGE_FIELD.COLLEGE_NAME.getFieldName())) {
			if (ascendingOrder) {
				collegeList.sort(new CompCollegeNameUp());
			} else {
				collegeList.sort(new CompCollegeNameDown());
			}
		} else if (fieldName.equals(Utils.COLLEGE_FIELD.TYPE_OF_SCHOOL.getFieldName())) {
			if (ascendingOrder) {
				collegeList.sort(new CompTypeUp());
			} else {
				collegeList.sort(new CompTypeDown());
			}
		} else if (fieldName.equals(Utils.COLLEGE_FIELD.STATE.getFieldName())) {
			if (ascendingOrder) {
				collegeList.sort(new CompStateUp());
			} else {
				collegeList.sort(new CompStateDown());
			}
		} else if (fieldName.equals(Utils.COLLEGE_FIELD.ZIP_CODE.getFieldName())) {
			if (ascendingOrder) {
				collegeList.sort(new CompZipUp());
			} else {
				collegeList.sort(new CompZipDown());
			}
		}

		filterRows(collegesFilter);
	}

	// We're sorting on College Name, Type of School, State and Zip Code (one at a
	// time).
	// We're filtering on College Name, Type of School, City, State and Zip Code
	// (possibly all at once).

	class CompCollegeNameUp implements java.util.Comparator<College> {
		@Override
		public int compare(College a, College b) {
			String collegeNameA = a.getCollegeName();
			String collegeNameB = b.getCollegeName();
			int result = collegeNameA.compareToIgnoreCase(collegeNameB);

			return result;
		}
	}

	class CompCollegeNameDown implements java.util.Comparator<College> {
		@Override
		public int compare(College a, College b) {
			String collegeNameA = a.getCollegeName();
			String collegeNameB = b.getCollegeName();
			int result = collegeNameB.compareToIgnoreCase(collegeNameA);

			return result;
		}
	}

	class CompTypeUp implements java.util.Comparator<College> {
		@Override
		public int compare(College a, College b) {
			String typeOfSchoolA = a.getTypeOfSchool();
			String typeOfSchoolB = b.getTypeOfSchool();
			int result = typeOfSchoolA.compareToIgnoreCase(typeOfSchoolB);

			return result;
		}
	}

	class CompTypeDown implements java.util.Comparator<College> {
		@Override
		public int compare(College a, College b) {
			String typeOfSchoolA = a.getTypeOfSchool();
			String typeOfSchoolB = b.getTypeOfSchool();
			int result = typeOfSchoolB.compareToIgnoreCase(typeOfSchoolA);

			return result;
		}
	}

	class CompStateUp implements java.util.Comparator<College> {
		@Override
		public int compare(College a, College b) {
			String stateNameA = a.getState();
			String stateNameB = b.getState();
			int result = stateNameA.compareToIgnoreCase(stateNameB);

			return result;
		}
	}

	class CompStateDown implements java.util.Comparator<College> {
		@Override
		public int compare(College a, College b) {
			String stateNameA = a.getState();
			String stateNameB = b.getState();
			int result = stateNameB.compareToIgnoreCase(stateNameA);

			return result;
		}
	}

	class CompZipUp implements java.util.Comparator<College> {
		@Override
		public int compare(College a, College b) {
			String zipCodeA = a.getZipCode();
			String zipCodeB = b.getZipCode();
			int result = zipCodeA.compareToIgnoreCase(zipCodeB);

			return result;
		}
	}

	class CompZipDown implements java.util.Comparator<College> {
		@Override
		public int compare(College a, College b) {
			String zipCodeA = a.getZipCode();
			String zipCodeB = b.getZipCode();
			int result = zipCodeB.compareToIgnoreCase(zipCodeA);

			return result;
		}
	}

	class CompId implements java.util.Comparator<College> {
		@Override
		public int compare(College a, College b) {
			return (int) (a.getId() - b.getId());
		}
	}

}
