(function BlockedCharacterSearchController(){
    window.blockedCharacterSearchController = new function(){
        this.search = search;
    }
    
    function search(){
        try{
            const name = $("#blockcharactername").val();
            if(name.length >= 3){
                const characters = sortCharacters(communityDao.getBlockableCharacters(sessionStorage.characterId, name));
                showCharacters(characters);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");           
        }
        
        function sortCharacters(users){
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
                const container = document.getElementById("blockablecharactersfound");
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
                        container.classList.add("blockablecharacter");
                        
                        const nameCell = document.createElement("DIV");
                            nameCell.innerHTML = character.characterName;
                    container.appendChild(nameCell);
                        
                        const addFriendButton = document.createElement("BUTTON");
                            addFriendButton.classList.add("blockcharacterbutton");
                            addFriendButton.innerHTML = "Blokkolás";
                            addFriendButton.onclick = function(){
                                blockedCharacterController.block(character.characterId);
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
})();