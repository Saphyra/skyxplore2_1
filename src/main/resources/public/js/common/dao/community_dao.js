(function CommunityDao(){    
    window.communityDao = new function(){
        this.acceptFriendRequest = acceptFriendRequest;
        this.archiveMails = archiveMails;
        this.allowBlockedCharacter = allowBlockedCharacter;
        this.blockCharacter = blockCharacter;
        this.declineFriendRequest = declineFriendRequest;
        this.deleteMails = deleteMails;
        this.getAddressees = getAddressees;
        this.getArchivedMails = getArchivedMails;
        this.getBlockableCharacters = getBlockableCharacters;
        this.getBlockedCharacters = getBlockedCharacters;
        this.getCharactersCanBeFriend = getCharactersCanBeFriend;
        this.getFriends = getFriends;
        this.getFriendRequests = getFriendRequests;
        this.getMails = getMails;
        this.getNumberOfFriendRequests = getNumberOfFriendRequests;
        this.getNumberOfUnreadMails = getNumberOfUnreadMails;
        this.getSentFriendRequests = getSentFriendRequests;
        this.getSentMails = getSentMails;
        this.markMailsRead = markMailsRead;
        this.markMailsUnread = markMailsUnread;
        this.removeFriend = removeFriend;
        this.sendFriendRequest = sendFriendRequest;
        this.sendMail = sendMail;
        this.unarchiveMails = unarchiveMails;
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
            
            const path = "friend/friendrequest/accept";
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
    Sets archive status of the selected mails as archived.
    Arguments:
        - mailIds: the ids of the mails.
    Returns:
        - true, if the update was successful.
        - false otherwise.
    Throws:
        - IllegalArgument exception if mailIds is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function archiveMails(mailIds){
        try{
            if(mailIds == null || mailIds == undefined){
                throwException("IllegalArgument", "mailIds must not be null or undefined.");
            }
            
            const path = "mail/archive";
            const response = dao.sendRequest(dao.POST, path, mailIds);
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
            const path = "friend/friendrequest/decline";
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
    Deletes the given mails.
    Arguments:
        - the ids of the mails to delete.
    Returns:
        - true, if deletion was successful.
        - false otherwise.
    Throws:
        - IllegalArgument exception if mailIds is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function deleteMails(mailIds){
        try{
            if(mailIds == null || mailIds == undefined){
                throwException("IllegalArgument", "mailIds must not be null or undefined.");
            }
            
            const path = "mail/delete";
            const response = dao.sendRequest(dao.DELETE, path, mailIds);
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
    function getAddressees(queryText){
        try{
            
            if(queryText == null || queryText == undefined || queryText.length == 0){
                throwException("IllegalArgument", "queryText must not be null or undefined, and must not be empty.");
            }
            
            const path = "mail/addressee";
            const body = {
                value: queryText
            }
            const response = dao.sendRequest(dao.POST, path, body);
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
    Queries the archived mails of the character.
    Returns:
        - list of archived mails.
        - empty list upon fails.
    Throws:
        - UnknownBackendError exception if request fails.
    */
    function getArchivedMails(){
        try{
            const path = "mail/archived";
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
            
            const path = "friend/friendrequest/received";
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
    Queries the character's received mails.
    Returns:
        - the mails of character
        - empty list upon fail
    Throws
        - UnknownBackendError exception when request fails.
    */
    function getMails(){
        try{
            const path = "mail";
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
    Queries the server for number of unread mails.
    Returns:
        - the number of unread mails.
        - 0 upon fail.
    Throws:
        UnknownBackendError exception if request fails.
    */
    function getNumberOfUnreadMails(){
        try{
            const path = "mail/unread";
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
            const path = "friend/friendrequest/sent";
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
    Queries the sent mails of the character.
    Returns:
        - list of mails.
        - empty list upon fail.
    Throws:
        - UnknownBackendError exception if request fails.
    */
    function getSentMails(){
        try{
            const path = "mail/sent";
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
    Marks the given mails as read.
    Arguments:
        - mailIds: the ids of the mails to mark read.
    Throws:
        - IllegalArgument exception if mailIds is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function markMailsRead(mailIds){
        try{
            if(mailIds == null || mailIds == undefined){
                throwException("IllegalArgument", "mailIds must not be null or undefined.");
            }
            const path = "mail/markread";
            const response = dao.sendRequest(dao.POST, path, mailIds);
            if(response.status != ResponseStatus.OK){
                throwException("UnknownBackendError", response.toString());
            }
            return true;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Marks the given mails as unread.
    Arguments:
        - mailIds: the ids of the mails to mark unread.
    Throws:
        - IllegalArgument exception if mailIds is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function markMailsUnread(mailIds){
        try{
            if(mailIds == null || mailIds == undefined){
                throwException("IllegalArgument", "mailIds must not be null or undefined.");
            }
            const path = "mail/markunread";
            const response = dao.sendRequest(dao.POST, path, mailIds);
            if(response.status != ResponseStatus.OK){
                throwException("UnknownBackendError", response.toString());
            }
            return true;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
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
            
            const path = "friend/friendrequest/add";
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
    
    /*
    Sends a mail.
    Arguments:
        - addresseeId: id of the recipient.
        - subject: subject of the mail.
        - message: text content of the mail.
    Returns:
        - true, if the message is successfully sent.
        - false otherwise
    Throws:
        - IllegalArgument exception if any of the arguments is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function sendMail(addresseeId, subject, message){
        try{
            const path = "mail/send";
            const body = {
                addresseeId: addresseeId,
                subject: subject,
                message: message
            };
            const response = dao.sendRequest(dao.PUT, path, body);
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
    Restores the selected mails.
    Arguments:
        - mailIds: the ids of the mails to restore.
    Returns:
        - true, if mails successfully restored.
        - false otherwise.
    Throws:
        - IllegalArgument exception if mailIds is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function unarchiveMails(mailIds){
        try{
            if(mailIds == null || mailIds == undefined){
                throwException("IllegalArgument", "mailIds must not be null or undefined.");
            }
            const path = "mail/unarchive";
            const response = dao.sendRequest(dao.POST, path, mailIds);
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