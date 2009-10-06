package com.anasoft.os.sample.dmf.service;

import java.util.List;

import com.anasoft.os.daofusion.PersistentEntityDao;
import com.anasoft.os.daofusion.criteria.PersistentEntityCriteria;
import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.anasoft.os.daofusion.cto.server.CriteriaTransferObjectCountWrapper;
import com.anasoft.os.daofusion.entity.Persistable;
import com.anasoft.os.sample.dmf.service.cto.AbstractCtoConverter;
import com.anasoft.os.sample.dmf.service.cto.FlexCriteriaTransferObject;

public abstract class EntityFetchCommand<RESULT extends EntityFetchResult<DTO>, DTO extends DataTransferObject, ENTITY extends Persistable<?>>
        implements Command<RESULT> {

    private FlexCriteriaTransferObject cto;
    
    public FlexCriteriaTransferObject getCto() {
        return cto;
    }
    
    public void setCto(FlexCriteriaTransferObject cto) {
        this.cto = cto;
    }
    
    protected void updateResult(RESULT result) {
        CriteriaTransferObject realCto = cto.getActualType();
        PersistentEntityCriteria queryCriteria = getCtoConverter().convert(realCto);
        List<ENTITY> entityList = getDao().query(queryCriteria);
        
        for (ENTITY entity : entityList) {
            DTO item = convertEntity(entity);
            result.addItem(item);
        }
        
        CriteriaTransferObject realCountCto = new CriteriaTransferObjectCountWrapper(realCto).wrap();
        PersistentEntityCriteria countCriteria = getCtoConverter().convert(realCountCto);
        int count = getDao().count(countCriteria);
        
        result.setTotalCount(count);
    }
    
    protected abstract PersistentEntityDao<ENTITY, ?> getDao();
    
    protected abstract AbstractCtoConverter getCtoConverter();
    
    protected abstract DTO convertEntity(ENTITY entity);
    
}
