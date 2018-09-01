(function MailController(){
    window.mailController = new function(){
        scriptLoader.loadScript("js/common/dao/community_dao.js");
        
        this.mails = null;
        
        this.loadMails = loadMails;
        this.showMails = showMails;
        
        $(document).ready(function(){
            displayNumberOfUnreadMails();
        });
    }
    
    function loadMails(){
        try{
            //TODO implement
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            mailController.mails = [];
        }
    }
    
    function showMails(){
        try{
            //TODO implement
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
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