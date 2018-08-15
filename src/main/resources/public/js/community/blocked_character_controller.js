(function BlockCharacterController(){
    scriptLoader.loadScript("js/common/dao/community_dao.js");
    
    window.blockedCharacterController = new function(){
        this.blockedCharacters = null;
        
        this.allowBlockedCharacter = allowBlockedCharacter;
        this.block = block;
        this.displayBlockedCharacters = displayBlockedCharacters;
        this.loadBlockedCharacters = loadBlockedCharacters;
    }
    
    function allowBlockedCharacter(blockedCharacter){
        try{
            if(confirm("Biztosan visszavonod " + blockedCharacter.characterName + " blokkolását?")){
                if(communityDao.allowBlockedCharacter(sessionStorage.characterId, blockedCharacter.characterId)){
                    notificationService.showSuccess("Blokkolás feloldva.");
                }else{
                    notificationService.showError("Blokkolás feloldása sikertelen.");
                }
                
                pageController.refresh(true, false);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
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
    
    function displayBlockedCharacters(){
        try{
            if(blockedCharacterController.blockedCharacters == null){
                throwException("IllegalState", "blockedCharacters is null.");
            }
            
            const container = document.getElementById("blockedcharacterlist");
                container.innerHTML = "";
                
                if(blockedCharacterController.blockedCharacters.length == 0){
                    container.innerHTML = "Nincs blokkolt karakter.";
                }
                
                for(let cindex in blockedCharacterController.blockedCharacters){
                    container.appendChild(createBlockedCharacterItem(blockedCharacterController.blockedCharacters[cindex]));
                }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createBlockedCharacterItem(character){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("blockedcharacterlistitem");
                    
                    const characterNameElement = document.createElement("DIV");
                        characterNameElement.innerHTML = character.characterName;
                container.appendChild(characterNameElement);
                
                    const allowButton = document.createElement("BUTTON");
                        allowButton.classList.add("blockcharacterbutton");
                        allowButton.innerHTML = "Blokkolás feloldása";
                        allowButton.onclick = function(){allowBlockedCharacter(character);};
                container.appendChild(allowButton);
                    
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
        }
    }
    
    function loadBlockedCharacters(){
        try{
            const blockedCharacterList = communityDao.getBlockedCharacters(sessionStorage.characterId);
            blockedCharacterController.blockedCharacters = sortCharacters(blockedCharacterList);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            blockedCharacterController.loadBlockedCharacters = [];
        }
        
        function sortCharacters(characters){
            try{
                characters.sort(function(a, b){
                    return a.characterName.localeCompare(b.characterName);
                });
                return characters;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return [];
            }
        }
    }
})();