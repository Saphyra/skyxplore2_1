(function NewMailController(){
    window.newMailController = new function(){
        scriptLoader.loadScript("js/common/dao/mail_dao.js");
        
        this.invalidateAddressee = invalidateAddressee;
        this.sendMail = sendMail;
        this.setAddressee = setAddressee;
        this.showAddressees = showAddressees;
        
        this.addresseeId = null;
        
        $(document).ready(function(){
            addEventListeners();
        });
    }
    
    function invalidateAddressee(){
        try{
            newMailController.addresseeId = null;
            document.getElementById("addressee").classList.remove("bordercolorgreen");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function sendMail(){
        try{
            const subjectField = document.getElementById("subject");
            const addresseeField = document.getElementById("addressee");
            const messageField = document.getElementById("message");
            
            if(newMailController.addresseeId == null){
                notificationService.showError("A címzett megadása kötelező!");
            }else if(subjectField.value.length == 0){
                notificationService.showError("A tárgy kitöltése kötelező!");
            }else if(subjectField.value.length > 100){
                notificationService.showError("A tárgy túl hosszú (Max 100 karakter).")
            }else if(messageField.value.length == 0){
                notificationService.showError("Az üzenet kitöltése kötelező!")
            }else if(messageField.value.length > 4000){
                notificationService.showError("Az üzenet túl hosszú (Max 4000 karakter).")
            }else{
                if(mailDao.sendMail(newMailController.addresseeId, subjectField.value, messageField.value)){
                    notificationService.showSuccess("Üzenet elküldve.");
                    subjectField.value = "";
                    addresseeField.value = "";
                    newMailController.invalidateAddressee();
                    messageField.value = "";
                    pageController.refresh(true, true);
                }else{
                    notificationService.showError("Üzenet küldése sikertelen.");
                }
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function setAddressee(character){
        try{
            newMailController.addresseeId = character.characterId;
            const inputField = document.getElementById("addressee");
                inputField.value = character.characterName;
                inputField.classList.add("bordercolorgreen");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showAddressees(){
        try{
            const container = document.getElementById("addresseelist");
                container.innerHTML = "";
                
                const queryText = document.getElementById("addressee").value;
                
                if(queryText.length == 0){
                    container.innerHTML = "Kezdje el beírni a címzett nevét!";
                    return;
                }
                
                const addressees = orderCharacters(mailDao.getAddressees(queryText, sessionStorage.characterId));
                if(addressees.length == 0){
                    container.innerHTML = "Nem található címzett.";
                }
                
                for(let aindex in addressees){
                    container.appendChild(createAddressee(addressees[aindex]));
                }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createAddressee(character){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("addressee");
                    container.innerHTML = character.characterName;
                    
                    container.onclick = function(){
                        newMailController.setAddressee(character);
                    }
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.getElementById("DIV");
            }
        }
        
        function orderCharacters(characters){
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
    
    function addEventListeners(){
        try{
            $("#addressee").focusin(function(){
                newMailController.showAddressees();
                $("#addresseelist").fadeIn();
            });
            
            $("#addressee").focusout(function(){
                $("#addresseelist").fadeOut();
            });
            
            $("#addressee").keyup(function(){
                newMailController.invalidateAddressee();
                newMailController.showAddressees();
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();