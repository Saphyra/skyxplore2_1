(function UserDao(){
    window.userDao = new function(){
        this.isEmailExists = isEmailExists;
        this.isUserNameExists = isUserNameExists;
        this.registrateUser = registrateUser;
    }
    
    function isEmailExists(email){
        try{
            if(email == undefined || email == null){
                throwException("IllegalArgument", "email must not be null or undefined.");
            }
            
            const result = dao.sendRequest("GET", "isemailexists?email=" + email);
            if(result.responseText === "true"){
                return true;
            }else if(result.responseText === "false"){
                return false;
            }else{
                throwException("InvalidResult", result);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return true;
        }
    }
    
    function isUserNameExists(userName){
        try{
            if(userName == undefined || userName == null){
                throwException("IllegalArgument", "userName must not be null or undefined.");
            }
            
            const result = dao.sendRequest("GET", "isusernameexists?username=" + userName);
            if(result.responseText === "true"){
                return true;
            }else if(result.responseText === "false"){
                return false;
            }else{
                throwException("InvalidResult", result);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return true;
        }
    }
    
    function registrateUser(user){
        try{
            if(user == null && undefined){
                throwException("IllegalArgument", "user must not be null or undefined");
            }
            const result =  dao.sendRequest("POST", "registration", user);
            if(result.status == 200){
                return true;
            }else{
                throwException("UnknownServerError", result.status + " - " + result.responseText);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();