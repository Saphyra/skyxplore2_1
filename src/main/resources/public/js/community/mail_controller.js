(function MailController(){
    window.mailController = new function(){
        scriptLoader.loadScript("js/common/dao/community_dao.js");
        scriptLoader.loadScript("js/common/translator/date_time_util.js");
        
        this.mails = [];
        
        this.loadMails = loadMails;
        this.showMails = showMails;
        
        $(document).ready(function(){
            displayNumberOfUnreadMails();
        });
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
                        mailHeader.appendChild(createMailHeaderTable(mail));
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
                    if(!mail.read){
                        communityDao.markMailsRead([mail.mailId]);
                        container.classList.remove("unreadmail");
                        displayNumberOfUnreadMails();
                    }
                }
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
            
            function createMailHeaderTable(mail){
                try{
                    const table = document.createElement("TABLE");
                    
                        const row1 = document.createElement("TR");
                            const fromCell = document.createElement("TD");
                                fromCell.innerHTML = "Feladó: " + mail.fromName;
                        row1.appendChild(fromCell);
                        
                            const sendTimeCell = document.createElement("TD");
                                sendTimeCell.classList.add("textalignright");
                                sendTimeCell.innerHTML = "Elküldve: " + dateTimeUtil.formatEpoch(mail.sendTime);
                        row1.appendChild(sendTimeCell);
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
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();