(function CharacterListController(){
    scriptLoader.loadScript("js/characterselect/character_list/character_display_service.js");
    
    events.LOAD_CHARACTERS = "load_characters";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_CHARACTERS},
        loadCharacters
    ));
    
    function loadCharacters(){
        const request = new Request(HttpMethod.GET, Mapping.GET_CHARACTERS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(characters){
                for(let cindex in characters){
                    eventProcessor.processEvent(new Event(events.DISPLAY_CHARACTER, characters[cindex]));
                }
            }
        dao.sendRequestAsync(request);
    }
})();