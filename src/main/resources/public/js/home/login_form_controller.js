(function LoginController(){
    window.loginController = new function(){
        this.sendForm = sendForm;
        
        $(document).ready(function(){
            addListeners();
        });
    }
    
    /*
        Sends the login form.
    */
    function sendForm(){
        try{
            const userNameInput = document.getElementById("login_username");
            const passwordInput = document.getElementById("login_password");
            
            const userName = userNameInput.value;
            const password = passwordInput.value;
            
            if(userName == ""){
                notificationService.showError("Adja meg felhasználónevét!");
            }else if(password == ""){
                notificationService.showError("Adja meg jelszavát!");
            }else{
                if(authService.login(userName, password)){
                    window.location.href = "/home";
                }
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function addListeners(){
        try{
            $(".logininput").on("keyup", function(e){
                if(e.which == 13){
                    loginController.sendForm();
                }
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();