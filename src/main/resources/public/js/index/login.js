(function Login(){
    window.events.LOGIN_ATTEMPT = "login_attempt";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOGIN_ATTEMPT},
        login
    ));
    
    function login(event){
        const credentials = new Credentials(event.getPayload());
        $("#login-password").val("")
        
        if(!credentials.isValid()){
            notificationService.showError(MessageCode.getMessage("EMPTY_CREDENTIALS"));
            return;
        }
        
        const request = new Request(HttpMethod.POST, Mapping.LOGIN, credentials.stringify());
            request.processValidResponse = function(){location.href = Mapping.CHARACTER_SELECT};
            request.processInvalidResponse = function(response){
                if(response.status == ResponseStatus.UNAUTHORIZED){
                    notificationService.showError(MessageCode.getMessage("BAD_CREDENTIALS"));
                }else{
                    request.processErrorResponse(response);
                }
            }
            
        dao.sendRequestAsync(request);
    }
    
    function Credentials(payload){
        const userName = payload ? payload.userName : $("#login-username").val();
        const password = payload ? payload.password : $("#login-password").val();
        
        this.isValid = function(){
            return userName !== "" && password !== "";
        }
        
        this.stringify = function(){
            return {userName: userName, password: password};
        }
    }
})();