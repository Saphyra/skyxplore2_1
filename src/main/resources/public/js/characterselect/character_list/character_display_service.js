(function CharacterDisplayService(){
    events.DISPLAY_CHARACTER = "display_character";
    
    const ID_PREFIX = "character-";
    
    const characters = [];
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DISPLAY_CHARACTER},
        displayCharacter
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
                    nameCell.title = MessageCode.getMessage("SELECT_CHARACTER");
                    nameCell.onclick = function(){
                        eventProcessor.processEvent(new Event(events.SELECT_CHARACTER, character.characterId));
                    }
            row.appendChild(nameCell);
            
                const operationsCell = document.createElement("TD");
                    operationsCell.classList.add("character-operations");
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
    
    function displayNoCharacter(){
        document.getElementById("no-character").style.display = "block";
    }
    
    function hideNoCharacter(){
        document.getElementById("no-character").style.display = "none";
    }
    
    function getId(characterId){
        return ID_PREFIX + characterId;
    }
})();