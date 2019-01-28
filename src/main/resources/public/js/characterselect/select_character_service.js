(function SelectCharacter(){
    events.SELECT_CHARACTER = "select_character";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.SELECT_CHARACTER},
        selectCharacter
    ));
    
    function selectCharacter(event){
        const characterId = event.getPayload();
        
        const request = new Request(HttpMethod.PUT, Mapping.concat(Mapping.SELECT_CHARACTER, characterId));
            request.processValidResponse = function(){
                window.location.href = "overview";
            }
        dao.sendRequestAsync(request);
    }
})();