(function ChangeEmailController(){
    scriptLoader.loadScript("js/account/change_email/change_email_validator.js");
    scriptLoader.loadScript("js/account/change_email/change_email_service.js");
    
    events.EMAIL_VALIDATION_ATTEMPT = "email_validation_attempt";
    
    let timeout;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.EMAIL_VALIDATION_ATTEMPT},
        setAttempt
    ));
    
    function setAttempt(){
        eventProcessor.processEvent(new Event(events.BLOCK_EMAIL_CHANGE));
        
        if(timeout){
            clearTimeout(timeout);
        }
        timeout = setTimeout(startValidation, 1000);
        
        function startValidation(){
            eventProcessor.processEvent(new Event(events.VALIDATE_EMAIL));
        }
    }
    
    $(document).ready(function(){
        addEventListeners();
        
        function addEventListeners(){
            $(".change-email-input").on("keyup", function(e){
                if(e.which == 13){
                    eventProcessor.processEvent(new Event(events.CHANGE_EMAIL_ATTEMPT));
                }else{
                    eventProcessor.processEvent(new Event(events.EMAIL_VALIDATION_ATTEMPT));
                }
            });
            $(".change-email-input").on("focusin", function(){
                eventProcessor.processEvent(new Event(events.EMAIL_VALIDATION_ATTEMPT));
            });
        }
    });
})();