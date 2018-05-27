(function LoginController(){
    window.loginController = new function(){
        this.sendForm = sendForm;
    }
    
    function sendForm(){
        try{
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();