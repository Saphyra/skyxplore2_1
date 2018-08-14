(function BlockUserController(){
    scriptLoader.loadScript("js/common/dao/community_dao.js");
    
    window.blockUserController = new function(){
        this.block = block;
    }
    
    function block(blockUserId){
        try{
            const isBlocked = communityDao.blockUser(sessionStorage.characterId, blockUserId);
            if(isBlocked){
                notificationService.showSuccess("Karakter blokkolva.");
            }else{
                notificationService.showError("Blokkolás sikertelen.");
            }

            pageController.refresh(true, true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();