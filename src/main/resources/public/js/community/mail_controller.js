(function MailController(){
    window.mailController = new function(){
        scriptLoader.loadScript("js/common/dao/community_dao.js");
        
        $(document).ready(function(){
            displayNumberOfUnreadMails();
        });
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