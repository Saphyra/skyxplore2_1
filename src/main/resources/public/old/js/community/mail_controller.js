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
    
    function loadMails(){
        try{
            const mails = mailDao.getMails();
            mailController.mails = orderMails(mails);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            mailController.mails = [];
        }
    }
    
    function loadArchivedMails(){
        try{
            const mails = mailDao.getArchivedMails();
            mailController.archivedMails = orderMails(mails);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            mailController.archivedMails = [];
        }
    }
    
    function showArchivedMails(){
        try{
            const container = document.getElementById("archivedmaillist");
                container.innerHTML = "";
                
            if(mailController.archivedMails.length == 0){
                container.innerHTML = "Nincs archivált üzenet.";
            }
            
            for(let mindex in mailController.archivedMails){
                container.appendChild(createItem(mailController.archivedMails[mindex]));
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createItem(mail){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("mailitem");
                    
                    const mailHeader = document.createElement("DIV");
                        mailHeader.classList.add("mailheader");
                        mailHeader.appendChild(createMailHeaderTable(mail, container));
                container.appendChild(mailHeader);
                
                    const mailBody = document.createElement("DIV");
                        mailBody.classList.add("mailbody");
                        
                        const message = document.createElement("TEXTAREA");
                            message.disabled = true;
                            message.value = mail.message;
                    mailBody.appendChild(message);
                container.appendChild(mailBody);
                
                mailHeader.onclick = function(){
                    $(mailBody).fadeToggle();
                }
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
            
            function createMailHeaderTable(mail, container){
                try{
                    const table = document.createElement("TABLE");
                    
                        const row1 = document.createElement("TR");
                            const checkboxCell = document.createElement("TD");
                                checkboxCell.onclick = function(e){e.stopPropagation()};
                                checkboxCell.rowSpan = 2;
                                checkboxCell.classList.add("textaligncenter");
                                checkboxCell.classList.add("width2rem");
                                
                                const checkbox = document.createElement("INPUT");
                                    checkbox.type = "checkbox";
                                    checkbox.name = "archivedmailselected";
                                    checkbox.value = mail.mailId;
                                    checkbox.onclick = function(e){e.stopPropagation()};
                            checkboxCell.appendChild(checkbox);
                        row1.appendChild(checkboxCell);
                        
                            const fromCell = document.createElement("TD");
                                fromCell.innerHTML = "Feladó: " + mail.fromName;
                        row1.appendChild(fromCell);
                        
                            const sendTimeCell = document.createElement("TD");
                                sendTimeCell.classList.add("textalignright");
                                sendTimeCell.classList.add("width11rem");
                                sendTimeCell.innerHTML = dateTimeUtil.formatEpoch(mail.sendTime);
                        row1.appendChild(sendTimeCell);
                        
                            const buttonCell = document.createElement("TD");
                                buttonCell.rowSpan = 2;
                                buttonCell.classList.add("textaligncenter");
                                buttonCell.classList.add("width2rem");
                                
                                const deleteButton = document.createElement("BUTTON");
                                    deleteButton.innerHTML = "Törlés";
                                    deleteButton.onclick = function(e){
                                        e.stopPropagation();
                                        if(confirm("Biztosan törli a kiválszott üzeneteket?")){
                                            if(mailDao.deleteMails([mail.mailId])){
                                                notificationService.showSuccess("Üzenet törölve.");
                                            }else{
                                                notificationService.showError("Üzenet törlése sikertelen.");
                                            }
                                            pageController.refresh(true, false);
                                        }
                                    }
                            buttonCell.appendChild(deleteButton);
                            
                                const unArchiveButton = document.createElement("BUTTON");
                                    unArchiveButton.innerHTML = "Visszaállítás";
                                    unArchiveButton.onclick = function(e){
                                        if(mailDao.unarchiveMails([mail.mailId])){
                                            notificationService.showSuccess("Üzenet visszaállítva.");
                                        }else{
                                            notificationService.showError("Üzenet visszaállítása sikertelen.");
                                        }
                                        refresh();
                                        e.stopPropagation();
                                    }
                            buttonCell.appendChild(unArchiveButton);
                        row1.appendChild(buttonCell);
                    table.appendChild(row1);
                    
                        const row2 = document.createElement("TR");
                            const subjectCell = document.createElement("TD");
                                subjectCell.colSpan = 2;
                                subjectCell.innerHTML = "Tárgy: " + mail.subject;
                        row2.appendChild(subjectCell);
                    table.appendChild(row2);
                    return table;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                    return document.createElement("TABLE");
                }
            }
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