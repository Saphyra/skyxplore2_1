(function FriendController(){
    events.DELETE_FRIEND = "delete_friend";

    $(document).ready(init);

    let isActive = true;

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.OPEN_FRIENDS_TAB},
        function(){
            loadFriends();
            isActive = true;
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType == events.OPEN_FRIEND_REQUESTS_TAB
                || eventType == events.OPEN_SENT_FRIEND_REQUESTS_TAB
        },
        function(){
            isActive = false;
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType == events.OPEN_FRIEND_CHARACTERS_TAB
                && isActive
        },
        loadFriends
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DELETE_FRIEND},
        function(event){
            deleteFriend(event.getPayload());
        }
    ));

    function loadFriends(){
        const request = new Request(HttpMethod.GET, Mapping.GET_FRIENDS);
            request.convertResponse = function(response){
                const friends = JSON.parse(response.body);
                friends.sort(function(a, b){
                    if(a.active == b.active){
                        return a.friendName.localeCompare(b.friendName);
                    }
                    if(a.active == true){
                        return 1;
                    }
                });
                return friends;
            }
            request.processValidResponse = function(friends){
                showFriends(friends);
            }
        dao.sendRequestAsync(request);

        function showFriends(friends){
            friends.length == 0 ? $("#no-friends").show() : $("#no-friends").hide();

            const container = document.getElementById("friend-list");
                container.innerHTML = "";

            for(let fIndex in friends){
                container.appendChild(createFriend(friends[fIndex]));
            }

            function createFriend(friend){
                const container = document.createElement("DIV");
                    container.classList.add("friend-list-item");
                    container.id = generateFriendId(friend.friendshipId);

                    if(friend.active){
                        container.classList.add("active-friend");
                    }

                    const friendName = document.createElement("DIV");
                        friendName.innerHTML = friend.friendName;
                container.appendChild(friendName);

                    const buttonWrapper = document.createElement("DIV");
                        buttonWrapper.classList.add("friend-list-item-button-wrapper");

                        const wrapperSpan = document.createElement("SPAN");

                            const deleteButton = document.createElement("BUTTON");
                                deleteButton.innerHTML = Localization.getAdditionalContent("delete-friend");
                                deleteButton.onclick = function(){
                                    eventProcessor.processEvent(new Event(events.DELETE_FRIEND, friend.friendshipId));
                                }
                        wrapperSpan.appendChild(deleteButton);

                            const viewProfileButton = document.createElement("BUTTON");
                                viewProfileButton.innerHTML = Localization.getAdditionalContent("view-profile");
                        wrapperSpan.appendChild(viewProfileButton);

                    buttonWrapper.appendChild(wrapperSpan);

                        const writeMailButton = document.createElement("BUTTON");
                            writeMailButton.innerHTML = Localization.getAdditionalContent("write-mail-to-friend");
                            writeMailButton.onclick = function(){
                                writeMailController.setAddressee({characterId: friend.friendId, characterName: friend.friendName});
                                eventProcessor.processEvent(new Event(events.OPEN_WRITE_MAIL_WINDOW));
                            }
                    buttonWrapper.appendChild(writeMailButton);

                $(buttonWrapper).hover(function(){$(wrapperSpan).fadeIn()}, function(){$(wrapperSpan).fadeOut()});
                container.appendChild(buttonWrapper);

                return container;
            }
        }
    }

    function deleteFriend(friendshipId){
        if(!confirm(Localization.getAdditionalContent("confirm-delete-friend"))){
            return;
        }

        const request = new Request(HttpMethod.DELETE, Mapping.DELETE_FRIEND, {value: friendshipId});
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("friend-deleted"));
                document.getElementById("friend-list").removeChild(document.getElementById(generateFriendId(friendshipId)));
            }
        
        dao.sendRequestAsync(request);
    }

    function generateFriendId(friendshipId){
        return "friend-" + friendshipId;
    }

    function init(){
        loadFriends();
    }
})();