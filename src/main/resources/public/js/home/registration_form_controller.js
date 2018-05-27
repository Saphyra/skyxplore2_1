(function RegistrationController(){
    window.registrationController = new function(){
        scriptLoader.loadScript("js/common/dao/user_dao.js");
        
        this.validate = validate;
        this.sendForm = sendForm;
        $(document).ready(function(){
            addListeners();
        });
    }
    function sendForm(){
        try{
            const userNameInput = document.getElementById("registration_username");
            const passwordInput = document.getElementById("registration_password");
            const confirmPasswordInput = document.getElementById("registration_confirm_password");
            const emailInput = document.getElementById("registration_email");
            
            const userName = userNameInput.value;
            const password = passwordInput.value;
            const confirmPassword = confirmPasswordInput.value;
            const email = emailInput.value;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function validate(){
        try{
            const isUsernameValid = validateUserName();
            const arePasswordsValid = validatePasswords();
            const isEmailValid = validateEmail();
            
            if(isUsernameValid && arePasswordsValid && isEmailValid){
                document.getElementById("registration_button").disabled = false;
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function validateUserName(){
            try{
                const userName = document.getElementById("registration_username").value;
                const errorElementName = "#invalid_username";
                
                if(userName.length < 3){
                    activateErrorElement(errorElementName, "Felhasználónév túl rövid (Minimum 3 karakter).");
                    return false;
                }else if(userDao.isUserNameExists(userName)){
                    activateErrorElement(errorElementName, "Felhasználónév foglalt.");
                    return false;
                }else{
                    deactivateErrorElement(errorElementName);
                    return true;
                }
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function validatePasswords(){
            try{
                
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function validateEmail(){
            try{
                
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function activateErrorElement(elementName, message){
            try{
                $(elementName).attr("title", message).fadeIn();
                document.getElementById("registration_button").disabled = true;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function deactivateErrorElement(elementName){
            try{
                $(elementName).fadeOut();
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }
    
    function addListeners(){
        try{
            $(".registrationinput").on("keyup focusin", function(){registrationController.validate()});
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();