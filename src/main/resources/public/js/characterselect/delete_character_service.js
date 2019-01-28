(function DeleteCharacterService(){
    events.DELETE_CHARACTER = "delete_character";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DELETE_CHARACTER},
        deleteCharacter
    ));

    function deleteCharacter(event){
        logService.log(event.getPayload(), "info", "Character deleted with id ");
    }
})();