(function FriendController(){
    scriptLoader.loadScript("js/common/dao/community_dao.js");
    
    window.friendController = new function(){
        this.friends = [];
        this.friendRequests = [];
        this.sentFriendRequests = [];
        
        this.acceptFriendRequest = acceptFriendRequest;
        this.addFriend = addFriend;
        this.declineFriendRequest = declineFriendRequest;
        this.removeFriend = removeFriend;
        
        this.loadFriends = loadFriends;
        this.loadFriendRequests = loadFriendRequests;
        this.loadSentFriendRequests = loadSentFriendRequests;
        
        this.showFriends = showFriends;
        this.showFriendRequestNum = showFriendRequestNum;
        this.showFriendRequests = showFriendRequests;
        this.showSentFriendRequestNum = showSentFriendRequestNum;
        this.showSentFriendRequests = showSentFriendRequests;
    }

    function acceptFriendRequest(friendRequestId){
        try{
            if(communityDao.acceptFriendRequest(friendRequestId)){
                notificationService.showSuccess("Barátkérelem elfogadva.");
            }else{
                notificationService.showError("Barátkérelem elfogadása sikertelen.");
            }
            
            pageController.refresh(true, false);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function addFriend(friendId){
        try{
            const isAdded = communityDao.sendFriendRequest(friendId);
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
    
    function declineFriendRequest(requestId){
        try{
            if(confirm("Biztosan elutasítod a barátkérelmet?")){
                if(communityDao.declineFriendRequest(requestId)){
                    notificationService.showSuccess("Barátkérelem elutasítva.");
                }else{
                    notificationService.showError("Barátkérelem elutasítása sikertelen.");
                }
                
                pageController.refresh(true, false);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function removeFriend(friendship){
        try{
            if(confirm("Biztosan el akarod távolítani " + friendship.friendName + "-t a barátlistádról?")){
                if(communityDao.removeFriend(sessionStorage.characterId, friendship.friendshipId)){
                    notificationService.showSuccess("Barát eltávolítva.");
                }else{
                    notificationService.showError("Barát eltávolítása sikertelen.");
                }
                
                pageController.refresh(true, false);
            }
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
        try{
            const container = document.getElementById("friendlistitems");
                container.innerHTML = "";
                
                if(friendController.friends.length == 0){
                    container.innerHTML = "Nincsenek barátok.";
                }
                
                for(let findex in friendController.friends){
                    container.appendChild(createItem(friendController.friends[findex]));
                }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function createItem(friendship){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("friendlistitem");
                    if(friendship.active){
                        container.classList.add("activefriend");
                    }
                    
                    const characterNameElement = document.createElement("DIV");
                        characterNameElement.innerHTML = friendship.friendName;
                container.appendChild(characterNameElement);
                
                    const buttonWrapper = document.createElement("DIV");
                        buttonWrapper.classList.add("absolute");
                        buttonWrapper.classList.add("right0");
                        buttonWrapper.classList.add("top0");
                        buttonWrapper.classList.add("textalignright");
                        
                        const wrapperSpan = document.createElement("SPAN");
                            wrapperSpan.classList.add("displaynone");
                            
                            const removeFriendButton = document.createElement("BUTTON");
                                removeFriendButton.innerHTML = "Törlés";
                                removeFriendButton.onclick = function(){
                                    friendController.removeFriend(friendship);
                                }
                        wrapperSpan.appendChild(removeFriendButton);
                            
                            const viewProfileButton = document.createElement("BUTTON");
                                viewProfileButton.innerHTML = "Profil"
                                viewProfileButton.onclick = function(){
                                    //TODO view profile
                                };
                        wrapperSpan.appendChild(viewProfileButton);
                    buttonWrapper.appendChild(wrapperSpan);
                        
                        const mailButton = document.createElement("BUTTON");
                            mailButton.innerHTML = "Üzenet írása";
                            mailButton.onclick = function(){
                                newMailController.setAddressee({
                                    characterId: friendship.friendId,
                                    characterName: friendship.friendName
                                });
                                switchTab("mainlabel", "mail");
                            };
                    buttonWrapper.appendChild(mailButton);
                    
                    $(buttonWrapper).hover(function(){$(wrapperSpan).fadeIn()}, function(){$(wrapperSpan).fadeOut()});
                container.appendChild(buttonWrapper);
                
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
        }
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
        
        function createItem(friendRequest){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("friendlistitem");
                    
                    const characterNameElement = document.createElement("DIV");
                        characterNameElement.innerHTML = friendRequest.friendName;
                container.appendChild(characterNameElement);
                
                    const buttonWrapper = document.createElement("DIV");
                        buttonWrapper.classList.add("absolute");
                        buttonWrapper.classList.add("right0");
                        buttonWrapper.classList.add("top0");
                        buttonWrapper.classList.add("textalignright");
                        
                        const wrapperSpan = document.createElement("SPAN");
                            wrapperSpan.classList.add("displaynone");
                        
                            const blockButton = document.createElement("BUTTON");
                                    blockButton.innerHTML = "Blokkolás";
                                    blockButton.onclick = function(){
                                        if(confirm("Biztosan blokkolni szeretnéd " + friendRequest.friendName + " karaktert?")){
                                            blockedCharacterController.block(friendRequest.friendId);
                                        }
                                    }
                            wrapperSpan.appendChild(blockButton);
                    
                            const declineButton = document.createElement("BUTTON");
                                    declineButton.innerHTML = "Elutasítás";
                                    declineButton.onclick = function(){
                                        friendController.declineFriendRequest(friendRequest.friendRequestId)
                                    };
                            wrapperSpan.appendChild(declineButton);
                    buttonWrapper.appendChild(wrapperSpan);        
                        
                        const allowButton = document.createElement("BUTTON");
                            allowButton.innerHTML = "Elfogadás";
                            allowButton.onclick = function(){friendController.acceptFriendRequest(friendRequest.friendRequestId)};
                    buttonWrapper.appendChild(allowButton);
                    
                    $(buttonWrapper).hover(function(){$(wrapperSpan).fadeIn()}, function(){$(wrapperSpan).fadeOut()});
                container.appendChild(buttonWrapper);
                    
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
        
        function createItem(friendRequest){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("friendlistitem");
                    
                    const characterNameElement = document.createElement("DIV");
                        characterNameElement.innerHTML = friendRequest.friendName;
                container.appendChild(characterNameElement);
                
                    const allowButton = document.createElement("BUTTON");
                        allowButton.classList.add("friendlistitembutton");
                        allowButton.innerHTML = "Visszavonás";
                        allowButton.onclick = function(){
                            friendController.declineFriendRequest(friendRequest.friendRequestId)
                        };
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
                if(a.active == b.active){
                    return a.friendName.localeCompare(b.friendName);
                }
                if(a.active == true){
                    return 1;
                }
            });
            return friends;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
}());