function ValidationResult(){
    const INVALID_USERNAME = "#invalid-username";
    const INVALID_PASSWORD = "#invalid-password";
    const INVALID_CONFIRM_PASSWORD = "#invalid-confirm-password";
    const INVALID_EMAIL = "#invalid-email";
    
    const userName = getUserName();
    const password = getPassword();
    const confirmPassword = getConfirmPassword();
    const email = getEmail();
    
    let userNameValid = false;
    let passwordValid = false;
    let confirmPasswordValid = false;
    let emailValid = false;
    
    let userNameValidated = false;
    let emailValidated = false;
    
    let userNameProcess = function(){$(INVALID_USERNAME).fadeOut()}
    let passwordProcess = function(){$(INVALID_PASSWORD).fadeOut()}
    let confirmPasswordProcess = function(){$(INVALID_CONFIRM_PASSWORD).fadeOut()}
    let emailProcess = function(){$(INVALID_EMAIL).fadeOut()}
    
    this.validate = validate;
    this.continueValidation = continueValidation;
    this.processValidationResult = processValidationResult;
    
    function validate(){
        validatePasswords();
        eventProcessor.processEvent(new Event(events.VALIDATION_ONGOING, this));
    }
    
    function isValid(){
        const fieldsValid = userNameValid && passwordValid && confirmPasswordValid && emailValid;
        const fieldsNotChanged = userName == getUserName() && password == getPassword() && confirmPassword == getConfirmPassword() && email == getEmail();
        return fieldsValid && fieldsNotChanged;
    }
    
    function continueValidation(payload){
        if(!userNameValidated){
            validateUserName(payload);
        }else if(!emailValidated){
            validateEmail(payload);
        }else{
            eventProcessor.processEvent(new Event(events.VALIDATION_FINISHED, this))
        }
    }
    
    function processValidationResult(){
        userNameProcess();
        passwordProcess();
        confirmPasswordProcess();
        emailProcess();
        if(isValid()){
            eventProcessor.processEvent(new Event(events.ALLOW_REGISTRATION));
        }else{
            eventProcessor.processEvent(new Event(events.BLOCK_REGISTRATION));
        }
    }
    
    function getUserName(){
        return $("#reg-username").val();
    }
    
    function getPassword(){
        return $("#reg-password").val();
    }
    
    function getConfirmPassword(){
        return $("#reg-confirm-password").val();
    }
    
    function getEmail(){
        return $("#reg-email").val();
    }

    function validatePasswords(){
        if(password.length < 6){
            passwordProcess = function(){errorProcess(INVALID_PASSWORD, "PASWORD_TOO_SHORT")};
        }else if(password.length < 6){
            passwordProcess = function(){errorProcess(INVALID_PASSWORD, "PASWORD_TOO_LONG")};
        }else if(password !== confirmPassword){
            passwordProcess = function(){errorProcess(INVALID_PASSWORD, "BAD_CONFIRM_PASSWORD")};
            confirmPasswordProcess = function(){errorProcess(INVALID_CONFIRM_PASSWORD, "BAD_CONFIRM_PASSWORD")};
        }else{
            passwordValid = true;
            confirmPasswordValid = true;
        }
    }
    
    function validateUserName(payload){
        if(userName.length < 3){
            userNameValidated = true;
            userNameProcess = function(){errorProcess(INVALID_USERNAME, "USERNAME_TOO_SHORT")};
            eventProcessor.processEvent(new Event(events.VALIDATION_ONGOING, payload));
        }else if(userName.length > 30){
            userNameValidated = true;
            userNameProcess = function(){errorProcess(INVALID_USERNAME, "USERNAME_TOO_LONG")};
            eventProcessor.processEvent(new Event(events.VALIDATION_ONGOING, payload));
        }else{
            const request = new Request(HttpMethod.POST, Mapping.USERNAME_EXISTS, {value: userName});
                request.isResponseOk = function(response){
                    return response.status == ResponseStatus.OK & (response.body === "true" || response.body === "false");
                }
                request.processValidResponse = function(response){
                    if(response.body === "false"){
                        userNameValid = true;
                    }else{
                        userNameProcess = function(){errorProcess(INVALID_USERNAME, "USERNAME_ALREADY_EXISTS")};
                    }
                    userNameValidated = true;
                    eventProcessor.processEvent(new Event(events.VALIDATION_ONGOING, payload));
                }
                
            dao.sendRequestAsync(request);
        }
    }
    
    function validateEmail(payload){
        if(!isEmailValid(email)){
            emailValidated = true;
            emailProcess = function(){errorProcess(INVALID_EMAIL, "INVALID_EMAIL")};
            eventProcessor.processEvent(new Event(events.VALIDATION_ONGOING, payload));
        }else{
            const request = new Request(HttpMethod.POST, Mapping.EMAIL_EXISTS, {value: email});
                request.isResponseOk = function(response){
                    return response.status == ResponseStatus.OK & (response.body === "true" || response.body === "false");
                }
                request.processValidResponse = function(response){
                    if(response.body === "false"){
                        emailValid = true;
                    }else{
                        emailProcess = function(){errorProcess(INVALID_EMAIL, "EMAIL_ALREADY_EXISTS")};
                    }
                    emailValidated = true;
                    eventProcessor.processEvent(new Event(events.VALIDATION_ONGOING, payload));
                }
                
            dao.sendRequestAsync(request);
        }
    }
    
    function errorProcess(id, code){
        $(id).prop("title", MessageCode.getMessage(code))
            .fadeIn();
    }
}