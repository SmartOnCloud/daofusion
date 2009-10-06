package com.anasoft.os.sample.dmf.client.controller {
	
    import com.anasoft.os.sample.dmf.client.Command;
    import com.anasoft.os.sample.dmf.client.Result;
    
    import mx.rpc.events.ResultEvent;
    import mx.rpc.remoting.RemoteObject;
    
    import org.swizframework.Swiz;
	
    public class CommandController {
    	
    	[Autowire]
    	public var remoteService:RemoteObject;
        
        [Mediate(event="Command.TYPE")]
        public function process(command:Command): void {
        	Swiz.executeServiceCall(remoteService.process(command), onResult);
        }
        
        private function onResult(resultEvent:ResultEvent): void {
            var result:Result = resultEvent.result as Result;
            result.dispatch();
        }
        
	}
	
}
