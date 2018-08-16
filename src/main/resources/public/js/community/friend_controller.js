(function FriendController(){
    scriptLoader.loadScript("js/common/dao/community_dao.js");
    
    window.friendController = new function(){
        this.friends = [];
        this.friendRequests = [];
        this.sentFriendRequests = [];
        
        this.addFriend = addFriend;
        this.loadFriends = loadFriends;
        this.loadFriendRequests = loadFriendRequests;
        this.loadSentFriendRequests = loadSentFriendRequests;
        this.showFriendRequestNum = showFriendRequestNum;
        this.showSentFriendRequestNum = showSentFriendRequestNum;
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
            friendController.friends = orderFriends(friendList);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            friendController.friends = [];
        }
    }
    
    function loadFriendRequests(){
        try{
            const friendRequestList = communityDao.getFriendRequests(sessionStorage.characterId);
            friendController.friendRequests = orderFriends(friendRequestList);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            friendController.friendRequests = [];
        }
    }
    
    function loadSentFriendRequests(){
        try{
            const friendRequestList = communityDao.getSentFriendRequests(sessionStorage.characterId);
            friendController.sentFriendRequests = orderFriends(friendRequestList)
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            friendController.friendRequests = [];
        }
    }
    
    function showFriendRequestNum(){
        try{
            document.getElementById("friendrequestnum").innerHTML = friendController.friendRequests.length;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showSentFriendRequestNum(){
        try{
            document.getElementById("sentfriendrequestnum").innerHTML = friendController.sentFriendRequests.length;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function orderFriends(friends){
        try{
            friends.sort(function(a, b){
                return a.friendName.localeCompare(b.friendName);
            });
            return friends;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
}());