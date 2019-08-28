(function ChangePasswordService(){
    events.CHANGE_PASSWORD_ATTEMPT = "change_password_attempt";
    events.BLOCK_PASSWORD_CHANGE = "block_password_change";
    events.ALLOW_PASSWORD_CHANGE = "allow_password_change";
    
    let passwordChangeEnabled = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ALLOW_PASSWORD_CHANGE},
        function(){
            passwordChangeEnabled = true;
            document.getElementById("change-password-button").disabled = false;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.BLOCK_PASSWORD_CHANGE},
        function(){
            passwordChangeEnabled = false;
            document.getElementById("change-password-button").disabled = true;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.CHANGE_PASSWORD_ATTEMPT},
        changePassword
    ));
    
    function changePassword(){
        if(!passwordChangeEnabled){
            return;
        }
        
        const newPassword = getNewPassword();
        const currentPassword = getCurrentPassword();
        
        const request = new Request(HttpMethod.PUT, Mapping.CHANGE_PASSWORD, {newPassword: newPassword, oldPassword: currentPassword});
            request.handleLogout = false;
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("password-changed"));
            }
        dao.sendRequestAsync(request);
        
        cleanFields();
        
        function cleanFields(){
            $("#new-password").val("");
            $("#new-password-confirmation").val("");
            $("#current-password-for-password-change").val("");
        }
    }
    
    function getNewPassword(){
        return $("#new-password").val();
    }
    
    function getConfirmPassword(){
        return $("#new-password-confirmation").val();
    }
    
    function getCurrentPassword(){
        return $("#current-password-for-password-change").val();
    }
})();