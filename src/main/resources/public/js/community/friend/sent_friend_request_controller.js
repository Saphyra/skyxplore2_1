(function SentFriendRequestController(){
    events.CANCEL_FRIEND_REQUEST = "cancel_friend_request";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.OPEN_SENT_FRIEND_REQUESTS_TAB},
        loadSentFriendRequests
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.CANCEL_FRIEND_REQUEST},
        function(event){cancelFriendRequest(event.getPayload())}
    ));

    function loadSentFriendRequests(){
        const request = new Request(HttpMethod.GET, Mapping.GET_SENT_FRIEND_REQUESTS);
            request.convertResponse = function(response){
                const friendRequests = JSON.parse(response.body);
                friendRequests.sort(function(a, b){return a.friendName.localeCompare(b.friendName)});
                return friendRequests;
            }
            request.processValidResponse = function(friendRequests){
                displaySentFriendRequests(friendRequests);
            }
        dao.sendRequestAsync(request);

        function displaySentFriendRequests(friendRequests){
            friendRequests.length == 0 ? $("#no-sent-friend-requests").show() : $("#no-sent-friend-requests").hide();

            const container = document.getElementById("sent-friend-request-list");
                container.innerHTML = "";

            for(let fIndex in friendRequests){
                container.appendChild(createSentFriendRequest(friendRequests[fIndex]));
            }

            function createSentFriendRequest(friendRequest){
                const container = document.createElement("DIV");
                    container.classList.add("friend-list-item");
                    container.id = generateSentFriendRequestId(friendRequest.friendRequestId);

                    const friendName = document.createElement("DIV");
                        friendName.innerHTML = friendRequest.friendName;
                container.appendChild(friendName);

                    const cancelButton = document.createElement("BUTTON");
                        cancelButton.classList.add("friend-list-item-button");
                        cancelButton.innerHTML = Localization.getAdditionalContent("cancel-friend-request");
                        cancelButton.onclick = function(){
                            eventProcessor.processEvent(new Event(events.CANCEL_FRIEND_REQUEST, friendRequest.friendRequestId));
                        }
                container.appendChild(cancelButton);

                return container;
            }
        }
    }

    function cancelFriendRequest(friendRequestId){
        const request = new Request(HttpMethod.DELETE, Mapping.CANCEL_FRIEND_REQUEST, {value: friendRequestId});
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("FRIEND_REQUEST_CANCELLED"));
                document.getElementById("sent-friend-request-list").removeChild(document.getElementById(generateSentFriendRequestId(friendRequestId)));
            }
        dao.sendRequestAsync(request);
    }

    function generateSentFriendRequestId(id){
        return "sent-friend-request-" + id;
    }
})();