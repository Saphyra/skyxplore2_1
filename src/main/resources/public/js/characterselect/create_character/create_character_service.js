(function CreateCharacterService(){
    events.CREATE_CHARACTER_ATTEMPT = "create_character_attempt";
    events.BLOCK_CREATE_CHARACTER = "block_create_character";
    events.ALLOW_CREATE_CHARACTER = "allow_create_character";
    
    let characterCreationEnabled = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ALLOW_CREATE_CHARACTER},
        function(){
            characterCreationEnabled = true;
            document.getElementById("create-character-button").disabled = false;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.BLOCK_CREATE_CHARACTER},
        function(){
            characterCreationEnabled = false;
            document.getElementById("create-character-button").disabled = true;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.CREATE_CHARACTER_ATTEMPT},
        createCharacter
    ));
    
    function createCharacter(){
        if(!characterCreationEnabled){
            return;
        }
        
        const characterName = $("#create-character-name").val();
        const request = new Request(HttpMethod.POST, Mapping.CREATE_CHARACTER, {characterName: characterName});
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(payload){
                notificationService.showSuccess(Localization.getAdditionalContent("character-created"));
                eventProcessor.processEvent(new Event(events.DISPLAY_CHARACTER, payload));
            }
        dao.sendRequestAsync(request);
        $("#create-character-name").val("");
    }
})();