(function CommunityDao(){    
    window.communityDao = new function(){
        this.acceptFriendRequest = acceptFriendRequest;
        this.allowBlockedCharacter = allowBlockedCharacter;
        this.blockCharacter = blockCharacter;
        this.declineFriendRequest = declineFriendRequest;
        this.getAddressees = getAddressees;
        this.getBlockableCharacters = getBlockableCharacters;
        this.getBlockedCharacters = getBlockedCharacters;
        this.getCharactersCanBeFriend = getCharactersCanBeFriend;
        this.getFriends = getFriends;
        this.getFriendRequests = getFriendRequests;
        this.getSentFriendRequests = getSentFriendRequests;
        this.removeFriend = removeFriend;
        this.sendFriendRequest = sendFriendRequest;
    }
    
    /*
    Accepts the specified friend request.
    Arguments:
        - characterId: the id of the character
        - friendRequestId: the id of the friend request.
    Returns:
        - true, if the friend request was accepted successfully.
        - false otherwise.
    Throws:
        - IllegalArgument exception if characterId or friendRequestId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function acceptFriendRequest(characterId, friendRequestId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            if(friendRequestId == null || friendRequestId == undefined){
                throwException("IllegalArgument", "friendRequestId must not be null or undefined.");
            }
            
            const path = "friend/friendrequest/accept";
            const body = {characterId: characterId, friendRequestId: friendRequestId};
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
    Declines the friend request with the given id.
    Arguments:
        - characterId: the id of the character.
        - friendRequestId: the id of the friend request to decline.
    Returns:
        - true, if the request is declined succeú.
        - false otherwise.
    Throws:
        - IllegalArgument exception if characterId or friendRequestId is null or undefined.
    */
    function declineFriendRequest(characterId, friendRequestId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            if(friendRequestId == null || friendRequestId == undefined){
                throwException("IllegalArgument", "friendRequestId must not be null or undefined.");
            }
            const path = "friend/friendrequest/decline";
            const body = {characterId: characterId, friendRequestId: friendRequestId};
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
    Queries the possible addressees for mail.
    Arguments:
        - queryText: the name to query.
        - characterId: the id of the character.
    Returns:
        - the list of addressees.
        - empty list upon fail.
    Throws:
        - IllegalArgument exception if query is empty, null or undefined.
        - IllegalArgument exception if characterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function getAddressees(queryText, characterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            
            if(queryText == null || queryText == undefined || queryText.length == 0){
                throwException("IllegalArgument", "queryText must not be null or undefined, and must not be empty.");
            }
            
            const path = "mail/addressee/" + characterId + "/" + queryText;
            const response = dao.sendRequest(dao.GET, path);
            if(response.status == ResponseStatus.OK){
                return JSON.parse(response.response);
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
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
            
            const path = "friend/friendrequest/received/" + characterId;
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
            
            const path = "friend/friendrequest/sent/" + characterId;
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
    Sends a delete friendship request.
    Arguments:
        - characterId: the id of the character.
        - friendshipId: the id of the friendship.
    Returns:
        - true, if the friendship is successfully deleted.
        - false otherwise.
    Throws:
        - IllegalArgument exception if characterId or friendshipId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function removeFriend(characterId, friendshipId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            if(friendshipId == null || friendshipId == undefined){
                throwException("IllegalArgument", "friendshipId must not be null or undefined.");
            }
            
            const path = "friend";
            const body = {characterId: characterId, friendshipId: friendshipId};
            const response = dao.sendRequest(dao.DELETE, path, body);
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