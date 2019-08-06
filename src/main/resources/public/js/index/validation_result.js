scriptLoader.loadScript("/js/common/validation_util.js");

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
    
    let userNameProcess = createSuccessProcess(INVALID_USERNAME);
    let passwordProcess = createSuccessProcess(INVALID_PASSWORD);
    let confirmPasswordProcess = createSuccessProcess(INVALID_CONFIRM_PASSWORD);
    let emailProcess = createSuccessProcess(INVALID_EMAIL);
    
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
            passwordProcess = createErrorProcess(INVALID_PASSWORD, "PASSWORD_TOO_SHORT");
        }else if(password.length > 30){
            passwordProcess = createErrorProcess(INVALID_PASSWORD, "PASSWORD_TOO_LONG");
        }else if(password !== confirmPassword){
            passwordProcess = createErrorProcess(INVALID_PASSWORD, "BAD_CONFIRM_PASSWORD");
            confirmPasswordProcess = createErrorProcess(INVALID_CONFIRM_PASSWORD, "BAD_CONFIRM_PASSWORD");
        }else{
            passwordValid = true;
            confirmPasswordValid = true;
        }
    }
    
    function validateUserName(payload){
        if(userName.length < 3){
            userNameValidated = true;
            userNameProcess = createErrorProcess(INVALID_USERNAME, "USERNAME_TOO_SHORT");
            eventProcessor.processEvent(new Event(events.VALIDATION_ONGOING, payload));
        }else if(userName.length > 30){
            userNameValidated = true;
            userNameProcess = createErrorProcess(INVALID_USERNAME, "USERNAME_TOO_LONG");
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
                        userNameProcess = createErrorProcess(INVALID_USERNAME, "USERNAME_ALREADY_EXISTS");
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
            emailProcess = createErrorProcess(INVALID_EMAIL, "INVALID_EMAIL");
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
                        emailProcess = createErrorProcess(INVALID_EMAIL, "EMAIL_ALREADY_EXISTS");
                    }
                    emailValidated = true;
                    eventProcessor.processEvent(new Event(events.VALIDATION_ONGOING, payload));
                }
                
            dao.sendRequestAsync(request);
        }
    }
}