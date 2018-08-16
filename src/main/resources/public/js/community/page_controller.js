(function PageController(){
    scriptLoader.loadScript("js/community/friend_controller.js");
    scriptLoader.loadScript("js/community/friend_search_controller.js");
    scriptLoader.loadScript("js/community/blocked_character_search_controller.js");
    scriptLoader.loadScript("js/community/blocked_character_controller.js");

    window.pageController = new function(){
        this.refresh = refresh;
        this.showAddFriendWindow = showAddFriendWindow;
        this.showBlockableCharactersWindow = showBlockableCharactersWindow;
        this.showBlockedCharacters = showBlockedCharacters;
        this.showFriendList = showFriendList;
        this.showLists = showLists;
        
        $(document).ready(function(){
            refresh(true, true);
        });
    }

    function refresh(needReload, windowsBackToDefault){
        try{
            if(needReload == null || needReload == undefined){
                needReload = true;
            }
            if(windowsBackToDefault == null || windowsBackToDefault == undefined){
                windowsBackToDefault = false;
            }
            
            if(windowsBackToDefault){
                showLists();
            }
            
            if(needReload){
                friendController.loadFriends();
                friendController.loadFriendRequests();
                friendController.loadSentFriendRequests();
                blockedCharacterController.loadBlockedCharacters();
            }
            
            blockedCharacterController.displayBlockedCharacters();
            
            friendController.showFriendRequestNum();
            friendController.showSentFriendRequestNum();
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
    
    function showBlockableCharactersWindow(){
        try{
            $(".mainlabel").hide();
            $("#blockedcharacter").show();
        }catch(err){
             const message = arguments.callee.name + " - " + err.name + ": " + err.message;
             logService.log(message, "error");
        }
    }

    function showBlockedCharacters(){
        try{
            $(".listlabel").hide();
            $("#blockedcharacters").show();
        }catch(err){
             const message = arguments.callee.name + " - " + err.name + ": " + err.message;
             logService.log(message, "error");
        }
    }

    function showFriendList(){
        try{
            $(".listlabel").hide();
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