(function MailDao(){
    window.mailDao = new function(){
        this.archiveMails = archiveMails;
        this.deleteMails = deleteMails;
        this.getAddressees = getAddressees;
        this.getArchivedMails = getArchivedMails;
        this.getMails = getMails;
        this.getNumberOfUnreadMails = getNumberOfUnreadMails;
        this.getSentMails = getSentMails;
        this.markMailsRead = markMailsRead;
        this.markMailsUnread = markMailsUnread;
        this.sendMail = sendMail;
        this.unarchiveMails = unarchiveMails;
    }
    
    /*
    Sets archive status of the selected mails as archived.
    Arguments:
        - mailIds: the ids of the mails.
    Returns:
        - true, if the update was successful.
        - false otherwise.
    Throws:
        - IllegalArgument exception if mailIds is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function archiveMails(mailIds){
        try{
            if(mailIds == null || mailIds == undefined){
                throwException("IllegalArgument", "mailIds must not be null or undefined.");
            }
            
            const path = "mail/archive";
            const response = dao.sendRequest(dao.POST, path, mailIds);
            if(response.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
    
    /*
    Deletes the given mails.
    Arguments:
        - the ids of the mails to delete.
    Returns:
        - true, if deletion was successful.
        - false otherwise.
    Throws:
        - IllegalArgument exception if mailIds is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function deleteMails(mailIds){
        try{
            if(mailIds == null || mailIds == undefined){
                throwException("IllegalArgument", "mailIds must not be null or undefined.");
            }
            
            const path = "mail";
            const response = dao.sendRequest(dao.DELETE, path, mailIds);
            if(response.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
    
    /*
    Queries the possible addressees for mail.
    Arguments:
        - queryText: the name to query.
        - characterId: the id of the character.
    Returns:
        - the list of addressees.
        - empty list upon fail.
    Throws:
        - IllegalArgument exception if query is empty, null or undefined.
        - IllegalArgument exception if characterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function getAddressees(queryText){
        try{
            
            if(queryText == null || queryText == undefined || queryText.length == 0){
                throwException("IllegalArgument", "queryText must not be null or undefined, and must not be empty.");
            }
            
            const path = "mail/addressee";
            const body = {
                value: queryText
            }
            const response = dao.sendRequest(dao.POST, path, body);
            if(response.status == ResponseStatus.OK){
                return JSON.parse(response.response);
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    Queries the archived mails of the character.
    Returns:
        - list of archived mails.
        - empty list upon fails.
    Throws:
        - UnknownBackendError exception if request fails.
    */
    function getArchivedMails(){
        try{
            const path = "mail/archived";
            const response = dao.sendRequest(dao.GET, path);
            if(response.status == ResponseStatus.OK){
                return JSON.parse(response.response);
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    Queries the character's received mails.
    Returns:
        - the mails of character
        - empty list upon fail
    Throws
        - UnknownBackendError exception when request fails.
    */
    function getMails(){
        try{
            const path = "mail";
            const result = dao.sendRequest(dao.GET, path);
            if(result.status == ResponseStatus.OK){
                return JSON.parse(result.response);
            }else{
                throwException("UnknownBackendError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    Queries the server for number of unread mails.
    Returns:
        - the number of unread mails.
        - 0 upon fail.
    Throws:
        UnknownBackendError exception if request fails.
    */
    function getNumberOfUnreadMails(){
        try{
            const path = "mail/unread";
            const response = dao.sendRequest(dao.GET, path);
            if(response.status == ResponseStatus.OK){
                return Number(response.response);
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return 0;
        }
    }
    
    /*
    Queries the sent mails of the character.
    Returns:
        - list of mails.
        - empty list upon fail.
    Throws:
        - UnknownBackendError exception if request fails.
    */
    function getSentMails(){
        try{
            const path = "mail/sent";
            const result = dao.sendRequest(dao.GET, path);
            if(result.status == ResponseStatus.OK){
                return JSON.parse(result.response);
            }else{
                throwException("UnknownBackendError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    Marks the given mails as read.
    Arguments:
        - mailIds: the ids of the mails to mark read.
    Throws:
        - IllegalArgument exception if mailIds is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function markMailsRead(mailIds){
        try{
            if(mailIds == null || mailIds == undefined){
                throwException("IllegalArgument", "mailIds must not be null or undefined.");
            }
            const path = "mail/mark/read";
            const response = dao.sendRequest(dao.POST, path, mailIds);
            if(response.status != ResponseStatus.OK){
                throwException("UnknownBackendError", response.toString());
            }
            return true;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Marks the given mails as unread.
    Arguments:
        - mailIds: the ids of the mails to mark unread.
    Throws:
        - IllegalArgument exception if mailIds is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function markMailsUnread(mailIds){
        try{
            if(mailIds == null || mailIds == undefined){
                throwException("IllegalArgument", "mailIds must not be null or undefined.");
            }
            const path = "mail/mark/unread";
            const response = dao.sendRequest(dao.POST, path, mailIds);
            if(response.status != ResponseStatus.OK){
                throwException("UnknownBackendError", response.toString());
            }
            return true;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Sends a mail.
    Arguments:
        - addresseeId: id of the recipient.
        - subject: subject of the mail.
        - message: text content of the mail.
    Returns:
        - true, if the message is successfully sent.
        - false otherwise
    Throws:
        - IllegalArgument exception if any of the arguments is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function sendMail(addresseeId, subject, message){
        try{
            const path = "mail/send";
            const body = {
                addresseeId: addresseeId,
                subject: subject,
                message: message
            };
            const response = dao.sendRequest(dao.PUT, path, body);
            if(response.status == ResponseStatus.OK){
                return true;
            }else if(response.status == ResponseStatus.LOCKED){
                return false;
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
    
    /*
    Restores the selected mails.
    Arguments:
        - mailIds: the ids of the mails to restore.
    Returns:
        - true, if mails successfully restored.
        - false otherwise.
    Throws:
        - IllegalArgument exception if mailIds is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function unarchiveMails(mailIds){
        try{
            if(mailIds == null || mailIds == undefined){
                throwException("IllegalArgument", "mailIds must not be null or undefined.");
            }
            const path = "mail/unarchive";
            const response = dao.sendRequest(dao.POST, path, mailIds);
            if(response.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();