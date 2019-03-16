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
})();