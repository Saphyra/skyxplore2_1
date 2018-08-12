(function FriendSearchController(){
    scriptLoader.loadScript("js/common/dao/character_dao.js")
    
    window.friendSearchController = new function(){
        this.search = search;
    }
    
    function search(){
        try{
            const name = $("#friendname").val();
            if(name.length >= 3){
                const characters = sortUsers(characterDao.findByNameLike(name));
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
                    
                    for(let cindex in characters){
                        container.appendChild(createCharacterItem(characters[cindex]));
                    }
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
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