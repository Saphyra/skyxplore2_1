(function MailController(){
    window.mailController = new function(){
        scriptLoader.loadScript("js/common/dao/mail_dao.js");
        scriptLoader.loadScript("js/common/translator/date_time_util.js");
        
        this.selectAll = selectAll;
        this.processOnSelected = processOnSelected;
        
        this.archivedMails = [];
        this.mails = [];
        this.sentMails = [];
        
        this.displayNumberOfUnreadMails = displayNumberOfUnreadMails;
        this.loadMails = loadMails;
        this.showMails = showMails;
        
        this.loadSentMails = loadSentMails;
        this.showSentMails = showSentMails;
        
        this.loadArchivedMails = loadArchivedMails;
        this.showArchivedMails = showArchivedMails;
        
        this.refresh = refresh;
    }
    
    function refresh(){
        try{
            displayNumberOfUnreadMails();
            loadMails();
            loadSentMails();
            
            showMails();
            showSentMails();
            
            loadArchivedMails();
            showArchivedMails();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function selectAll(checkboxName){
        try{
            $("input[name='" + checkboxName + "']").prop("checked", true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function processOnSelected(selectMenuId, checkboxName){
        try{
            const selectMenu = document.getElementById(selectMenuId);
            const mailIds = getMailIds(checkboxName);
            
            if(mailIds.length == 0){
                notificationService.showError("Válassza ki az üzeneteket, amin a kiválasztott műveletet végre szeretné hajtani!");
                return;
            }
            
            switch(selectMenu.value){
                case "none":
                    notificationService.showError("Válassza ki a végrerhajtani kívánt műveletet!");
                break;
                case "archive":
                    if(mailDao.archiveMails(mailIds)){
                        notificationService.showSuccess("Üzenetek archiválva.");
                    }else{
                        notificationService.showError("Üzenetek archiválása sikertelen.");
                    }
                    selectMenu.value = "none";
                    refresh();
                break;
                case "delete":
                    if(confirm("Biztosan törli a kiválasztott üzeneteket?")){
                        if(mailDao.deleteMails(mailIds)){
                            notificationService.showSuccess("Üzenetek törölve.");
                        }else{
                            notificationService.showError("Üzenetek törlése sikertelen.");
                        }
                        selectMenu.value = "none";
                        refresh();
                    }
                break;
                case "markasread":
                    if(mailDao.markMailsRead(mailIds)){
                        notificationService.showSuccess("Üzenetek olvasottnak jelölve.");
                    }else{
                        notificationService.showError("Üzenetek olvasottnak jelölése sikertelen.");
                    }
                    selectMenu.value = "none";
                    refresh();
                break;
                case "markasunread":
                    if(mailDao.markMailsUnread(mailIds)){
                        notificationService.showSuccess("Üzenetek olvasatlannak jelölve.");
                    }else{
                        notificationService.showError("Üzenetek olvasatlannak jelölése sikertelen.");
                    }
                    selectMenu.value = "none";
                    refresh();
                break;
                case "unarchive":
                    if(mailDao.unarchiveMails(mailIds)){
                        notificationService.showSuccess("Üzenetek visszaállítva.");
                    }else{
                        notificationService.showError("Üzenetek visszaállítása sikertelen.");
                    }
                    selectMenu.value = "none";
                    refresh();
                break;
                default:
                    throwException("UnknownValue", selectMenu.value);
                break;
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function getMailIds(checkboxName){
            try{
                const selected = $("input[name='" + checkboxName + "']:checked");
                const result = [];
                for(let cindex = 0; cindex < selected.length; cindex++){
                    result.push(selected[cindex].value);
                }
                return result;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return [];
            }
        }
    }
    
    function displayNumberOfUnreadMails(){
        try{
            const numberOfUnreadMails = mailDao.getNumberOfUnreadMails();
            if(numberOfUnreadMails > 0){
                document.getElementById("numberofunreadmails").innerHTML = " (" + numberOfUnreadMails + ")";
            }else{
                document.getElementById("numberofunreadmails").innerHTML = "";
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function orderMails(mails){
        try{
            mails.sort(function(a, b){
                return b.sendTime - a.sendTime;
            });
            return mails;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
})();