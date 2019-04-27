(function LogoutService(){
    eventProcessor.registerProcessor(new EventProcessor(
            function(eventType){return eventType === events.LOGOUT},
            logout
        ));
    
    function logout(){
        const request = new Request(HttpMethod.POST, Mapping.LOGOUT);
            request.processValidResponse = function(){
                sessionStorage.successMessage = MessageCode.getMessage("SUCCESSFUL_LOGOUT");
                location.href = "/";
            }
            
        dao.sendRequestAsync(request);
    }
})();