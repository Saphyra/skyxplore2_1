(function ChangePasswordController(){
    scriptLoader.loadScript("js/account/change_password/password_validator.js");
    
    events.PASSWORD_VALIDATION_ATTEMPT = "password_validation_attempt";
    events.CHANGE_PASSWORD_ATTEMPT = "change_password_attempt";
    events.BLOCK_PASSWORD_CHANGE = "block_password_change";
    events.ALLOW_PASSWORD_CHANGE = "allow_password_change";
    
    let timeout;
    let passwordChangeEnabled = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ALLOW_PASSWORD_CHANGE},
        function(){
            passwordChangeEnabled = true;
            document.getElementById("change-password-button").disabled = false;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.BLOCK_PASSWORD_CHANGE},
        function(){
            passwordChangeEnabled = false;
            document.getElementById("change-password-button").disabled = true;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.PASSWORD_VALIDATION_ATTEMPT},
        setAttempt
    ));
    
    function setAttempt(){
        eventProcessor.processEvent(new Event(events.BLOCK_PASSWORD_CHANGE));
        
        if(timeout){
            clearTimeout(timeout);
        }
        timeout = setTimeout(startValidation, 1000);
        
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