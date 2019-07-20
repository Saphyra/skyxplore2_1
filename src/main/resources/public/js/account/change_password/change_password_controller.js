(function ChangePasswordController(){
    scriptLoader.loadScript("/js/account/change_password/change_password_validator.js");
    scriptLoader.loadScript("/js/account/change_password/change_password_service.js");
    
    events.PASSWORD_VALIDATION_ATTEMPT = "password_validation_attempt";
    
    let timeout;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.PASSWORD_VALIDATION_ATTEMPT},
        setAttempt
    ));
    
    function setAttempt(){
        eventProcessor.processEvent(new Event(events.BLOCK_PASSWORD_CHANGE));
        
        if(timeout){
            clearTimeout(timeout);
        }
        timeout = setTimeout(startValidation, getValidationTimeout());
        
        function startValidation(){
            eventProcessor.processEvent(new Event(events.VALIDATE_PASSWORD));
        }
    }
    
    $(document).ready(function(){
        addEventListeners();
        
        function addEventListeners(){
            $(".change-password-input").on("keyup", function(e){
                if(e.which == 13){
                    eventProcessor.processEvent(new Event(events.CHANGE_PASSWORD_ATTEMPT));
                }else{
                    eventProcessor.processEvent(new Event(events.PASSWORD_VALIDATION_ATTEMPT));
                }
            });
            $(".change-password-input").on("focusin", function(){
                eventProcessor.processEvent(new Event(events.PASSWORD_VALIDATION_ATTEMPT));
            });
        }
    });
})();