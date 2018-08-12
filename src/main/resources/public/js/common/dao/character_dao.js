(function CharacterDao(){
    window.characterDao = new function(){
        this.buyItems = buyItems;
        this.createCharacter = createCharacter;
        this.deleteCharacter = deleteCharacter;
        this.findByNameLike = findByNameLike;
        this.getCharacters = getCharacters;
        this.getEquipmentOfCharacter = getEquipmentOfCharacter;
        this.getMoney = getMoney;
        this.isCharNameExists = isCharNameExists;
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
            const path = "character/equipment/" + sessionStorage.characterId;
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
            
            const path = "character/";
            const content = {characterId: characterId};
            const result = dao.sendRequest("delete", path, content);
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
    Queries the server for names that contain the given name.
    Arguments:
        - name: the name to search for.
    Returns:
        - The list of users whose name contains the given name.
        - Empty list upon exception
    Throws:
        - IllegalArgument exception if name is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function findByNameLike(name){
        try{
            if(name == null || name == undefined){
                throwException("IllegalArgument", "name must not be null or undefined.");
            }
            
            const path = "character/namelike/" + name;
            const result = dao.sendRequest(dao.GET, path);
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
        IllegalArgument exception if characterId is null or undefined.
        UnknownBackendError exception if request fails.
    */
    function getEquipmentOfCharacter(characterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            const path = "character/equipment/" + characterId;
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
            
            const path = "character/ischarnameexists/" + charName;
            const result = dao.sendRequest("get", path);
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
    Arguments:
        - characterId: the id of the charcater.
    Returns:
        - The money of the character.
        - 0 If exception
    Throws:
        - IllegalArgument exception if characterId is null or undefined
        - UnknownBackendError exception if request failed.
    */
    function getMoney(characterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            
            const path = "character/money/" + characterId;
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