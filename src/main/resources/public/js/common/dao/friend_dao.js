(function FriendDao(){    
    window.friendDao = new function(){
        this.sendFriendRequest = sendFriendRequest;
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