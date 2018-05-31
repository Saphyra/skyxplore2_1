(function CharacterDao(){
    window.characterDao = new function(){
        this.isCharNameExists = isCharNameExists;
        this.getCharacters = getCharacters;
        this.createCharacter = createCharacter;
    }
    
    /*
    Saves a new character with the given name.
    Arguments:
        - charName: The name of the new character.
    Returns:
        - The sent request.
    Throws:
        - IllegalArgument exception if charName is null or undefined.
    */
    function createCharacter(charName){
        try{
            if(charName == undefined){
                throwException("IllegalArgument", "charName is undefined.");
            }
            
            const path = "character/createcharacter";
            const content = {characterName: charName};
            return dao.sendRequest("put", path, content);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
    
    /*
    Queries all the characters of the user.
    Returns:
        - List of characters
    Throws:
        - UnknownBackendError exception if request failed.;
    */
    function getCharacters(){
        try{
            const path = "character/characters";
            const result = dao.sendRequest("get", path);
            if(result.status == 200){
                return JSON.parse(result.responseText);
            }else{
                throwException("UnknownBackendError", result.status + " - " + result.responseText);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
        Checks if the character name is already registered.
        Parameters:
            - charName: The character name to check.
        Returns:
            - False is the character does not exists.
            - True otherwise (even an exception).
        Throws:
            - IllegalArgument exception if charName is null or undefined.
            - UnknownBackendError exception if request failed.
    */
    function isCharNameExists(charName){
        try{
            if(charName == undefined || charName == null){
                throwException("IllegalArgument", "charName must not be null or undefined.");
            }
            
            const path = "character/ischarnameexists/" + charName;
            const result = dao.sendRequest("get", path);
            if(result.status == 200){
                if(result.responseText == "true"){
                    return true;
                }else if(result.responseText == "false"){
                    return false;
                }else{
                    throwException("UnknownBackendError", result.status + " - " + result.responseText);
                }
            }else{
                throwException("UnknownBackendError", result.status + " - " + result.responseText);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return true;
        }
    }
})();

/*(function CharacterDao(){
    window.characterDao = new function(){
        this.createCharacter = createCharacter;
        this.deleteCharacter = deleteCharacter;
        this.getCharacters = getCharacters;
        this.isCharNameExists = isCharNameExists;
        this.renameCharacter = renameCharacter;
    }
    
    
    
    function deleteCharacter(characterId){
        try{
            if(characterId == undefined || characterId == null){
                throwException("IllegalArgument", "characterId must not be null or undefined.")
            }if(typeof characterId != "number"){
                throwException("IllegalArgument", "characterId must be a number. Given: " + typeof characterId);
            }
            
            const path = "api/character/deletecharacter.php";
            const content = {characterid: characterId};
            const result = dao.sendRequest("post", path, content);
            if(result == ""){
                return true;
            }else{
                throwException("UnknownBackendError", result);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
    
    function renameCharacter(characterId, newCharacterName){
        try{
            if(characterId == undefined || characterId == null){
                throwException("IllegalArgument", "characterId must not be null or undefined.")
            }if(typeof characterId != "number"){
                throwException("IllegalArgument", "characterId must be a number. Given: " + typeof characterId);
            }if(!newCharacterName){
                throwException("IllegalArgument", "newCharacterName is undefined.");
            }
            
            const path = "api/character/renamecharacter.php";
            const content = {
                characterid: characterId,
                charactername: newCharacterName
            }
            const result = dao.sendRequest("post", path, content);
            if(result == ""){
                return true;
            }else{
                throwException("UnknownBackendError", result);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();*/