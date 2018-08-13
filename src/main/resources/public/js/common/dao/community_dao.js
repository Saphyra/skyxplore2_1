(function CommunityDao(){    
    window.communityDao = new function(){
        this.blockUser = blockUser;
        this.getBlockableCharacters = getBlockableCharacters;
        this.getCharactersCanBeFriend = getCharactersCanBeFriend;
        this.sendFriendRequest = sendFriendRequest;
    }
    
    /*
    Sends a block user request.
    Arguments:
        - characterId: the id of the character who sends the request.
        - blockedUserId: the id of the character who becomes blocked.
    Returns:
        - true, if character is successfully blocked
        - false otherwise.
    Throws:
        - IllegalArgument exception if characterId or blockedUserId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function blockUser(characterId, blockedUserId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            if(friendCharcterId == null || friendCharcterId == undefined){
                throwException("IllegalArgument", "friendCharcterId must not be null or undefined.");
            }
            
            const path = "blockcharacter/block";
            const body = {characterId: characterId, blockedUserId: blockedUserId};
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
    function sendFriendRequest(characterId, friendCharcterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            if(friendCharcterId == null || friendCharcterId == undefined){
                throwException("IllegalArgument", "friendCharcterId must not be null or undefined.");
            }
            
            const path = "friend/add";
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