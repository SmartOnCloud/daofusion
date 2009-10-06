package com.anasoft.os.sample.dmf.client {
    
    import mx.collections.ArrayCollection;
    
    public class EntityFetchResult extends Result {
        
        public var items:ArrayCollection;
        public var totalCount:int;
        
        public function EntityFetchResult(eventType:String) {
            super(eventType);
        }
        
    }
    
}
