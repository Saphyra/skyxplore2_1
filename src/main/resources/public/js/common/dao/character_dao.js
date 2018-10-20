(function CharacterDao(){
    window.characterDao = new function(){
        this.buyItems = buyItems;
        this.createCharacter = createCharacter;
        this.deleteCharacter = deleteCharacter;
        this.getCharacters = getCharacters;
        this.getEquipmentOfCharacter = getEquipmentOfCharacter;
        this.getMoney = getMoney;
        this.isCharNameExists = isCharNameExists;
        this.selectCharacter = selectCharacter;
        this.renameCharacter = renameCharacter;
    }
    
    /*
    Buys the selected items.
    Arguments:
        - items: the type and amount of items to buy.
    Returns:
        - Response object represents the result of the request.
    Throws:
        - IllegalArgument exception if items is null or undefined.
    */
    function buyItems(items){
        try{
            if(items == null || items == undefined){
                throwException("IllegalArgument", "items must not be null or undefined.");
            }
            const path = "character/equipment";
            const result = dao.sendRequest(dao.PUT, path, items);
            return result;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return new Response();
        }
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
            
            const path = "character";
            const content = {characterName: charName};
            return dao.sendRequest("put", path, content);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return new Response();
        }
    }
    
    /*
    Deletes the character with the given id.
    Arguments:
        - characterId: The id of the character to delete.
    Returns:
        - True if the deletion was successful.
        - False otherwise.
    Throws:
        - IllegalArgument exception if characterId is null, undefined or not a string.
        - UnknownBackendError exception if request failed.
    */
    function deleteCharacter(characterId){
        try{
            if(characterId == undefined || characterId == null){
                throwException("IllegalArgument", "characterId must not be null or undefined.")
            }if(typeof characterId != "string"){
                throwException("IllegalArgument", "characterId must be a number. Given: " + typeof characterId);
            }
            
            const path = "character/delete/" + characterId;
            const result = dao.sendRequest("delete", path);
            if(result.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", result.toString());
            }
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
        - UnknownBackendError exception if request failed.
    */
    function getCharacters(){
        try{
            const path = "character/characters";
            const result = dao.sendRequest("get", path);
            if(result.status == ResponseStatus.OK){
                return JSON.parse(result.response);
            }else{
                throwException("UnknownBackendError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    Queries the equipments of the given character.
    Throws:
        UnknownBackendError exception if request fails.
    */
    function getEquipmentOfCharacter(){
        try{
            const path = "character/equipment";
            const result = dao.sendRequest(dao.GET, path);
            if(result.status == ResponseStatus.OK){
                const elements = JSON.parse(result.response);
                
                cache.addAll(elements.data);
                return elements.info;
            }else{
                throwException("UnknownBackendError", result.toString());
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
            - UnknownResponse exception if response cannot be parsed
    */
    function isCharNameExists(charName){
        try{
            if(charName == undefined || charName == null){
                throwException("IllegalArgument", "charName must not be null or undefined.");
            }
            
            const path = "character/name/exist";
            const body = {
                value: charName
            }
            const result = dao.sendRequest(dao.POST, path, body);
            if(result.status == ResponseStatus.OK){
                if(result.response == "true"){
                    return true;
                }else if(result.response == "false"){
                    return false;
                }else{
                    throwException("UnknownResponse", result.toString());
                }
            }else{
                throwException("UnknownBackendError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return true;
        }
    }
    
    /*
    Returns the money of the character.
    Returns:
        - The money of the character.
        - 0 If exception
    Throws:
        - UnknownBackendError exception if request failed.
    */
    function getMoney(){
        try{
            const path = "character/money";
            const result = dao.sendRequest(dao.GET, path);
            if(result.status == ResponseStatus.OK){
                return Number(result.response);
            }else{
                throwException("UnknownBackendError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return 0;
        }
    }

    /*
    Notificates the server about the character selection.
    Arguments:
        - characterId: the id of the selected character.
    Returns:
        - true, if the selection was successful.
        - false otherwise.
    Throws:
        - IllegalArgument exception if characterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function selectCharacter(characterId){
        try{
            if(characterId == undefined || characterId == null){
                throwException("IllegalArgument", "characterId must not be null or undefined.")
            }
            
            const path = "character/select/" + characterId;
            const response = dao.sendRequest(dao.POST, path);
            if(response.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }

    /*
    Sends a rename character request.
    Arguments:
        - characterId: The id of the character to rename.
        - newCharacterName: The new name of the character.
    Returns:
        - True, if the character is renamed successfully.
        - False otherwise.
    Throws:
        - IllegalArgument exception if characterId/newCharacterName is null or undefined.
        - UnknownBackendError exception if request failed.
    */
    function renameCharacter(characterId, newCharacterName){
        try{
            if(characterId == undefined || characterId == null){
                throwException("IllegalArgument", "characterId must not be null or undefined.")
            }
            if(!newCharacterName){
                throwException("IllegalArgument", "newCharacterName is undefined.");
            }
            
            const path = "character/rename";
            const content = {
                characterId: characterId,
                newCharacterName: newCharacterName
            }
            const result = dao.sendRequest("post", path, content);
            if(result.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();