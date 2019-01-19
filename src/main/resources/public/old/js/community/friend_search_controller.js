(function FriendSearchController(){
    scriptLoader.loadScript("js/common/dao/character_dao.js");
    scriptLoader.loadScript("js/common/dao/community_dao.js");
    
    window.friendSearchController = new function(){
        this.search = search;
    }
    
    function search(){
        try{
            const name = $("#friendname").val();
            if(name.length >= 3){
                const characters = sortUsers(communityDao.getCharactersCanBeFriend(name));
                showCharacters(characters);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function sortUsers(users){
            try{
                users.sort(function(a, b){
                    return a.characterName.localeCompare(b.characterName);
                });
                
                return users;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return []
            }
        }
        
        function showCharacters(characters){
            try{
                const container = document.getElementById("usersfoundfornewfriend");
                    container.innerHTML = "";
                    
                    if(characters.length == 0){
                        container.appendChild(createNoAvailableMessage());
                    }
                    
                    for(let cindex in characters){
                        container.appendChild(createCharacterItem(characters[cindex]));
                    }
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
            
            function createNoAvailableMessage(){
                try{
                    const div = document.createElement("DIV");
                        div.innerHTML = "Nem található karakter a megadott névvel.";
                        div.classList.add("textaligncenter");
                        div.classList.add("fontsize1_5rem");
                    return div;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                    return document.createElement("DIV");
                }
            }
            
            function createCharacterItem(character){
                try{
                    const container = document.createElement("DIV");
                        container.classList.add("maybefriend");
                        
                        const nameCell = document.createElement("DIV");
                            nameCell.innerHTML = character.characterName;
                    container.appendChild(nameCell);
                        
                        const addFriendButton = document.createElement("BUTTON");
                            addFriendButton.classList.add("addfriendbutton");
                            addFriendButton.innerHTML = "Barát felvétele";
                            addFriendButton.onclick = function(){
                                friendController.addFriend(character.characterId);
                            }
                    container.appendChild(addFriendButton);
                        
                    return container;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                    return document.createElement("DIV");
                }
            }
        }
    }
}());