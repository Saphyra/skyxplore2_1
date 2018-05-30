(function AuthService(){
    window.authService = new function(){
        scriptLoader.loadScript("js/common/dao/auth_dao.js");
        
        this.login = login;
        this.logout = logout;
    }
    
    //TODO documentation
    function login(userName, password){
        try{
            const result = authDao.login(userName, password);
            if(result.status == 401){
                sessionStorage.errorMessage = "Érvénytelen felhasználónév vagy jelszó!";
                window.location.href = "/";
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
    
    //TODO documentation
    function logout(){
        //TODO implement
    }
})();