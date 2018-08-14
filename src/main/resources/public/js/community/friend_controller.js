(function FriendController(){
    scriptLoader.loadScript("js/common/dao/community_dao.js");
    
    window.friendController = new function(){
        this.friends = null;
        
        this.addFriend = addFriend;
        this.loadFriends = loadFriends;
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
            logService.log(friendList, "info", "Friends:");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            friendController.friends = [];
        }
    }
}());