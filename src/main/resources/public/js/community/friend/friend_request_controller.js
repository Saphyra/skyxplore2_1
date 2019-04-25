(function FriendRequestController(){
    events.ACCEPT_FRIEND_REQUEST = "accept_friend_request";
    events.FRIEND_REQUEST_ACCEPTED = "friend_request_accepted";

    events.DECLINE_FRIEND_REQUEST = "decline_friend_request";
    events.FRIEND_REQUEST_DECLINED = "friend_request_declined";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.OPEN_FRIEND_REQUESTS_TAB},
        function(){loadFriendRequests()}
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ACCEPT_FRIEND_REQUEST},
        function(event){acceptFriendRequest(event.getPayload())}
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DECLINE_FRIEND_REQUEST},
        function(event){declineFriendRequest(event.getPayload())}
    ));

    function loadFriendRequests(){
        const request = new Request(HttpMethod.GET, Mapping.GET_FRIEND_REQUESTS);
            request.convertResponse = function(response){
                const friendRequests = JSON.parse(response.body);
                friendRequests.sort(function(a, b){return a.friendName.localeCompare(b.friendName)});
                return friendRequests;
            }
            request.processValidResponse = function(friendRequests){
                displayFriendRequests(friendRequests);
            }
        dao.sendRequestAsync(request);

        function displayFriendRequests(friendRequests){
            friendRequests.length == 0 ? $("#no-friend-requests").show() : $("#no-friend-requests").hide();

            const container = document.getElementById("friend-request-list");
                container.innerHTML = "";

            for(let fIndex in friendRequests){
                container.appendChild(createFriendRequest(friendRequests[fIndex]));
            }

            function createFriendRequest(friendRequest){
                const container = document.createElement("DIV");
                    container.classList.add("friend-list-item");
                    container.id = generateFriendRequestId(friendRequest.friendRequestId);

                    const friendName = document.createElement("DIV");
                        friendName.innerHTML = friendRequest.friendName;
                container.appendChild(friendName);

                    const buttonWrapper = document.createElement("DIV");
                        buttonWrapper.classList.add("friend-list-item-button-wrapper");

                        const wrapperSpan = document.createElement("SPAN");

                            const blockButton = document.createElement("BUTTON");
                                blockButton.innerHTML = Localization.getAdditionalContent("block-character");
                        wrapperSpan.appendChild(blockButton);

                            const declineButton = document.createElement("BUTTON");
                                declineButton.innerHTML = Localization.getAdditionalContent("decline-friend-request");
                                declineButton.onclick = function(){
                                    eventProcessor.processEvent(new Event(events.DECLINE_FRIEND_REQUEST, friendRequest.friendRequestId));
                                }
                        wrapperSpan.appendChild(declineButton);

                    buttonWrapper.appendChild(wrapperSpan);

                        const acceptButton = document.createElement("BUTTON");
                            acceptButton.innerHTML = Localization.getAdditionalContent("accept-friend-request");
                            acceptButton.onclick = function(){
                                eventProcessor.processEvent(new Event(events.ACCEPT_FRIEND_REQUEST, friendRequest.friendRequestId));
                            }
                    buttonWrapper.appendChild(acceptButton);

                $(buttonWrapper).hover(function(){$(wrapperSpan).fadeIn()}, function(){$(wrapperSpan).fadeOut()});
                container.appendChild(buttonWrapper);

                return container;
            }
        }
    }

    function acceptFriendRequest(friendRequestId){
        const request = new Request(HttpMethod.POST, Mapping.ACCEPT_FRIEND_REQUEST, {value: friendRequestId});
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("FRIEND_REQUEST_ACCEPTED"));
                document.getElementById("friend-request-list").removeChild(document.getElementById(generateFriendRequestId(friendRequestId)));
                eventProcessor.processEvent(new Event(events.FRIEND_REQUEST_ACCEPTED));
            }
        dao.sendRequestAsync(request);
    }

    function declineFriendRequest(friendRequestId){
        const request = new Request(HttpMethod.DELETE, Mapping.DECLINE_FRIEND_REQUEST, {value: friendRequestId});
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("FRIEND_REQUEST_DECLINED"));
                document.getElementById("friend-request-list").removeChild(document.getElementById(generateFriendRequestId(friendRequestId)));
                eventProcessor.processEvent(new Event(events.FRIEND_REQUEST_DECLINED));
            }
        dao.sendRequestAsync(request);
    }

    function generateFriendRequestId(id){
        return "friend-request-" + id;
    }
})();