(function CharacterDao(){
    window.characterDao = new function(){
        //TODO implement
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
    
    function createCharacter(charName){
        try{
            if(charName == undefined){
                throwException("IllegalArgument", "charName is undefined.");
            }
            
            const path = "api/character/createcharacter.php";
            const content = {charname: charName};
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
    
    function getCharacters(){
        try{
            const path = "api/character/getcharacters.php";
            const result = dao.sendRequest("get", path);
            return characterConverter.convertCharacters(JSON.parse(result));
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    function isCharNameExists(charName){
        try{
            if(charName == undefined){
                throwException("IllegalArgument", "charName is undefined.");
            }
            
            const path = "api/character/ischarnameexists.php?charname=" + charName;
            const result = dao.sendRequest("get", path);
            if(result == "true"){
                return true;
            }else if(result == "false"){
                return false;
            }else{
                throwException("UnknownBackendError", result);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return true;
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