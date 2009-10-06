package com.anasoft.os.sample.dmf.client.model {
    
    import flash.events.EventDispatcher;
    
    import mx.collections.ArrayCollection;
    
    [Bindable]
    public class GridModel extends EventDispatcher {
        
        public var items:ArrayCollection;
        
        private var _totalCount:int;
        
        private var changeListeners:ArrayCollection = new ArrayCollection();
        
        public function addChangeListener(listener:ModelChangeListener): void {
            changeListeners.addItem(listener);
        }
        
        public function fireTotalCountChange(): void {
            for each (var listener:ModelChangeListener in changeListeners) {
                listener.onTotalCountChange(this);
            }
        }
        
        [Bindable(event="totalCountChanged")]
        public function get totalCount(): int {
            return _totalCount;
        }
        
        public function set totalCount(totalCount:int): void {
            var originalValue:int = _totalCount;
            _totalCount = totalCount;
            dispatchEvent(new Event("totalCountChanged"));
            
            if (totalCount != originalValue)
                fireTotalCountChange();
        }
        
    }
    
}
