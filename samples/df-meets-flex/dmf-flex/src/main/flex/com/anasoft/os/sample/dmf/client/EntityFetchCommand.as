package com.anasoft.os.sample.dmf.client {
    
    import com.anasoft.os.sample.dmf.client.cto.FlexCriteriaTransferObject;
    
    public class EntityFetchCommand extends Command {
        
        public var cto:FlexCriteriaTransferObject;
        
        public function EntityFetchCommand(cto:FlexCriteriaTransferObject) {
            super();
            this.cto = cto;
        }
        
    }
    
}
