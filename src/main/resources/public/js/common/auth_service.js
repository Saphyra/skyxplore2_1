(function AuthService(){
    window.authService = new function(){
        scriptLoader.loadScript("js/common/dao/auth_dao.js");
        
        this.login = login;
        this.logout = logout;
    }
    
    /*
        Sends an authentication request.
        Arguments:
            - userName: The userName of the user.
            - password: The password of the user.
        Returns:
            - True if authentication was successful.
            - False otherwise.
        Throws:
            - IllegalState exception upon bad result from dao.
            - UnhandledServer exception upon unknown failure.
    */
    function login(userName, password){
        try{
            const result = authDao.login(userName, password);
            if(!result){
                throwException("IllegalState", "undefined result from dao.");
            }
            if(result.status == 401){
                return false;
            }else if(result.status == 200){
                return true;
            }else{
                throwException("UnhandledServer", result.status + " - " + result.responseText);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
    
    /*
    Sends a log out request, and redirects to index page.
    Throws:
        - IllegalState exception upon bad result from dao.
        - UnhandledServer upon unknown failure.
    */
    function logout(){
        try{
            const result = authDao.logout();
            if(!result){
                throwException("IllegalState", "undefined result from dao.");
            }
            if(result.status == 200){
                sessionStorage.successMessage = "Sikeres kijelentkezés!";
                window.location.href = "/";
            }else{
                throwException("UnhandledServer", result.status + " - " + result.responseText);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();