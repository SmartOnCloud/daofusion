package com.anasoft.os.sample.dmf.service;

import java.util.ArrayList;
import java.util.List;


public abstract class EntityFetchResult<DTO extends DataTransferObject> implements Result {

    private List<DTO> items = new ArrayList<DTO>();
    private Integer totalCount;
    
	public void addItem(DTO item) {
	    items.add(item);
	}
	
    public List<? extends DTO> getItems() {
        return items;
    }
    
	public void setItems(List<DTO> items) {
		this.items = items;
	}
	
	public Integer getTotalCount() {
	    return totalCount;
	}
	
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
    
}
