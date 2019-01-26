(function ChangeUsernameController(){
    scriptLoader.loadScript("js/account/change_username/change_username_validator.js");
    scriptLoader.loadScript("js/account/change_username/change_username_service.js");
    
    events.USERNAME_VALIDATION_ATTEMPT = "username_validation_attempt";
    
    let timeout;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.USERNAME_VALIDATION_ATTEMPT},
        setAttempt
    ));
    
    function setAttempt(){
        eventProcessor.processEvent(new Event(events.BLOCK_USERNAME_CHANGE));
        
        if(timeout){
            clearTimeout(timeout);
        }
        timeout = setTimeout(startValidation, 1000);
        
        function startValidation(){
            eventProcessor.processEvent(new Event(events.VALIDATE_USERNAME));
        }
    }
    
    $(document).ready(function(){
        addEventListeners();
        
        function addEventListeners(){
            $(".change-username-input").on("keyup", function(e){
                if(e.which == 13){
                    eventProcessor.processEvent(new Event(events.CHANGE_USERNAME_ATTEMPT));
                }else{
                    eventProcessor.processEvent(new Event(events.USERNAME_VALIDATION_ATTEMPT));
                }
            });
            $(".change-username-input").on("focusin", function(){
                eventProcessor.processEvent(new Event(events.USERNAME_VALIDATION_ATTEMPT));
            });
        }
    });
})();