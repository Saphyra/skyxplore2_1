(function DeleteAccountController(){
    scriptLoader.loadScript("js/account/delete_account/delete_account_validator.js");
    scriptLoader.loadScript("js/account/delete_account/delete_account_service.js");
    
    events.DELETE_ACCOUNT_VALIDATION_ATTEMPT = "delete_account_validation_attempt";
    
    let timeout;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DELETE_ACCOUNT_VALIDATION_ATTEMPT},
        setAttempt
    ));
    
    function setAttempt(){
        eventProcessor.processEvent(new Event(events.BLOCK_DELETE_ACCOUNT));
        
        if(timeout){
            clearTimeout(timeout);
        }
        timeout = setTimeout(startValidation, 1000);
        
        function startValidation(){
            eventProcessor.processEvent(new Event(events.VALIDATE_ACCOUNT_DELETION));
        }
    }
    
    $(document).ready(function(){
        addEventListeners();
        
        function addEventListeners(){
            $(".delete-account-input").on("keyup", function(e){
                if(e.which == 13){
                    eventProcessor.processEvent(new Event(events.DELETE_ACCOUNT_ATTEMPT));
                }else{
                    eventProcessor.processEvent(new Event(events.DELETE_ACCOUNT_VALIDATION_ATTEMPT));
                }
            });
            $(".delete-account-input").on("focusin", function(){
                eventProcessor.processEvent(new Event(events.DELETE_ACCOUNT_VALIDATION_ATTEMPT));
            });
        }
    });
})();