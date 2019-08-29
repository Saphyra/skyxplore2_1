(function AccountDeletionValidator(){
    scriptLoader.loadScript("/js/common/validation_util.js");
    scriptLoader.loadScript("/js/account/validation_result.js");
    
    events.VALIDATE_ACCOUNT_DELETION = "validate_account_deletion";
    
    const INVALID_PASSWORD = "#invalid-delete-account-password";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATE_ACCOUNT_DELETION},
        validateAccountDeletion
    ));
    
    function validateAccountDeletion(){
        const password = getPassword();
        let passwordValid = true;
        
        const validationResult = new ValidationResult([INVALID_PASSWORD]);
        
        if(password.length == 0){
            passwordValid = false;
            validationResult.createErrorFieldFor(INVALID_PASSWORD, "empty-password");
        }
        
        validationResult.processResult();
        if(isValid()){
            eventProcessor.processEvent(new Event(events.ALLOW_DELETE_ACCOUNT));
        }else{
            eventProcessor.processEvent(new Event(events.BLOCK_DELETE_ACCOUNT));
        }
        
        function isValid(){
            return passwordValid && password === getPassword();
        }
        
        function getPassword(){
            return $("#delete-account-password").val();
        }
    }
})();