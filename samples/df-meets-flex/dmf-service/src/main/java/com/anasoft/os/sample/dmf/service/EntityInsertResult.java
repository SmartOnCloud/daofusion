package com.anasoft.os.sample.dmf.service;


public abstract class EntityInsertResult<DTO extends DataTransferObject> implements Result {

    private Long id;
    private DTO item;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public DTO getItem() {
        return item;
    }
    
    public void setItem(DTO item) {
        this.item = item;
    }
    
}
