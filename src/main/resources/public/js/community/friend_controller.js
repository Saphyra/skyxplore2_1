(function FriendController(){
    window.friendController = new function(){
        this.addFriend = addFriend;
    }

    function addFriend(friendId){
        try{
            const isAdded = characterDao.addFriend(sessionStorage.characterId, friendId);
            if(isAdded){
                notificationService.showSuccess("Barát hozzáadva.");
            }else{
                notificationService.showError("Barát hozzáadása sikertelen.");
            }

            pageController.refresh(true, true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
}());