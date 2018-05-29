(function AuthDao(){
    window.authDao = new function(){
        this.login = login;
    }
    
    /*
    Requests the accessToken from the server.
    Parameters:
        - userName: The user name of the user.
        - password: The password of the user.
    Returns:
        - true if login successful.
        - false otherwise.
    Throws:
        - IllegalArgument exception if userName or password is null of undefined.
    */
    function login(userName, password){
        try{
            if(userName == null || userName == undefined){
                throwException("IllegalArgument", "userName must not be null or undefined");
            }
            if(password == null || password == undefined){
                throwException("IllegalArgument", "password must not be null or undefined");
            }
            
            const result = dao.sendRequest("POST", "login", {userName: userName, password: password});
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();