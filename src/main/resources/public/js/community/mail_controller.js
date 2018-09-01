(function MailController(){
    window.mailController = new function(){
        scriptLoader.loadScript("js/common/dao/community_dao.js");
        scriptLoader.loadScript("js/common/translator/date_time_util.js");
        
        this.mails = [];
        
        this.displayNumberOfUnreadMails = displayNumberOfUnreadMails;
        this.loadMails = loadMails;
        this.showMails = showMails;
        
        this.refresh = refresh;
    }
    
    function refresh(){
        try{
            displayNumberOfUnreadMails();
            loadMails();
            showMails();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function loadMails(){
        try{
            const mails = communityDao.getMails();
            mailController.mails = orderMails(mails);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            mailController.mails = [];
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
    }
    
    function showMails(){
        try{
            const container = document.getElementById("incomingmaillist");
                container.innerHTML = "";
                
            if(mailController.mails.length == 0){
                container.innerHTML = "Nincs bejövő üzenet.";
            }
            
            for(let mindex in mailController.mails){
                container.appendChild(createItem(mailController.mails[mindex]));
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createItem(mail){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("mailitem");
                    
                    if(mail.read == false){
                        container.classList.add("unreadmail");
                    }
                    
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
                    
                        const replyButton = document.createElement("BUTTON");
                            replyButton.innerHTML = "Válaszolás";
                            replyButton.onclick = function(){
                                document.getElementById("subject").value = "Re: " + mail.subject;
                                newMailController.setAddressee({characterId: mail.from, characterName: mail.fromName});
                                switchTab("mainlabel", "mail");
                            }
                    mailBody.appendChild(replyButton);
                container.appendChild(mailBody);
                
                mailHeader.onclick = function(){
                    $(mailBody).fadeToggle();
                    if(!mail.read){
                        communityDao.markMailsRead([mail.mailId]);
                        container.classList.remove("unreadmail");
                        displayNumberOfUnreadMails();
                        mail.read = true;
                    }
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
                                            if(communityDao.deleteMails([mail.mailId])){
                                                notificationService.showSuccess("Üzenet törölve.");
                                            }else{
                                                notificationService.showError("Üzenet törlése sikertelen.");
                                            }
                                            pageController.refresh(true, false);
                                        }
                                    }
                            buttonCell.appendChild(deleteButton);
                            
                                const archiveButton = document.createElement("BUTTON");
                                    archiveButton.innerHTML = "Archiválás";
                                    archiveButton.onclick = function(e){
                                        if(communityDao.archiveMails([mail.mailId])){
                                            notificationService.showSuccess("Üzenet archiválva.");
                                        }else{
                                            notificationService.showError("Üzenet archiválása sikertelen.");
                                        }
                                        refresh();
                                        e.stopPropagation();
                                    }
                            buttonCell.appendChild(archiveButton);
                            
                                const markButton = document.createElement("BUTTON");
                                    if(mail.read){
                                        markButton.innerHTML = "Megjelölés olvasatlanként";
                                        markButton.onclick = function(e){
                                            communityDao.markMailsUnread([mail.mailId]);
                                            refresh();
                                            e.stopPropagation();
                                        }
                                    }else{
                                        markButton.innerHTML = "Megjelölés olvasottként";
                                        markButton.onclick = function(e){
                                            communityDao.markMailsRead([mail.mailId]);
                                            refresh();
                                            e.stopPropagation();
                                        }
                                    }
                            buttonCell.appendChild(markButton);
                                
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
    
    function displayNumberOfUnreadMails(){
        try{
            const numberOfUnreadMails = communityDao.getNumberOfUnreadMails();
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
})();