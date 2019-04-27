(function ChangeUsernameService(){
    events.CHANGE_USERNAME_ATTEMPT = "change_username_attempt";
    events.BLOCK_USERNAME_CHANGE = "block_username_change";
    events.ALLOW_USERNAME_CHANGE = "allow_username_change";
    
    let userNameChangeEnabled = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ALLOW_USERNAME_CHANGE},
        function(){
            userNameChangeEnabled = true;
            document.getElementById("change-username-button").disabled = false;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.BLOCK_USERNAME_CHANGE},
        function(){
            userNameChangeEnabled = false;
            document.getElementById("change-username-button").disabled = true;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.CHANGE_USERNAME_ATTEMPT},
        changeUserName
    ));
    
    function changeUserName(){
        if(!userNameChangeEnabled){
            return;
        }
        
        const username = getUsername();
        const password = getPassword();
        
        const request = new Request(HttpMethod.PUT, Mapping.CHANGE_USERNAME, {password: password, newUserName: username});
            request.handleLogout = false;
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("USERNAME_CHANGE_SUCCESSFUL"));
            }
            request.processInvalidResponse = function(response){
                if(response.status == ResponseStatus.UNAUTHORIZED){
                    notificationService.showError(MessageCode.getMessage("BAD_PASSWORD"));
                }else{
                    request.processErrorResponse(response);
                }
            }
        dao.sendRequestAsync(request);
        
        $("#change-username-password").val("");
        
        function getUsername(){
            return $("#new-username").val();
        }
        
        function getPassword(){
            return $("#change-username-password").val();
        }
    }
})();