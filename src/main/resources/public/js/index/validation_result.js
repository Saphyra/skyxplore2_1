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
    this.isValid = isValid;
    this.continueValidation = continueValidation;
    
    function validate(){
        validatePasswords();
        eventProcessor.processEvent(new Event(events.VALIDATION_ONGOING, this));
    }
    
    function isValid(){
        return userNameValid && passwordValid && confirmPasswordValid && emailValid;
    }
    
    function continueValidation(){
        if(!userNameValidated){
            validateUserName();
        }else if(!emailValidated){
            
        }else{
            eventProcessor.processEvent(new Event(events.VALIDATION_FINISHED, this))
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
            passwordProcess = function(){errorProcess(INVALID_PASSWORD, "BAD_CONFIRM_PASSRORD")};
            confirmPasswordProcess = function(){errorProcess(INVALID_CONFIRM_PASSWORD, "BAD_CONFIRM_PASSRORD")};
        }else{
            passwordValid = true;
        }
    }
    
    function validateUserName(){
        if(userName.length < 3){
            userNameValidated = true;
            userNameProcess = function(){errorProcess(INVALID_USERNAME, "USERNAME_TOO_SHORT")};
        }else if(userName.length > 30){
            userNameValidated = true;
            userNameProcess = function(){errorProcess(INVALID_USERNAME, "USERNAME_TOO_LONG")};
        }else{
            
        }
    }
    
    function errorProcess(id, code){
        $(id).prop("title", errorCode.getMessage(errorCode))
            .fadeIn();
    }
}