package com.anasoft.os.sample.dmf.client.controller {
    
    import com.anasoft.os.sample.dmf.client.cto.FlexCriteriaTransferObject;
    
    public interface GridController {
        
        function fetchData(cto:FlexCriteriaTransferObject): void;
        
    }
    
}
