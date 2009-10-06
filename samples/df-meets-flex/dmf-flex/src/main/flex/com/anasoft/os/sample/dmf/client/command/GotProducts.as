package com.anasoft.os.sample.dmf.client.command {
    
    import com.anasoft.os.sample.dmf.client.EntityFetchResult;
    
    [RemoteClass(alias="com.anasoft.os.sample.dmf.service.command.GotProducts")]
    public class GotProducts extends EntityFetchResult {
        
        public static const TYPE:String = "GotProducts";
        
        public function GotProducts() {
            super(TYPE);
        }
        
    }
    
}
