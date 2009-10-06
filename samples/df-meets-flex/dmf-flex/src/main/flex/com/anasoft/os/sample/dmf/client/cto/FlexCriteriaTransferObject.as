package com.anasoft.os.sample.dmf.client.cto {
    
    import flash.utils.Dictionary;
    
    [RemoteClass(alias="com.anasoft.os.sample.dmf.service.cto.FlexCriteriaTransferObject")]
    public class FlexCriteriaTransferObject {
        
        public static const UNDEFINED:int = -1;
        
        public var firstResult:int;
        public var maxResults:int;
        
        public var criteriaMap:Dictionary = new Dictionary();
        
        public function FlexCriteriaTransferObject(firstResult:int = UNDEFINED, maxResults:int = UNDEFINED) {
            this.firstResult = firstResult;
            this.maxResults = maxResults;
        }
        
        public function add(criteria:FlexFilterAndSortCriteria): void {
            criteriaMap[criteria.propertyId] = criteria;
        }
        
        public function getCriteria(propertyId:String): FlexFilterAndSortCriteria {
            if (!criteriaMap.hasOwnProperty(propertyId))
                add(new FlexFilterAndSortCriteria(propertyId));
            
            return criteriaMap[propertyId];
        }
        
    }
    
}
