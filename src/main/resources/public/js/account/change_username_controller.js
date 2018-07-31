(function ChangeUsernameController(){
    window.changeUserNameController = new function(){
        scriptLoader.loadScript("js/common/dao/user_dao.js");
        
        this.lastUsernameValid = false;
        this.lastUsernameQueried = null;
        
        this.changeUserName = changeUserName;
        this.validateInputs = validateInputs;
        
        $(document).ready(function(){
            addListeners();
        });
    }
    
    function changeUserName(){
        try{
            const newUserNameInput = document.getElementById("newusername");
            const passwordInput = document.getElementById("newusernamepassword");
            
            const newUserName = newUserNameInput.value;
            const password = passwordInput.value;
            
            const validationResult = validateInputs();
            if(validationResult.isValid){
                const result = userDao.changeUserName(newUserName, password);
                
                if(result.status == ResponseStatus.OK){
                    notificationService.showSuccess("Felhasználónév megváltoztatása sikeres.");
                }else if(result.status == ResponseStatus.UNAUTHORIZED){
                    notificationService.showError("Hibás jelszó.");
                }else{
                    throwException("UnknownServerError", result.toString());
                }
                
                newUserNameInput.value = "";
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
            const newUserName = $("#newusername").val();
            const password = $("#newusernamepassword").val();
            
            const submitButton = document.getElementById("newusernamebutton");
            
            const invalidNewUsername = document.getElementById("invalid_newusername");
            const invalidPassword = document.getElementById("invalid_newusernamepassword");
            
            const response = {
                isValid: true,
                responses: []
            };
            
            if(newUserName.length < 3){
                response.isValid = false;
                this.lastUsernameValid = false;
                const errorMessage = "Túl rövid felhasználónév. (Minimum 3 karakter)";
                response.responses.push(errorMessage);
                invalidNewUsername.title = errorMessage;
                $(invalidNewUsername).fadeIn();
            }else if(newUserName != this.lastUsernameQueried){
                const isUserNameExists = userDao.isUserNameExists(newUserName);
                this.lastUsernameQueried = newUserName;
                this.lastUsernameValid = !isUserNameExists;   
            }
            
            if(response.isValid && !this.lastUsernameValid){
                response.isValid = false;
                this.lastUsernameValid = false;
                const errorMessage = "Felhasználónév foglalt.";
                response.responses.push(errorMessage);
                invalidNewUsername.title = errorMessage;
                $(invalidNewUsername).fadeIn();
            }
            
            if(this.lastUsernameValid){
                $(invalidNewUsername).fadeOut();
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
    }
    
    function addListeners(){
        try{
            $("#newusername, #newusernamepassword").on("keyup focusin", function(e){
                const validationResult = changeUserNameController.validateInputs();
                if(e.which == 13 && validationResult.isValid){
                    changeUserNameController.changeUserName();
                }
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();