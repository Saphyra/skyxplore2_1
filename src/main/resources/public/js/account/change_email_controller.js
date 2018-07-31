(function ChangeEmailController(){
    window.changeEmailController = new function(){
        scriptLoader.loadScript("js/common/dao/user_dao.js");
        
        this.lastEmailValid = false;
        this.lastEmailQueried = null;
        
        this.changeEmail = changeEmail;
        this.validateInputs = validateInputs;
        
        $(document).ready(function(){
           addListeners(); 
        });
    }
    
    function changeEmail(){
        try{
            const newEmailInput = document.getElementById("newemail");
            const passwordInput = document.getElementById("newemailpassword");
            
            const newEmail = newEmailInput.value;
            const password = passwordInput.value;
            
            const validationResult = validateInputs();
            
            if(validationResult.isValid){
                const result = userDao.changeEmail(newEmail, password);
                if(result.status == ResponseStatus.OK){
                    notificationService.showSuccess("E-mail cím sikeresen megváltoztatva.");
                }else if(result.status == ResponseStatus.UNAUTHORIZED){
                    notificationService.showError("Hibás jelszó.");
                }else{
                    throwException("UnknownServerError", result.toString());
                }
                
                newEmailInput.value = "";
                passwordInput.value = "";
            }else{
                for(let mindex in validationResult.responses){
                    notificationService.showError(validationResult.responses[mindex]);
                }
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function validateInputs(){
        try{
            const newEmail = $("#newemail").val();
            const password = $("#newemailpassword").val();
            
            const submitButton = document.getElementById("newemailbutton");
            
            const invalidEmail = document.getElementById("invalid_newemail");
            const invalidPassword = document.getElementById("invalid_newemailpassword");
            
            const response = {
                isValid: true,
                responses: []
            };
            
            if(!isEmailValid(newEmail)){
                response.isValid = false;
                this.lastEmailValid = false;
                const errorMessage = "Érvénytelen e-mail cím";
                response.responses.push(errorMessage);
                invalidEmail.title = errorMessage;
                $(invalidEmail).fadeIn();
            }else if(newEmail != this.lastEmailQueried){
                const isEmailExists = userDao.isEmailExists(newEmail);
                this.lastEmailQueried = newEmail;
                this.lastEmailValid = !isEmailExists;
            }
            
            if(response.isValid && !this.lastEmailValid){
                response.isValid = false;
                const errorMessage = "E-mal cím már regisztrálva van.";
                response.responses.push(errorMessage);
                invalidEmail.title = errorMessage;
                $(invalidEmail).fadeIn();
            }
            
            if(this.lastEmailValid){
                $(invalidEmail).fadeOut();
            }
            
            if(password.length == 0){
                response.isValid = false;
                const errorMessage = "Jelszó megadása kötelező!";
                response.responses.push(errorMessage);
                invalidPassword.title = errorMessage;
                $(invalidPassword).fadeIn();
            }else{
                $(invalidPassword).fadeOut();
            }
            
            submitButton.disabled = !response.isValid;
            
            return response;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return {
                isValid: false,
                responses: ["Ismeretlen hiba"]
            };
        }
        
        function isEmailValid(email){
            try{
                let result;
                if(email == null || email == undefined){
                    result = false;
                }else if(email.indexOf("@") < 1){
                    result = false;
                }else if(email.lenght < 4){
                    result = false;
                }else if(email.indexOf(".") < 0){
                    result = false;
                }else if(email.lastIndexOf(".") > email.length - 3){
                    result = false;
                }else{
                    result = true;
                }
                return result;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return false;
            }
        }
    }
    
    function addListeners(){
        try{
            $("#newemail, #newemailpassword").on("keyup focusin", function(e){
                const validationResult = changeEmailController.validateInputs();
                if(e.which == 13 && validationResult.isValid){
                    changeEmailController.changeEmail();
                }
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();