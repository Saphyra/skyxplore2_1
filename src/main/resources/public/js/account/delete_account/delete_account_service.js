(function DeleteAccountService(){
    events.DELETE_ACCOUNT_ATTEMPT = "delete_account_attempt";
    events.BLOCK_DELETE_ACCOUNT = "block_delete_account";
    events.ALLOW_DELETE_ACCOUNT = "allow_delete_account";
    
    let accountDeletionEnabled = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ALLOW_DELETE_ACCOUNT},
        function(){
            accountDeletionEnabled = true;
            document.getElementById("delete-account-button").disabled = false;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.BLOCK_DELETE_ACCOUNT},
        function(){
            accountDeletionEnabled = false;
            document.getElementById("delete-account-button").disabled = true;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DELETE_ACCOUNT_ATTEMPT},
        deleteAccount
    ));
    
    function deleteAccount(){
        if(!accountDeletionEnabled){
            return;
        }
        
        if(!confirm(MessageCode.getMessage("CONFIRM_ACCOUNT_DELETION"))){
            $("#delete-account-password").val("");
            return;
        }
        
        const password = $("#delete-account-password").val();
        
        const request = new Request(HttpMethod.DELETE, Mapping.DELETE_ACCOUNT, {password: password});
            request.handleLogout = false;
            request.processValidResponse = function(){
                sessionStorage.successMessage = MessageCode.getMessage("ACCOUNT_DELETION_SUCCESSFUL");
                location.href = "/";
            }
            request.processInvalidResponse = function(response){
                if(response.status == ResponseStatus.UNAUTHORIZED){
                    notificationService.showError(MessageCode.getMessage("BAD_PASSWORD"));
                }else{
                    request.processErrorResponse(response);
                }
            }
            
        dao.sendRequestAsync(request);
        
        $("#delete-account-password").val("");
    }
})();