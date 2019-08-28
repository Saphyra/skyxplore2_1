(function RenameCharacterService(){
    events.RENAME_CHARACTER_ATTEMPT = "rename_character_attempt";
    events.BLOCK_RENAME_CHARACTER = "block_rename_character";
    events.ALLOW_RENAME_CHARACTER = "allow_rename_character";
    
    let characterRenameEnabled = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ALLOW_RENAME_CHARACTER},
        function(){
            characterRenameEnabled = true;
            document.getElementById("rename-character-button").disabled = false;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.BLOCK_RENAME_CHARACTER},
        function(){
            characterRenameEnabled = false;
            document.getElementById("rename-character-button").disabled = true;
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.RENAME_CHARACTER_ATTEMPT},
        renameCharacter
    ));
    
    function renameCharacter(event){
        if(!characterRenameEnabled){
            return;
        }
        
        const characterName = $("#new-character-name").val();
        const request = new Request(HttpMethod.PUT, Mapping.RENAME_CHARACTER, {newCharacterName: characterName, characterId: event.getPayload()});
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(payload){
                notificationService.showSuccess(Localization.getAdditionalContent("character-renamed"));
                eventProcessor.processEvent(new Event(events.CHARACTER_RENAMED, payload));
                switchTab("main-page", "main-container")
            }
        dao.sendRequestAsync(request);
        $("#rename-character-name").val("");
    }
})();