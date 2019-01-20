(function InputValidator(){
    events.VALIDATION_ATTEMPT = "validation_attempt";
    events.VALIDATE = "validate";
    
    let timeout;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATION_ATTEMPT},
        setAttempt
    ));
    
    function setAttempt(){
        eventProcessor.processEvent(events.BLOCK_REGISTRATION);
        
        if(timeout){
            clearTimeout(timeout);
        }
        timeout = setTimeout(startValidation, 1000);
    }
    
    function startValidation(){
        eventProcessor.processEvent(new Event(events.VALIDATE));
    }
})();