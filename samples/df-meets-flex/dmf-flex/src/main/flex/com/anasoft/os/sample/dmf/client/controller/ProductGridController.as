package com.anasoft.os.sample.dmf.client.controller {
    
    import com.anasoft.os.sample.dmf.client.command.GetProducts;
    import com.anasoft.os.sample.dmf.client.command.GotProducts;
    import com.anasoft.os.sample.dmf.client.cto.FlexCriteriaTransferObject;
    import com.anasoft.os.sample.dmf.client.model.ProductGridModel;
    
    public class ProductGridController implements GridController {
        
        [Autowire]
        public var model:ProductGridModel;
        
        public function fetchData(cto:FlexCriteriaTransferObject): void {
            new GetProducts(cto).dispatch();
        }
        
        [Mediate(event="GotProducts.TYPE")]
        public function gotProducts(result:GotProducts): void {
            model.items = result.items;
            model.totalCount = result.totalCount;
        }
        
    }
    
}
