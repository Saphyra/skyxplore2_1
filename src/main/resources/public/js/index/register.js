(function Register(){
    events.REGISTER_ATTEMPT = "register_attempt";
    events.BLOCK_REGISTRATION = "block_registration";
    events.ALLOW_REGISTRATION = "allow_registration";
    
    let registrationAllowed = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.REGISTER_ATTEMPT},
        register
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.BLOCK_REGISTRATION},
        blockRegistration
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ALLOW_REGISTRATION},
        allowRegistration
    ));
    
    function blockRegistration(){
        registrationAllowed = false;
        document.getElementById("registration-button").disabled = true;
    }
    
    function allowRegistration(){
        registrationAllowed = true;
        document.getElementById("registration-button").disabled = false;
    }
    
    function register(){
        if(!registrationAllowed){
            return;
        }
        
        const userName = getUserName();
        const password = getPassword();
        const confirmPassword = getConfirmPassword();
        const email = getEmail();
        
        const user = {
            username: userName,
            password: password,
            confirmPassword: confirmPassword,
            email: email
        };
        
        const request = new Request(HttpMethod.POST, Mapping.REGISTER, user);
            request.processValidResponse = function(){
                sessionStorage.successMessage = MessageCode.getMessage("REGISTRATION_SUCCESSFUL");
                eventProcessor.processEvent(new Event(events.LOGIN_ATTEMPT, {userName: userName, password: password}));
            }
            request.processInvalidResponse = function(){
                notificationService.showError(MessageCode.getMessage("REGISTRATION_FAILED"));
            }
            
        dao.sendRequestAsync(request);
        
        function getUserName(){
            return $("#reg-username").val();
        }
        
        function getPassword(){
            return $("#reg-password").val();
        }
        
        function getConfirmPassword(){
            return $("#reg-confirm-password").val();
        }
        
        function getEmail(){
            return $("#reg-email").val();
        }
    }
})();