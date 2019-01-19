(function CommunityDao(){    
    window.communityDao = new function(){
        this.acceptFriendRequest = acceptFriendRequest;
        this.allowBlockedCharacter = allowBlockedCharacter;
        this.blockCharacter = blockCharacter;
        this.declineFriendRequest = declineFriendRequest;
        this.getBlockableCharacters = getBlockableCharacters;
        this.getBlockedCharacters = getBlockedCharacters;
        this.getCharactersCanBeFriend = getCharactersCanBeFriend;
        this.getFriends = getFriends;
        this.getFriendRequests = getFriendRequests;
        this.getNumberOfFriendRequests = getNumberOfFriendRequests;
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
    function acceptFriendRequest(friendRequestId){
        try{
            if(friendRequestId == null || friendRequestId == undefined){
                throwException("IllegalArgument", "friendRequestId must not be null or undefined.");
            }
            
            const path = "friend/request/accept";
            const body = {
                value: friendRequestId
            };
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
        - blockedCharacterId: the id of the character to allow.
    Returns:
        - true, if the blocked character is successfully allowed.
        - false otherwise.
    Throws:
        - IllegalArgument exception if blockedCharacterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function allowBlockedCharacter(blockedCharacterId){
        try{
            if(blockedCharacterId == null || blockedCharacterId == undefined){
                throwException("IllegalArgument", "blockedCharacterId must not be null or undefined.");
            }
            
            const path = "blockedcharacter/allow";
            const body = {
                value: blockedCharacterId
            };
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
        - blockedCharacterId: the id of the character who becomes blocked.
    Returns:
        - true, if character is successfully blocked
        - false otherwise.
    Throws:
        - IllegalArgument exception if characterId or blockedCharacterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function blockCharacter(blockedCharacterId){
        try{
            if(blockedCharacterId == null || blockedCharacterId == undefined){
                throwException("IllegalArgument", "blockedCharacterId must not be null or undefined.");
            }
            
            const path = "blockcharacter/block";
            const body = {
                value: blockedCharacterId
            };
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
        - friendRequestId: the id of the friend request to decline.
    Returns:
        - true, if the request is declined succeú.
        - false otherwise.
    Throws:
        - IllegalArgument exception if friendRequestId is null or undefined.
    */
    function declineFriendRequest(friendRequestId){
        try{
            if(friendRequestId == null || friendRequestId == undefined){
                throwException("IllegalArgument", "friendRequestId must not be null or undefined.");
            }
            const path = "friend/request/decline";
            const body = {
                value: friendRequestId
            };
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
    function getBlockableCharacters(name){
        try{
            if(name == null || name == undefined){
                throwException("IllegalArgument", "name must not be null or undefined.");
            }
            
            const path = "blockcharacter/namelike";
            const body = {
                value: name
            }
            const result = dao.sendRequest(dao.POST, path, body);
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
    function getBlockedCharacters(){
        try{
            
            const path = "blockedcharacter";
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
        - name: the name to search for.
    Returns:
        - The list of users whose name contains the given name.
        - Empty list upon exception
    Throws:
        - IllegalArgument exception if name is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function getCharactersCanBeFriend(name){
        try{
            if(name == null || name == undefined){
                throwException("IllegalArgument", "name must not be null or undefined.");
            }
            
            const path = "friend/namelike";
            const body = {
                value: name
            }
            const result = dao.sendRequest(dao.POST, path, body);
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
    Returns:
        - the list of friends.
        - empty list upon fail.
    Throws:
        - UnknownBackendError exception if request fails.
    */
    function getFriends(){
        try{
            const path = "friend";
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
    Returns:
        - the list of friend requests.
        - empty list upon fail.
    Throws:
        - UnknownBackendError exception if request fails.
    */
    function getFriendRequests(){
        try{
            
            const path = "friend/request/received";
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
    Queries the server for the number of received friend requests.
    Returns:
        - the number of friend requests.
        - 0 upon fail.
    Throws:
        - UnknownBackendError exception if request fails.
    */
    function getNumberOfFriendRequests(){
        try{
            const path = "friend/request/num";
            const response = dao.sendRequest(dao.GET, path);
            if(response.status == ResponseStatus.OK){
                return Number(response.response);
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return 0;
        }
    }
    
    /*
    Queries the friend requests sent by the character.
    Returns:
        - the list of sent friend requests.
        - empty list upon fail.
    Throws:
        - UnknownBackendError exception if request fails.
    */
    function getSentFriendRequests(){
        try{
            const path = "friend/request/sent";
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
        - friendshipId: the id of the friendship.
    Returns:
        - true, if the friendship is successfully deleted.
        - false otherwise.
    Throws:
        - UnknownBackendError exception if request fails.
    */
    function removeFriend(friendshipId){
        try{
            if(friendshipId == null || friendshipId == undefined){
                throwException("IllegalArgument", "friendshipId must not be null or undefined.");
            }
            
            const path = "friend";
            const body = {
                value: friendshipId
            };
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
    function sendFriendRequest(friendCharacterId){
        try{
            if(friendCharacterId == null || friendCharacterId == undefined){
                throwException("IllegalArgument", "friendCharacterId must not be null or undefined.");
            }
            
            const path = "friend/request/add";
            const body = {
                value: friendCharacterId
            };
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