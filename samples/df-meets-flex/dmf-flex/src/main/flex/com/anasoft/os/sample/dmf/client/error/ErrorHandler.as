package com.anasoft.os.sample.dmf.client.error {
    
    import mx.controls.Alert;
    import mx.rpc.events.FaultEvent;
    
    public class ErrorHandler {
        
        public static function onChannelSetFault(faultEvent:FaultEvent): void {
            Alert.show(faultEvent.fault.faultString, "Server communication error");
        }
        
        public static function onServiceCallFault(faultEvent:FaultEvent): void {
            Alert.show(faultEvent.fault.faultString, "Server call failed");
        }
        
    }
    
}
