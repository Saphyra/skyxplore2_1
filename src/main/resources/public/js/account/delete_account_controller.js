(function DeleteAccountController(){
    window.deleteAccountController = new function(){
        scriptLoader.loadScript("js/common/dao/user_dao.js");
        
        this.deleteAccount = deleteAccount;
        this.validateInputs = validateInputs;
        
        $(document).ready(function(){
            addListeners();
        });
    }
    
    function deleteAccount(){
        try{
            const passwordInput = document.getElementById("deleteaccountpassword");
            const password = passwordInput.value;
            
            const validationResult = validateInputs();
            if(validationResult.isValid && confirm("Biztosan törölni szeretné felhasználói fiókját?\nA törölt adatokat már nem lehet visszaállítani.")){
                const result = userDao.deleteAccount(password);
                if(result.status == ResponseStatus.OK){
                    sessionStorage.successMessage = "Account törölve.";
                    window.location.href = "/";
                }else if(result.status == ResponseStatus.UNAUTHORIZED){
                    notificationService.showError("Hibás jelszó.");
                }else{
                    throwException("UnknownServerError", result.toString());
                }
            }else{
                for(let mindex in validationResult.responses){
                    notificationService.showError(validationResult.responses[mindex]);
                }
            }
            
            passwordInput.value = "";
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function validateInputs(){
        try{
            const password = $("#deleteaccountpassword").val();
            
            const submitButton = document.getElementById("deleteaccountbutton");
            
            const invalidPassword = document.getElementById("invalid_deleteaccountpassword");
            
            const response = {
                isValid: true,
                responses: []
            };
            
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
        }
    }
    
    function addListeners(){
        try{
            $("#deleteaccountpassword").on("keyup focusin", function(e){
                const validationResult = deleteAccountController.validateInputs();
                if(e.which == 13 && validationResult.isValid){
                    deleteAccountController.deleteAccount();
                }
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();