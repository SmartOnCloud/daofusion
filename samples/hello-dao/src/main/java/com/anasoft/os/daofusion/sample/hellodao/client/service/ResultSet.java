package com.anasoft.os.daofusion.sample.hellodao.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResultSet<DTO extends IsSerializable> implements IsSerializable {
    
    private List<DTO> resultList;
    private Integer totalRecords;
    
    // for serialization purposes
    protected ResultSet() {
    }
    
    public ResultSet(List<DTO> resultList, Integer totalRecords) {
        this.resultList = resultList;
        this.totalRecords = totalRecords;
    }
    
    public List<DTO> getResultList() {
        return resultList;
    }
    
    public Integer getTotalRecords() {
        return totalRecords;
    }
    
}
