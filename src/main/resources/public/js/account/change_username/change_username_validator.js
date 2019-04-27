(function ChangeUsernameValidator(){
    scriptLoader.loadScript("js/common/validation_util.js");
    scriptLoader.loadScript("js/account/validation_result.js");
    
    events.VALIDATE_USERNAME = "validate_username";
    
    const INVALID_USERNAME = "#invalid-new-username";
    const INVALID_CURRENT_PASSWORD = "#invalid-change-username-password";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATE_USERNAME},
        validateUsername
    ));
    
    function validateUsername(){
        const username = getUsername();
        const password = getPassword();
        
        const validationResult = new ValidationResult([INVALID_USERNAME, INVALID_CURRENT_PASSWORD]);
        
        let usernameValid = true;
        let passwordValid = true;
        
        if(password.length == 0){
            validationResult.createErrorFieldFor(INVALID_CURRENT_PASSWORD, "CURRENT_PASSWORD_IS_EMPTY");
            passwordValid = false;
        }
        
        if(username.length < 3){
            validationResult.createErrorFieldFor(INVALID_USERNAME, "USERNAME_TOO_SHORT");
            usernameValid = false;
        }else if(username.length > 30){
            validationResult.createErrorFieldFor(INVALID_USERNAME, "USERNAME_TOO_LONG");
            usernameValid = false;
        }else{
            const request = new Request(HttpMethod.POST, Mapping.USERNAME_EXISTS, {value: username});
                request.isResponseOk = function(response){
                    return response.status == ResponseStatus.OK & (response.body === "true" || response.body === "false");
                }
                request.processValidResponse = function(response){
                    if(response.body === "false"){
                        usernameValid = true;
                    }else{
                        usernameValid = false;
                        validationResult.createErrorFieldFor(INVALID_USERNAME, "USERNAME_ALREADY_EXISTS");
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
                eventProcessor.processEvent(new Event(events.ALLOW_USERNAME_CHANGE));
            }else{
                eventProcessor.processEvent(new Event(events.BLOCK_USERNAME_CHANGE));
            }
        }
        
        function isValid(){
            const areFieldsValid = usernameValid && passwordValid;
            const areFieldsTheSame = username === getUsername() && password === getPassword();
            return areFieldsValid && areFieldsTheSame;
        }
        
        function getUsername(){
            return $("#new-username").val();
        }
        
        function getPassword(){
            return $("#change-username-password").val();
        }
    }
})();