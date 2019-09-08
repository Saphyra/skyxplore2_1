(function DeleteCharacterService(){
    events.DELETE_CHARACTER = "delete_character";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DELETE_CHARACTER},
        deleteCharacter
    ));

    function deleteCharacter(event){
        if(confirm(Localization.getAdditionalContent("confirm-character-deletion"))){
            const request = new Request(HttpMethod.DELETE, Mapping.concat(Mapping.DELETE_CHARACTER, event.getPayload()));
                request.processValidResponse = function(){
                    notificationService.showSuccess(Localization.getAdditionalContent("character-deleted"));
                    eventProcessor.processEvent(new Event(events.CHARACTER_DELETED, event.getPayload()));
                }
            dao.sendRequestAsync(request);
        }
    }
})();