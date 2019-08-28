(function ChangeEmailService(){
    events.CHANGE_EMAIL_ATTEMPT = "change_email_attempt";
    events.BLOCK_EMAIL_CHANGE = "block_email_change";
    events.ALLOW_EMAIL_CHANGE = "allow_email_change";
    
    let emailChangeEnabled = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ALLOW_EMAIL_CHANGE},
        function(){
            emailChangeEnabled = true;
            document.getElementById("change-email-button").disabled = false;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.BLOCK_EMAIL_CHANGE},
        function(){
            emailChangeEnabled = false;
            document.getElementById("change-email-button").disabled = true;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.CHANGE_EMAIL_ATTEMPT},
        changeEmail
    ));
    
    function changeEmail(){
        if(!emailChangeEnabled){
            return;
        }
        
        const email = getEmail();
        const password = getPassword();
        
        const request = new Request(HttpMethod.PUT, Mapping.CHANGE_EMAIL, {password: password, newEmail: email});
            request.handleLogout = false;
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("email-changed"));
            }
        dao.sendRequestAsync(request);
        
        $("#change-email-password").val("");
        
        function getEmail(){
            return $("#new-email").val();
        }
        
        function getPassword(){
            return $("#change-email-password").val();
        }
    }
})();