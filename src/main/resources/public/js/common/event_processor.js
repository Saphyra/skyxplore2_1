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
        if(!event instanceof Event){
            throwException("IllegalArgument", "event is not a type of Event.");
        }
        
        const eventType = event.getEventType();
        
        let hasProcessor = false;
        for(pindex in processors){
            const processor = processors[pindex];
            if(processor.canProcess(eventType)){
                hasProcessor = true;
                setTimeout(function(){processor.process(event)}, 0);
            }
        }
        if(!hasProcessor){
            logService.log("No eventProcessor for eventType " + event.getEventType(), "warn");
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
    const eventType = type || throwException("IllegalArgument", "eventType must not be null.");
    const payload = data || null;
    
    this.getPayload = function(){
        return payload;
    }
    
    this.getEventType = function(){
        return eventType;
    }
}