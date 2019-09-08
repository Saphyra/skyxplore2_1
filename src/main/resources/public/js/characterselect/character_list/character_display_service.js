(function CharacterDisplayService(){
    events.DISPLAY_CHARACTER = "display_character";
    events.CHARACTER_DELETED = "character_deleted";
    events.CHARACTER_RENAMED = "character_renamed";
    
    const ID_PREFIX = "character-";
    
    const characters = [];
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DISPLAY_CHARACTER},
        displayCharacter
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.CHARACTER_DELETED},
        removeCharacter
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.CHARACTER_RENAMED},
        updateCharacter
    ));
    
    function displayCharacter(event){
        const character = event.getPayload();
        
        hideNoCharacter();
        
        const appendMethod = getAppendMethod(character);
        
        appendMethod(createCharacterRow(character));
        
        characters.push(character);
        
        function createCharacterRow(character){
            const row = document.createElement("TR");
                row.id = getId(character.characterId);
                
                const nameCell = document.createElement("TD");
                    nameCell.innerHTML = character.characterName;
                    nameCell.classList.add("character-name-cell");
                    nameCell.title = Localization.getAdditionalContent("select-character");
                    nameCell.onclick = function(){
                        eventProcessor.processEvent(new Event(events.SELECT_CHARACTER, character.characterId));
                    }
            row.appendChild(nameCell);
            
                const operationsCell = document.createElement("TD");
                    operationsCell.classList.add("character-operations");

                    const renameButton = document.createElement("BUTTON");
                        renameButton.innerHTML = Localization.getAdditionalContent("rename-character-button");
                        renameButton.onclick = function(){
                            eventProcessor.processEvent(new Event(events.OPEN_RENAME_CHARACTER_PAGE, character));
                        }
                operationsCell.appendChild(renameButton);

                    const deleteButton = document.createElement("BUTTON");
                        deleteButton.innerHTML = Localization.getAdditionalContent("delete-character-button");
                        deleteButton.onclick = function(){
                            eventProcessor.processEvent(new Event(events.DELETE_CHARACTER, character.characterId));
                        }
                operationsCell.appendChild(deleteButton);

            row.appendChild(operationsCell);
            return row;
        }
        
        function getAppendMethod(character){
            if(!characters.length){
                return function(characterElement){
                    document.getElementById("characters").appendChild(characterElement);
                }
            }
            
            characters.sort(function(a, b){return a.characterName.localeCompare(b.characterName)});
            
            for(let cindex in characters){
                const otherCharacter = characters[cindex];
                if(character.characterName.localeCompare(otherCharacter.characterName) < 0){
                    return function(characterElement){
                        const otherElement = document.getElementById(getId(otherCharacter.characterId));
                        document.getElementById("characters").insertBefore(characterElement, otherElement);
                    }
                }
            }
            
            return function(characterElement){
                document.getElementById("characters").appendChild(characterElement);
            }
        }
    }
    
    function removeCharacter(event){
        const characterId = event.getPayload();
        document.getElementById("characters").removeChild(document.getElementById(getId(characterId)));
        const characterIndex = getCharacterIndex(characterId);
        
        if (characterIndex != null) {
          characters.splice(characterIndex, 1);
        }
        
        if(!characters.length){
            displayNoCharacter();
        }
    }
    
    function updateCharacter(event){
        const character = event.getPayload();
        const characterId = character.characterId;
        
        eventProcessor.processEvent(new Event(events.CHARACTER_DELETED, characterId));
        eventProcessor.processEvent(new Event(events.DISPLAY_CHARACTER, character));
    }
    
    function displayNoCharacter(){
        document.getElementById("no-character").style.display = "table-cell";
    }
    
    function hideNoCharacter(){
        document.getElementById("no-character").style.display = "none";
    }
    
    function getId(characterId){
        return ID_PREFIX + characterId;
    }
    
    function getCharacterIndex(characterId){
        for(let cindex in characters){
            if(characters[cindex].characterId === characterId){
                return cindex;
            }
        }
        
        return null;
    }
})();