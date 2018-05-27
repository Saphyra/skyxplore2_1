(function UserDao(){
    window.userDao = new function(){
        this.isUserNameExists = isUserNameExists;
    }
    
    function isUserNameExists(userName){
        try{
            if(userName == undefined || userName == null){
                throwException("IllegalArgument", "userName must not be null or undefined.");
            }
            
            const result = dao.sendRequest("GET", "api/isusernameexists?username=" + userName);
            if(result === "true"){
                return true;
            }else if(result === "false"){
                return false;
            }else{
                throwException("InvalidResult", result);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();