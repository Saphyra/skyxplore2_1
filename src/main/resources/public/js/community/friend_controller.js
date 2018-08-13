(function FriendController(){
    scriptLoader.loadScript("js/common/dao/friend_dao.js");
    
    window.friendController = new function(){
        this.addFriend = addFriend;
    }

    function addFriend(friendId){
        try{
            const isAdded = friendDao.addFriend(sessionStorage.characterId, friendId);
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
}());