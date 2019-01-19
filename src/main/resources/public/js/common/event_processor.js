(function EventProcessor(){
    const processors = [];
    
    window.eventProcessor = new function(){
        this.registerProcessor = registerProcessor;
        this.processEvent = processEvent;
    }
    
    function registerProcessor(processor){
        processors.push(processor);
    }
    
    function processEvent(event){
        for(pindex in processors){
            const processor = processors[pindex];
            if(processor.canProcess(event.getEventType())){
                processor.process(event);
            }else{
                alert("cannot proccess");
            }
        }
    }
})();

function EventProcessor(canProcessCallback, processEventCallback){
    const canProcess = canProcessCallback;
    const processEvent = processEventCallback;
    
    this.canProcess = function(eventType){
        return canProcess(eventType);
    }
    
    this.process = function(event){
        processEvent(event);
    }
}

function Event(type, data){
    const eventType = type;
    const payload = data || null;
    
    this.getPayload = function(){
        return payload;
    }
    
    this.getEventType = function(){
        return eventType;
    }
}