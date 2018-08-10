(function PageController(){
    window.pageController = new function(){
        this.showAddFriendWindow = showAddFriendWindow;
        this.showBlockedUsers = showBlockedUsers;
        this.showFriendList = showFriendList;
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
})();