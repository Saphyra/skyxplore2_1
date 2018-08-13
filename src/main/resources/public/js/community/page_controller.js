(function PageController(){
    scriptLoader.loadScript("js/community/friend_controller.js");
    scriptLoader.loadScript("js/community/friend_search_controller.js");
    scriptLoader.loadScript("js/community/block_user_search_controller.js");
    scriptLoader.loadScript("js/community/block_user_controller.js");

    window.pageController = new function(){
        this.refresh = refresh;
        this.showAddFriendWindow = showAddFriendWindow;
        this.showBlockableUsersWindow = showBlockableUsersWindow;
        this.showBlockedUsers = showBlockedUsers;
        this.showFriendList = showFriendList;
        this.showLists = showLists;
    }

    function refresh(needReload, windowsBackToDefault){
        try{
            //TODO implement
        }catch(err){
             const message = arguments.callee.name + " - " + err.name + ": " + err.message;
             logService.log(message, "error");
        }
    }

    function showAddFriendWindow(){
        try{
            $(".mainlabel").hide();
            $("#addfriend").show();
        }catch(err){
             const message = arguments.callee.name + " - " + err.name + ": " + err.message;
             logService.log(message, "error");
        }
    }
    
    function showBlockableUsersWindow(){
        try{
            $(".mainlabel").hide();
            $("#blockuser").show();
        }catch(err){
             const message = arguments.callee.name + " - " + err.name + ": " + err.message;
             logService.log(message, "error");
        }
    }

    function showBlockedUsers(){
        try{
            $(".friendlistlabel").hide();
            $("#blockedusers").show();
        }catch(err){
             const message = arguments.callee.name + " - " + err.name + ": " + err.message;
             logService.log(message, "error");
        }
    }

    function showFriendList(){
        try{
            $(".friendlistlabel").hide();
            $("#listfriends").show();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }

    function showLists(){
        try{
            $(".mainlabel").hide();
            $("#lists").show();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();