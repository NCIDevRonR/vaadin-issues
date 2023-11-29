package com.empowerai.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.empowerai.entity.ListCrudEntity;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public abstract class ListCrudService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private List<CityInfo> cityInfoList = null;

	ListCrudService() {
//		cityInfoList = new ArrayList<>();
	}

//	public abstract <T extends ListCrudEntity> void save(Class<T> cityInfo);
//
//	public abstract <T extends ListCrudEntity> void delete(Class<T> cityInfo);
	
	public abstract List<? extends ListCrudEntity> getItems();
    
	
	public abstract List<? extends ListCrudEntity> getPageItems(int pageNumber, int pageCount, int itemsPerPage);

	public abstract int getPageCount(int itemsPerPage);

	public abstract int getItemCount();
	

	public abstract long getMaxId();

//    public abstract void sortRowsBy(String fieldName, boolean ascendingOrder);
    
}

