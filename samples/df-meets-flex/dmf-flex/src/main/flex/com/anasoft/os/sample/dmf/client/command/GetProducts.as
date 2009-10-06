package com.anasoft.os.sample.dmf.client.command {
    
    import com.anasoft.os.sample.dmf.client.EntityFetchCommand;
    import com.anasoft.os.sample.dmf.client.cto.FlexCriteriaTransferObject;
    
    [RemoteClass(alias="com.anasoft.os.sample.dmf.service.command.GetProducts")]
    public class GetProducts extends EntityFetchCommand {
        
        public function GetProducts(cto:FlexCriteriaTransferObject) {
            super(cto);
        }
        
    }
    
}
