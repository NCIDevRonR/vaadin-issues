package com.empowerai.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import com.empowerai.entity.CityInfo;
import com.empowerai.util.Utils;
import com.empowerai.view.CityInfoView;
import com.vaadin.cdi.annotation.RouteScopeOwner;
import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.cdi.annotation.VaadinServiceEnabled;
import com.vaadin.cdi.annotation.VaadinServiceScoped;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@SessionScoped
public class CityInfoService extends ListCrudService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CityInfo> cityInfoList = null;
	private List<CityInfo> cityInfoFltrdList = null;
	CityInfo cityInfoFilter = new CityInfo();

	public CityInfoService() {
		super();
		cityInfoList = new ArrayList<>();
		cityInfoFltrdList = new ArrayList<>();
	}

	public void save(CityInfo cityInfo) {
		if (cityInfo.getId() == -1) {
			cityInfo.setId((long) cityInfoList.size() + 1);
			cityInfoList.add(cityInfo);
		} else if (cityInfo.getId() == cityInfoList.size() + 1) {
			cityInfoList.add(cityInfo);
		} else {
			CityInfo existingCityInfo = null;
			int eachPosition = -1;
			int foundPosition = -1;
			for (CityInfo eachCityInfo : cityInfoList) {
				eachPosition++;
				if (eachCityInfo.getId().compareTo(cityInfo.getId()) == 0) {
					foundPosition = eachPosition;
					existingCityInfo = eachCityInfo;
					break;
				}
			}
			if (existingCityInfo != null) {
				cityInfoList.remove(existingCityInfo);
				cityInfoList.add(foundPosition, cityInfo);
			} else {
				cityInfoList.add(cityInfo);
			}

		}
		filterRows(cityInfoFilter);
	}

	public void delete(CityInfo cityInfo) {
		if (cityInfoList.contains(cityInfo)) {
			cityInfoList.remove(cityInfo);
		}
		filterRows(cityInfoFilter);
	}

	@Override
	public List<CityInfo> getItems() {
		if (cityInfoList == null) {
			cityInfoList = loadCityInfo();
		} else if (cityInfoList.isEmpty()) {
			cityInfoList = loadCityInfo();
		}
		filterRows(cityInfoFilter);

		return cityInfoFltrdList;
	}

	public void filterRows(CityInfo newCollFilter) {
		cityInfoFltrdList = new ArrayList<>();

		if (newCollFilter != null) {
			cityInfoFilter = newCollFilter;
		}

		if (cityInfoFilter.isEmpty()) {
			cityInfoFltrdList = cityInfoList;
		} else {
			for (CityInfo eachCityInfo : cityInfoList) {
				boolean includeCityInfo = true;

				if (!cityInfoFilter.getZipCode().isEmpty()) {
					// Add zip code to filter criteria.
					if (!eachCityInfo.getZipCode().toLowerCase().contains(cityInfoFilter.getZipCode().toLowerCase())) {
						includeCityInfo = false;
					}
				}
				if (!cityInfoFilter.getCityName().isEmpty()) {
					// Add city name to filter criteria.
					if (!eachCityInfo.getCityName().toLowerCase()
							.contains(cityInfoFilter.getCityName().toLowerCase())) {
						includeCityInfo = false;
					}
				}
				if (!cityInfoFilter.getStateName().isEmpty()) {
					// Add state name to filter criteria.
					if (!eachCityInfo.getStateName().toLowerCase()
							.contains(cityInfoFilter.getStateName().toLowerCase())) {
						includeCityInfo = false;
					}
				}
				if (!cityInfoFilter.getStateAbbrev().isEmpty()) {
					// Add state abbreviation to filter criteria.
					if (!eachCityInfo.getStateAbbrev().toLowerCase()
							.contains(cityInfoFilter.getStateAbbrev().toLowerCase())) {
						includeCityInfo = false;
					}
				}

				if (includeCityInfo) {
					cityInfoFltrdList.add(eachCityInfo);
				}
			}
		}
	}

	@Override
	public List<CityInfo> getPageItems(int pageNumber, int pageCount, int itemsPerPage) {
		// Skip itemsPerPage * pageNumber items, then add in the next itemsPerPage items
		List<CityInfo> itemsToReturn = new ArrayList<>();

		int firstItem = (pageNumber - 1) * itemsPerPage;
		int lastItem = firstItem + (itemsPerPage - 1);
		if (lastItem >= cityInfoFltrdList.size()) {
			lastItem = cityInfoFltrdList.size() - 1;
		}

		int eachItem = -1;
		for (CityInfo eachCity : cityInfoFltrdList) {
			++eachItem;
			if ((eachItem >= firstItem) && (eachItem <= lastItem)) {
				itemsToReturn.add(eachCity);
			}
		}

		return itemsToReturn;
	}

	@Override
	public int getPageCount(int itemsPerPage) {
		int pageCount = 0;

		if (cityInfoFltrdList != null) {
			if (!cityInfoFltrdList.isEmpty()) {
				pageCount = cityInfoFltrdList.size() / itemsPerPage;
			}
		}
		if (cityInfoFltrdList.size() > (pageCount * itemsPerPage)) {
			pageCount++;
		}

		return pageCount;
	}

	@Override
	public int getItemCount() {
		return cityInfoFltrdList.size();
	}

	@Override
	public long getMaxId() {
		long maxId = -1;

		for (CityInfo eachCityInfo : cityInfoList) {
			if (eachCityInfo.getId() > maxId) {
				maxId = eachCityInfo.getId();
			}
		}
		return maxId;

	}

	private List<CityInfo> loadCityInfo() {
		List<CityInfo> listOfCities = new ArrayList<>();

		try {

			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/US.txt");
            List<String> listOfStrings = Utils.readInStream(inputStream);

			long eachId = 0;
			for (String eachLine : listOfStrings) {
				eachId++;
				String fields[] = eachLine.split("\t");
				String zipCode = fields[1];
				String cityName = fields[2];
				String stateName = fields[3];
				String stateAbbrev = fields[4];
				String county = fields[5];
				if (!stateName.isEmpty()) {
					CityInfo cityInfo = new CityInfo(eachId, zipCode, cityName, stateName, stateAbbrev, county);
					listOfCities.add(cityInfo);
				}
			}
			return listOfCities;

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Get city info from a HUGE text file.
		return listOfCities;
	}


	public void sortRowsBy(String fieldName, boolean ascendingOrder) {
		if (fieldName.equals(Utils.CITY_INFO_FIELD.ID.getFieldName())) {
			// Default sort order, for when user clicks button back to "no sort".
			cityInfoList.sort(new CompId());
		} else if (fieldName.equals(Utils.CITY_INFO_FIELD.ZIP_CODE.getFieldName())) {
			if (ascendingOrder) {
				cityInfoList.sort(new CompZipUp());
			} else {
				cityInfoList.sort(new CompZipDown());
			}
		} else if (fieldName.equals(Utils.CITY_INFO_FIELD.CITY_NAME.getFieldName())) {
			if (ascendingOrder) {
				cityInfoList.sort(new CompCityUp());
			} else {
				cityInfoList.sort(new CompCityDown());
			}
		} else if (fieldName.equals(Utils.CITY_INFO_FIELD.STATE_NAME.getFieldName())) {
			if (ascendingOrder) {
				cityInfoList.sort(new CompStateNameUp());
			} else {
				cityInfoList.sort(new CompStateNameDown());
			}
		} else if (fieldName.equals(Utils.CITY_INFO_FIELD.STATE_ABBREV.getFieldName())) {
			if (ascendingOrder) {
				cityInfoList.sort(new CompStateAbbrvUp());
			} else {
				cityInfoList.sort(new CompStateAbbrvDown());
			}
		}
	}

	class CompZipUp implements java.util.Comparator<CityInfo> {
		@Override
		public int compare(CityInfo a, CityInfo b) {
			String zipCodeA = a.getZipCode();
			String zipCodeB = b.getZipCode();
			int result = zipCodeA.compareToIgnoreCase(zipCodeB);

			return result;
		}
	}

	class CompZipDown implements java.util.Comparator<CityInfo> {
		@Override
		public int compare(CityInfo a, CityInfo b) {
			String zipCodeA = a.getZipCode();
			String zipCodeB = b.getZipCode();
			int result = zipCodeB.compareToIgnoreCase(zipCodeA);

			return result;
		}
	}

	class CompCityUp implements java.util.Comparator<CityInfo> {
		@Override
		public int compare(CityInfo a, CityInfo b) {
			String cityNameA = a.getCityName();
			String cityNameB = b.getCityName();
			int result = cityNameA.compareToIgnoreCase(cityNameB);

			return result;
		}
	}

	class CompCityDown implements java.util.Comparator<CityInfo> {
		@Override
		public int compare(CityInfo a, CityInfo b) {
			String cityNameA = a.getCityName();
			String cityNameB = b.getCityName();
			int result = cityNameB.compareToIgnoreCase(cityNameA);

			return result;
		}
	}

	class CompStateNameUp implements java.util.Comparator<CityInfo> {
		@Override
		public int compare(CityInfo a, CityInfo b) {
			String stateNameA = a.getStateName();
			String stateNameB = b.getStateName();
			int result = stateNameA.compareToIgnoreCase(stateNameB);

			return result;
		}
	}

	class CompStateNameDown implements java.util.Comparator<CityInfo> {
		@Override
		public int compare(CityInfo a, CityInfo b) {
			String stateNameA = a.getStateName();
			String stateNameB = b.getStateName();
			int result = stateNameB.compareToIgnoreCase(stateNameA);

			return result;
		}
	}

	class CompStateAbbrvUp implements java.util.Comparator<CityInfo> {
		@Override
		public int compare(CityInfo a, CityInfo b) {
			String stateAbbrevA = a.getStateAbbrev();
			String stateAbbrevB = b.getStateAbbrev();
			int result = stateAbbrevA.compareToIgnoreCase(stateAbbrevB);

			return result;
		}
	}

	class CompStateAbbrvDown implements java.util.Comparator<CityInfo> {
		@Override
		public int compare(CityInfo a, CityInfo b) {
			String stateAbbrevA = a.getStateAbbrev();
			String stateAbbrevB = b.getStateAbbrev();
			int result = stateAbbrevB.compareToIgnoreCase(stateAbbrevA);

			return result;
		}
	}

	class CompId implements java.util.Comparator<CityInfo> {
		@Override
		public int compare(CityInfo a, CityInfo b) {
			return (int) (a.getId() - b.getId());
		}
	}

}
