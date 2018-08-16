(function FriendController(){
    scriptLoader.loadScript("js/common/dao/community_dao.js");
    
    window.friendController = new function(){
        this.friends = [];
        this.friendRequests = [];
        this.sentFriendRequests = [];
        
        this.addFriend = addFriend;
        this.loadFriends = loadFriends;
        this.loadFriendRequests = loadFriendRequests;
        this.loadSentFriendRequests = loadSentFriendRequests;
        
        this.showFriends = showFriends;
        this.showFriendRequestNum = showFriendRequestNum;
        this.showFriendRequests = showFriendRequests;
        this.showSentFriendRequestNum = showSentFriendRequestNum;
        this.showSentFriendRequests = showSentFriendRequests;
    }

    function addFriend(friendId){
        try{
            const isAdded = communityDao.sendFriendRequest(sessionStorage.characterId, friendId);
            if(isAdded){
                notificationService.showSuccess("Barátkérelem elküldve.");
            }else{
                notificationService.showError("Barátkérelem elküldése sikertelen.");
            }

            pageController.refresh(true, true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function loadFriends(){
        try{
            const friendList = communityDao.getFriends(sessionStorage.characterId);
            friendController.friends = orderFriends(friendList);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            friendController.friends = [];
        }
    }
    
    function loadFriendRequests(){
        try{
            const friendRequestList = communityDao.getFriendRequests(sessionStorage.characterId);
            friendController.friendRequests = orderFriends(friendRequestList);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            friendController.friendRequests = [];
        }
    }
    
    function loadSentFriendRequests(){
        try{
            const friendRequestList = communityDao.getSentFriendRequests(sessionStorage.characterId);
            friendController.sentFriendRequests = orderFriends(friendRequestList)
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            friendController.friendRequests = [];
        }
    }
    
    function showFriends(){
        //TODO implement
    }
    
    function showFriendRequestNum(){
        try{
            document.getElementById("friendrequestnum").innerHTML = friendController.friendRequests.length;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showFriendRequests(){
        try{
            const container = document.getElementById("friendrequestitems");
                container.innerHTML = "";
                
                if(!friendController.friendRequests.length){
                    container.innerHTML = "Nincsenek barátkérelmek.";
                }
                
                for(let findex in friendController.friendRequests){
                    container.appendChild(createItem(friendController.friendRequests[findex]));
                }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createItem(character){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("friendlistitem");
                    
                    const characterNameElement = document.createElement("DIV");
                        characterNameElement.innerHTML = character.friendName;
                container.appendChild(characterNameElement);
                
                    const allowButton = document.createElement("BUTTON");
                        allowButton.classList.add("friendlistitembutton");
                        allowButton.innerHTML = "Elfogadás / Elutasítás";
                        allowButton.onclick = function(){}; //TODO implement (accept/cancel request)
                container.appendChild(allowButton);
                    
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
        }
    }
    
    function showSentFriendRequestNum(){
        try{
            document.getElementById("sentfriendrequestnum").innerHTML = friendController.sentFriendRequests.length;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showSentFriendRequests(){
        try{
            const container = document.getElementById("sentfriendrequestitems");
                container.innerHTML = "";
                
                if(!friendController.sentFriendRequests.length){
                    container.innerHTML = "Nincsenek elküldött barátkérelmek.";
                }
                
                for(let findex in friendController.sentFriendRequests){
                    container.appendChild(createItem(friendController.sentFriendRequests[findex]));
                }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createItem(character){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("friendlistitem");
                    
                    const characterNameElement = document.createElement("DIV");
                        characterNameElement.innerHTML = character.friendName;
                container.appendChild(characterNameElement);
                
                    const allowButton = document.createElement("BUTTON");
                        allowButton.classList.add("friendlistitembutton");
                        allowButton.innerHTML = "Visszavonás";
                        allowButton.onclick = function(){}; //TODO implement (cancel request)
                container.appendChild(allowButton);
                    
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
        }
    }
    
    function orderFriends(friends){
        try{
            friends.sort(function(a, b){
                return a.friendName.localeCompare(b.friendName);
            });
            return friends;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
}());