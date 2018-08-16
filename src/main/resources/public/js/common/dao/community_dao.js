(function CommunityDao(){    
    window.communityDao = new function(){
        this.allowBlockedCharacter = allowBlockedCharacter;
        this.blockCharacter = blockCharacter;
        this.getBlockableCharacters = getBlockableCharacters;
        this.getBlockedCharacters = getBlockedCharacters;
        this.getCharactersCanBeFriend = getCharactersCanBeFriend;
        this.getFriends = getFriends;
        this.getFriendRequests = getFriendRequests;
        this.getSentFriendRequests = getSentFriendRequests;
        this.sendFriendRequest = sendFriendRequest;
    }
    
    /*
    Sends an allowBlockedCharacter request.
    Arguments:
        - characterId: the id of the character.
        - blockedCharacterId: the id of the character to allow.
    Returns:
        - true, if the blocked character is successfully allowed.
        - false otherwise.
    Throws:
        - IllegalArgument exception if characterId or blockedCharacterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function allowBlockedCharacter(characterId, blockedCharacterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            if(blockedCharacterId == null || blockedCharacterId == undefined){
                throwException("IllegalArgument", "blockedCharacterId must not be null or undefined.");
            }
            
            const path = "blockedcharacter/allow";
            const body = {characterId: characterId, blockedCharacterId: blockedCharacterId};
            const response = dao.sendRequest(dao.POST, path, body);
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
    Sends a block character request.
    Arguments:
        - characterId: the id of the character who sends the request.
        - blockedCharacterId: the id of the character who becomes blocked.
    Returns:
        - true, if character is successfully blocked
        - false otherwise.
    Throws:
        - IllegalArgument exception if characterId or blockedCharacterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function blockCharacter(characterId, blockedCharacterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            if(blockedCharacterId == null || blockedCharacterId == undefined){
                throwException("IllegalArgument", "blockedCharacterId must not be null or undefined.");
            }
            
            const path = "blockcharacter/block";
            const body = {characterId: characterId, blockedCharacterId: blockedCharacterId};
            const response = dao.sendRequest(dao.POST, path, body);
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
    Queries the server for blockable users that contain the given name.
    Arguments:
        - characterId: the id of the character to query for.
        - name: the name to search for.
    Returns:
        - The list of users whose name contains the given name.
        - Empty list upon exception
    Throws:
        - IllegalArgument exception if characterId or name is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function getBlockableCharacters(characterId, name){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            if(name == null || name == undefined){
                throwException("IllegalArgument", "name must not be null or undefined.");
            }
            
            const path = "blockcharacter/" + characterId + "/namelike/" + name;
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
    Queries the character's blocked characters.
    Arguments:
        - characterId: the id of the character.
    Returns:
        - the list of blocked characters.
        - empty list upon fail.
    Throws:
        - IllegalArgument exception if characterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function getBlockedCharacters(characterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            
            const path = "blockedcharacter/" + characterId;
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
    Queries the server for possible friends that contain the given name.
    Arguments:
        - characterId: the id of the character to query for.
        - name: the name to search for.
    Returns:
        - The list of users whose name contains the given name.
        - Empty list upon exception
    Throws:
        - IllegalArgument exception if characterId or name is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function getCharactersCanBeFriend(characterId, name){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            if(name == null || name == undefined){
                throwException("IllegalArgument", "name must not be null or undefined.");
            }
            
            const path = "friend/" + characterId + "/namelike/" + name;
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
    Queries the character's friends.
    Arguments:
        - characterId: the id of the character.
    Returns:
        - the list of friends.
        - empty list upon fail.
    Throws:
        - IllegalArgument exception if characterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function getFriends(characterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            
            const path = "friend/" + characterId;
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
    Queries the character's friend requests.
    Arguments:
        - characterId: the id of the character.
    Returns:
        - the list of friend requests.
        - empty list upon fail.
    Throws:
        - IllegalArgument exception if characterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function getFriendRequests(characterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            
            const path = "friend/" + characterId + "/friendrequest/received";
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
    Queries the friend requests sent by the character.
    Arguments:
        - characterId: the id of the character.
    Returns:
        - the list of sent friend requests.
        - empty list upon fail.
    Throws:
        - IllegalArgument exception if characterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function getSentFriendRequests(characterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            
            const path = "friend/" + characterId + "/friendrequest/sent";
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
    Sends an add friend request.
    Arguments:
        - characterId: the id of the character who sends the request.
        - friendCharacterId: the id of the character who becomes friend.
    Returns:
        - true, if friend successfully added.
        - false otherwise.
    Throws:
        - IllegalArgument exception if characterId or friendCharacterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function sendFriendRequest(characterId, friendCharacterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            if(friendCharacterId == null || friendCharacterId == undefined){
                throwException("IllegalArgument", "friendCharacterId must not be null or undefined.");
            }
            
            const path = "friend/friendrequest/add";
            const body = {characterId: characterId, friendId: friendCharacterId};
            const response = dao.sendRequest(dao.POST, path, body);
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
})();