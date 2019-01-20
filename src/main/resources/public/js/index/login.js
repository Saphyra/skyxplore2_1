(function Login(){
    window.events.LOGIN_ATTEMPT = "login_attempt";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOGIN_ATTEMPT},
        login
    ));
    
    function login(){
        const userName = $("#login-username").val();
        const passwordInput = $("#login-password");
        const password = $(passwordInput).val();
        $(passwordInput).val("");
        
        if(userName == "" || password == ""){
            notificationService.showError(ErrorCode.getMessage("EMPTY_CREDENTIALS"));
            return;
        }
        
        const request = new Request(dao.POST, "login", {userName: userName, password: password});
            request.processValidResponse = function(){location.href = "characterselect"};
            request.processInvalidResponse = function(response){
                if(response.status == ResponseStatus.UNAUTHORIZED){
                    notificationService.showError(ErrorCode.getMessage("BAD_CREDENTIALS"));
                }else{
                    logService.log(response.toString(), "warn", "Invalid response from BackEnd: ")
                }
            }
            
        dao.sendRequestAsync(request);
    }
})();