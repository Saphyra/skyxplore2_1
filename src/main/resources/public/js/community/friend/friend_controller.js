(function FriendController(){
    $(document).ready(init);

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.OPEN_FRIENDS_TAB},
        loadFriends
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
                    container.id = createFriendId(friend.friendshipId);

                    const friendName = document.createElement("DIV");
                        friendName.innerHTML = friend.friendName;
                container.appendChild(friendName);

                return container;
            }
        }
    }

    function createFriendId(friendshipId){
        return "friend-" + friendshipId;
    }

    function init(){
        loadFriends();
    }
})();