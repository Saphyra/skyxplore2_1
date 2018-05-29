(function AuthService(){
    window.authService = new function(){
        scriptLoader.loadScript("js/common/dao/auth_dao.js");
        
        this.login = login;
    }
    
    function login(userName, password){
        try{
            const result = authDao.login(userName, password);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();