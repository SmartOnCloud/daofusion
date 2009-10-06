package com.anasoft.os.sample.dmf.client.view {
    
    import flash.events.Event;
    
    import mx.controls.Label;
    
    public class ActiveViewLabel extends Label {
        
        private var _activeView: int;
        
        [Autowire(bean="applicationModel", property="activeView")]
        [Bindable(event="activeViewChanged")]
        public function get activeView(): int {
            return _activeView;
        }
        
        public function set activeView(value:int): void {
            _activeView = value;
            dispatchEvent(new Event("activeViewChanged"));
            htmlText = "Active view = " + _activeView;
        }
        
    }
    
}
