(function CharacterController(){
    window.characterController = new function(){
        scriptLoader.loadScript("js/common/dao/character_dao.js");
        
        this.createCharacter = createCharacter;
        this.deleteCharacter = deleteCharacter;
        this.renameCharacter = renameCharacter;
        this.showCharacters = showCharacters;
        this.validateNewCharacterName = validateNewCharacterName;
    }
    
    function createCharacter(){
        try{
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function deleteCharacter(){
        try{
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function renameCharacter(){
        try{
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showCharacters(){
        try{
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function validateNewCharacterName(){
        try{
            const errorField = document.getElementById("invalid_newcharactername");
            const characterName = document.getElementById("newcharactername").value;
            let isValid = characterName.length >= 3;
            if(isValid){
                notificationService.showError(isValid);
                if(isValid){
                    errorField.style.display = "none";
                }else{
                    errorField.style.display = "block";
                    errorField.title = "Karakternév foglalt.";
                }
            }else{
                errorField.style.display = "block";
                errorField.title = "Karakternév túl rövid. (Minimum 3 karakter)";
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();

/*(function CharacterController(){
    window.characterController = new function(){
        
    }
    
    function createCharacter(){
        try{
            const charNameInput = document.getElementById("newcharactername");
            const charName = charNameInput.value;
            
            if(charName == ""){
                notificationService.showError("A karakternév megadása kötelező!");
            }else if(characterDao.isCharNameExists(charName)){
                notificationService.showError("Karakternév foglalt.");
            }else if(characterDao.createCharacter(charName)){
                notificationService.showSuccess("Karakter létrehozva.");
                charNameInput.value = "";
                pageController.refresh();
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function deleteCharacter(characterId){
        try{
            if(confirm("Biztosan törlölni szeretné a karaktert?")){
                if(characterDao.deleteCharacter(characterId)){
                    notificationService.showSuccess("Karakter törölve.");
                }else{
                    notificationService.showError("Törlés sikertelen.");
                }
                pageController.refresh();
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function renameCharacter(){
        try{
            const newCharacterName = document.getElementById("renamecharacterinput").value;
            const characterId = Number(document.getElementById("renamecharacterid").value);
            
            if(newCharacterName == ""){
                notificationService.showError("Adja meg a karakter új nevét!");
            }else if(characterDao.isCharNameExists(newCharacterName)){
                notificationService.showError("Karakternév foglalt.");
            }else if(characterDao.renameCharacter(characterId, newCharacterName)){
                notificationService.showSuccess("Karakter átnevezve.");
                pageController.refresh();
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showCharacters(){
        try{
            const characters = characterDao.getCharacters();
            const container = document.getElementById("characters");
                container.innerHTML = "";
                
                if(characters.length){
                    for(let cindex in characters){
                        container.appendChild(displayCharacter(characters[cindex]));
                    }
                }else{
                    container.appendChild(displayNoCharacters());
                }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function displayCharacter(character){
            try{
                const container = document.createElement("TR");
                
                    const nameCell = document.createElement("TD");
                        nameCell.innerHTML = character.characterName;
                        nameCell.classList.add("cursorpointer");
                        nameCell.classList.add("fontsize1_25rem");
                        nameCell.classList.add("textaligncenter");
                        nameCell.title = "Játék indítása";
                        nameCell.onclick = function(){window.location.href = "overview.php?character=" + character.characterId};
                container.appendChild(nameCell);
                    
                    const actionCell = document.createElement("TD");
                        actionCell.classList.add("textaligncenter");
                        
                        const renameButton = document.createElement("BUTTON");
                            renameButton.innerHTML = "Átnevezés";
                            renameButton.onclick = function(){pageController.renameCharacter(character.characterId, character.characterName)};
                    actionCell.appendChild(renameButton);
                        
                        const deleteButton = document.createElement("BUTTON");
                            deleteButton.innerHTML = "Törlés";
                            deleteButton.onclick = function(){characterController.deleteCharacter(character.characterId)};
                    actionCell.appendChild(deleteButton);
                    
                container.appendChild(actionCell);
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function displayNoCharacters(){
            try{
                const container = document.createElement("TR");
                    const cell = document.createElement("TD");
                        cell.colSpan = 2;
                        cell.innerHTML = "Nincs karakter.";
                        cell.classList.add("fontsize1_25rem");
                        cell.classList.add("textaligncenter");
                container.appendChild(cell);
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }
})();*/