(function SendInvitationController(){
    events.INVITE_TO_LOBBY = "invite_to_lobby";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.INVITE_TO_LOBBY},
        function(event){inviteToLobby(event.getPayload())}
    ));

    function inviteToLobby(characterId){
        const request = new Request(HttpMethod.PUT, Mapping.concat(Mapping.INVITE_TO_LOBBY, characterId));
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("invited-to-lobby"));
            }
        dao.sendRequestAsync(request);
    }
})();