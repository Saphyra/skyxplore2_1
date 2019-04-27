(function InputValidator(){
    scriptLoader.loadScript("js/index/validation_result.js");
    
    events.VALIDATION_ATTEMPT = "validation_attempt";
    events.VALIDATE = "validate";
    events.VALIDATION_ONGOING = "validation_ongoing";
    events.VALIDATION_FINISHED = "validation_finished";
    
    let timeout;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATION_ATTEMPT},
        setAttempt
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATE},
        function(){new ValidationResult().validate()}
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATION_ONGOING},
        function(event){event.getPayload().continueValidation(event.getPayload())}
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATION_FINISHED},
        function(event){event.getPayload().processValidationResult()}
    ));
    
    function setAttempt(){
        eventProcessor.processEvent(new Event(events.BLOCK_REGISTRATION));
        
        if(timeout){
            clearTimeout(timeout);
        }
        timeout = setTimeout(startValidation, getValidationTimeout());
        
        function startValidation(){
            eventProcessor.processEvent(new Event(events.VALIDATE));
        }
    }
})();