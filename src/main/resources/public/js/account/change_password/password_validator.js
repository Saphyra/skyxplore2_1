(function PasswordValidator(){
    scriptLoader.loadScript("js/common/validation_util.js");
    
    events.VALIDATE_PASSWORD = "validate_password";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATE_PASSWORD},
        validatePassword;
    ));
    
    function validatePassword(){
        
    }
})();