package com.anasoft.os.sample.dmf.client {
    
    public class Command extends GenericEvent {
        
        public static const TYPE:String = "Command";
        
        public function Command() {
            super(TYPE, false, true);
        }
        
    }
    
}
