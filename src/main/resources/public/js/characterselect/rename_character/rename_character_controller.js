(function RenameCharacterController(){
    scriptLoader.loadScript("/js/characterselect/rename_character/rename_character_validator.js");
    scriptLoader.loadScript("/js/characterselect/rename_character/rename_character_service.js");
    
    events.OPEN_RENAME_CHARACTER_PAGE = "open_rename_character_page";
    events.VALIDATE_NEW_CHARACTER_NAME_ATTEMPT = "validate_new_character_name_attempt";

    let timeout;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_RENAME_CHARACTER_PAGE},
        openRenamePage
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATE_NEW_CHARACTER_NAME_ATTEMPT},
        setAttempt
    ));

    function openRenamePage(event){
        const character = event.getPayload();
        const characterId = character.characterId
        
        document.getElementById("rename-character-button").onclick = function(){
            eventProcessor.processEvent(new Event(events.RENAME_CHARACTER_ATTEMPT, characterId));
        }
        
        $("#new-character-name").val(character.characterName);
        document.getElementById("rename-character-button").disabled = true;
        
        switchTab("main-page", "rename-character-container");
    }
    
    function setAttempt(){
        eventProcessor.processEvent(new Event(events.BLOCK_RENAME_CHARACTER));
        
        if(timeout){
            clearTimeout(timeout);
        }
        timeout = setTimeout(startValidation, getValidationTimeout());
        
        function startValidation(){
            eventProcessor.processEvent(new Event(events.VALIDATE_NEW_CHARACTER_NAME));
        }
    }
    
    $(document).ready(function(){
        $("#new-character-name").on("keyup", function(e){
            if(e.which == 13){
                eventProcessor.processEvent(new Event(events.RENAME_CHARACTER_ATTEMPT));
            }else{
                eventProcessor.processEvent(new Event(events.VALIDATE_NEW_CHARACTER_NAME_ATTEMPT));
            }
        });
        $("#new-character-name").on("focusin", function(){
            eventProcessor.processEvent(new Event(events.VALIDATE_NEW_CHARACTER_NAME_ATTEMPT));
        });
    });
})();