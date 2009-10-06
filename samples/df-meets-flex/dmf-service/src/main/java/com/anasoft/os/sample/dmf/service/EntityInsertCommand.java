package com.anasoft.os.sample.dmf.service;

import com.anasoft.os.daofusion.PersistentEntityDao;
import com.anasoft.os.daofusion.entity.PersistentEntity;

public abstract class EntityInsertCommand<RESULT extends EntityInsertResult<DTO>, DTO extends DataTransferObject, ENTITY extends PersistentEntity<Long>>
        implements Command<RESULT> {

    protected void updateResult(RESULT result) {
        ENTITY entity = createEntity();
        getDao().saveOrUpdate(entity);
        
        DTO item = convertEntity(entity);
        
        result.setId(entity.getId());
        result.setItem(item);
    }
    
    protected abstract PersistentEntityDao<ENTITY, ?> getDao();
    
    protected abstract DTO convertEntity(ENTITY entity);
    
    protected abstract ENTITY createEntity();
    
}
