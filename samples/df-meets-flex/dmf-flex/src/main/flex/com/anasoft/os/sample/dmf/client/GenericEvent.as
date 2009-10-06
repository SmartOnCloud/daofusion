package com.anasoft.os.sample.dmf.client {
    
    import flash.events.Event;
    import org.swizframework.Swiz;
    
    public class GenericEvent extends Event {
        
        public function GenericEvent(eventType:String, bubbles:Boolean = false, cancelable:Boolean = false) {
            super(eventType, bubbles, cancelable);
        }
        
        public function dispatch(): void {
            Swiz.dispatchEvent(this);
        }
        
    }
    
}
