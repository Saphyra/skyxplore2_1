(function NewMailController(){
    window.newMailController = new function(){
        scriptLoader.loadScript("js/common/dao/mail_dao.js");
        
        this.invalidateAddressee = invalidateAddressee;
        this.sendMail = sendMail;
        this.setAddressee = setAddressee;
        this.showAddressees = showAddressees;
        
        this.addresseeId = null;
        
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
})();