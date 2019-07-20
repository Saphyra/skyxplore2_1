(function ChangeEmailValidator(){
    scriptLoader.loadScript("/js/common/validation_util.js");
    scriptLoader.loadScript("/js/account/validation_result.js");
    
    events.VALIDATE_EMAIL = "validate_email";
    
    const INVALID_EMAIL = "#invalid-new-email";
    const INVALID_CURRENT_PASSWORD = "#invalid-change-email-password";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATE_EMAIL},
        validateEmail
    ));
    
    function validateEmail(){
        const email = getEmail();
        const password = getPassword();
        
        const validationResult = new ValidationResult([INVALID_EMAIL, INVALID_CURRENT_PASSWORD]);
        
        let emailValid = true;
        let passwordValid = true;
        
        if(password.length == 0){
            validationResult.createErrorFieldFor(INVALID_CURRENT_PASSWORD, "CURRENT_PASSWORD_IS_EMPTY");
            passwordValid = false;
        }
        
        if(!isEmailValid(email)){
            validationResult.createErrorFieldFor(INVALID_EMAIL, "INVALID_EMAIL");
            emailValid = false;
        }else{
            const request = new Request(HttpMethod.POST, Mapping.EMAIL_EXISTS, {value: email});
                request.isResponseOk = function(response){
                    return response.status == ResponseStatus.OK & (response.body === "true" || response.body === "false");
                }
                request.processValidResponse = function(response){
                    if(response.body === "false"){
                        emailValid = true;
                    }else{
                        emailValid = false;
                        validationResult.createErrorFieldFor(INVALID_EMAIL, "EMAIL_ALREADY_EXISTS");
                    }
                    processValidationResult();
                }
            dao.sendRequestAsync(request);
            return;
        }
        
        processValidationResult();
        
        function processValidationResult(){
            validationResult.processResult();
            if(isValid()){
                eventProcessor.processEvent(new Event(events.ALLOW_EMAIL_CHANGE));
            }else{
                eventProcessor.processEvent(new Event(events.BLOCK_EMAIL_CHANGE));
            }
        }
        
        function isValid(){
            const areFieldsValid = emailValid && passwordValid;
            const areFieldsTheSame = email === getEmail() && password === getPassword();
            return areFieldsValid && areFieldsTheSame;
        }
        
        function getEmail(){
            return $("#new-email").val();
        }
        
        function getPassword(){
            return $("#change-email-password").val();
        }
    }
})();