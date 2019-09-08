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
            notificationService.showError(Localization.getAdditionalContent("empty-credentials"));
            return;
        }
        
        const request = new Request(HttpMethod.POST, Mapping.LOGIN, credentials.stringify());
            request.processValidResponse = function(){location.href = Mapping.CHARACTER_SELECT_PAGE};
            request.handleLogout = false;
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