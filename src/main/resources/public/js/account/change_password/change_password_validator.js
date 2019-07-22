(function PasswordValidator(){
    scriptLoader.loadScript("/js/common/validation_util.js");
    scriptLoader.loadScript("/js/account/validation_result.js");
    
    events.VALIDATE_PASSWORD = "validate_password";
    
    const INVALID_NEW_PASSWORD = "#invalid-new-password";
    const INVALID_CONFIRM_PASSWORD = "#invalid-new-password-confirmation";
    const INVALID_CURRENT_PASSWORD = "#invalid-current-password-for-password-change";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATE_PASSWORD},
        validatePassword
    ));
    
    function validatePassword(){
        const newPassword = getNewPassword();
        const confirmPassword = getConfirmPassword();
        const currentPassword = getCurrentPassword();
        
        const validationResult = new ValidationResult([INVALID_CONFIRM_PASSWORD, INVALID_CURRENT_PASSWORD, INVALID_NEW_PASSWORD]);
        
        let newPasswordValid = true;
        let confirmPasswordValid = true;
        let currentPasswordValid = true;
        
        if(newPassword.length < 6){
            validationResult.createErrorFieldFor(INVALID_NEW_PASSWORD, "NEW_PASSWORD_TOO_SHORT");
            newPasswordValid = false;
        }else if(newPassword.length > 30){
            validationResult.createErrorFieldFor(INVALID_NEW_PASSWORD, "NEW_PASSWORD_TOO_LONG");
            newPasswordValid = false;
        }else if(newPassword !== confirmPassword){
            validationResult.createErrorFieldFor(INVALID_NEW_PASSWORD, "BAD_CONFIRM_PASSWORD");
            validationResult.createErrorFieldFor(INVALID_CONFIRM_PASSWORD, "BAD_CONFIRM_PASSWORD");
            newPasswordValid = false;
            confirmPasswordValid = false;
        }
        
        if(currentPassword.length == 0){
            validationResult.createErrorFieldFor(INVALID_CURRENT_PASSWORD, "CURRENT_PASSWORD_IS_EMPTY");
            currentPasswordValid = false;
        }
        
        validationResult.processResult();
        if(isValid()){
            eventProcessor.processEvent(new Event(events.ALLOW_PASSWORD_CHANGE));
        }else{
            eventProcessor.processEvent(new Event(events.BLOCK_PASSWORD_CHANGE));
        }
        
        function isValid(){
            const inputsValid = newPasswordValid && confirmPasswordValid && currentPasswordValid;
            const inputsNotChanged = newPassword === getNewPassword() && confirmPassword == getConfirmPassword() && currentPassword && getCurrentPassword();
            return inputsValid && inputsNotChanged;
        }
    }
    
    function getNewPassword(){
        return $("#new-password").val();
    }
    
    function getConfirmPassword(){
        return $("#new-password-confirmation").val();
    }
    
    function getCurrentPassword(){
        return $("#current-password-for-password-change").val();
    }
})();