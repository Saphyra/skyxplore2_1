(function BlockCharacterController(){
    scriptLoader.loadScript("js/common/dao/community_dao.js");
    
    window.blockedCharacterController = new function(){
        this.blockedCharacters = null;
        
        this.block = block;
        this.loadBlockedCharacters = loadBlockedCharacters;
    }
    
    function block(blockedCharacterId){
        try{
            const isBlocked = communityDao.blockCharacter(sessionStorage.characterId, blockedCharacterId);
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
    
    function loadBlockedCharacters(){
        try{
            const blockedCharacterList = communityDao.getBlockedCharacters(sessionStorage.characterId);
            logService.log(blockedCharacterList, "info", "Blocked characters: ");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            blockedCharacterController.blockedUsers = [];
        }
    }
})();