package com.anasoft.os.sample.dmf.client.cto {
    
    import mx.collections.ArrayList;
    
    [RemoteClass(alias="com.anasoft.os.sample.dmf.service.cto.FlexFilterAndSortCriteria")]
    public class FlexFilterAndSortCriteria {
        
        public var propertyId:String;
        public var filterValues:ArrayList = new ArrayList();
        
        public var sortAscending:Boolean;
        public var ignoreCase:Boolean;
        
        public function FlexFilterAndSortCriteria(propertyId:String) {
            this.propertyId = propertyId;
        }
        
        public function clearFilterValues(): void {
            filterValues.removeAll();
        }
        
        public function setFilterValue(value:String): void {
            clearFilterValues();
            filterValues.addItem(value);
        }
        
        public function setFilterValues(values:Array): void {
            clearFilterValues();
            for each (var value:String in values) {
                filterValues.addItem(value);
            }
        }
        
    }
    
}
