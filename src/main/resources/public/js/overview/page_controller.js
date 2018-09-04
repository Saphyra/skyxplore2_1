(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/overview/equipment_controller.js");
        scriptLoader.loadScript("js/common/dao/community_dao.js");
        
        $(document).ready(function(){
            displayNotifications();
        });
    }
    
    function displayNotifications(){
        try{
            const notificationNum = getNumberOfNotifications();
            
            if(notificationNum > 0){
                document.getElementById("notificationnum").innerHTML = " (" + notificationNum + ")";
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function getNumberOfNotifications(){
            try{
                const friendRequestNum = communityDao.getNumberOfFriendRequests();
                const unreadMailNum = communityDao.getNumberOfUnreadMails();
                return friendRequestNum + unreadMailNum;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return 0;
            }
        }
    }
})();