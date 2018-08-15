(function FriendController(){
    scriptLoader.loadScript("js/common/dao/community_dao.js");
    
    window.friendController = new function(){
        this.friends = null;
        this.friendRequests = null;
        
        this.addFriend = addFriend;
        this.loadFriends = loadFriends;
        this.loadFriendRequests = loadFriendRequests;
        this.loadSentFriendRequests = loadSentFriendRequests;
    }

    function addFriend(friendId){
        try{
            const isAdded = communityDao.sendFriendRequest(sessionStorage.characterId, friendId);
            if(isAdded){
                notificationService.showSuccess("Barátkérelem elküldve.");
            }else{
                notificationService.showError("Barátkérelem elküldése sikertelen.");
            }

            pageController.refresh(true, true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function loadFriends(){
        try{
            const friendList = communityDao.getFriends(sessionStorage.characterId);
            //logService.log(friendList, "info", "Friends:");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            friendController.friends = [];
        }
    }
    
    function loadFriendRequests(){
        try{
            const friendRequestList = communityDao.getFriendRequests(sessionStorage.characterId);
            logService.log(friendRequestList, "info", "FriendRequests:");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            friendController.friendRequests = [];
        }
    }
    
    function loadSentFriendRequests(){
        try{
            const friendRequestList = communityDao.getSentFriendRequests(sessionStorage.characterId);
            logService.log(friendRequestList, "info", "Sent FriendRequests:");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            friendController.friendRequests = [];
        }
    }
}());