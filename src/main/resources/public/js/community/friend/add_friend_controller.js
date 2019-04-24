(function AddFriendController(){
    events.SEARCH_POSSIBLE_FRIENDS_ATTEMPT = "search_possible_friends_attempt";
    events.ADD_FRIEND = "add_friend";

    $(document).ready(init);

    let timeout = null;

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SEARCH_POSSIBLE_FRIENDS_ATTEMPT},
        function(){
            if(timeout){
                clearTimeout(timeout);
            }

            timeout = setTimeout(searchCharactersCanBeFriend, getSearchResultTimeout());
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ADD_FRIEND},
        function(event){
            sendFriendRequest(event.getPayload());
        }
    ));

    function searchCharactersCanBeFriend(){
        const friendName = $("#friend-name").val();

        if(friendName.length < 3){
            return;
        }

        const request = new Request(HttpMethod.POST, Mapping.GET_CHARACTERS_CAN_BE_FRIEND, {value: friendName});
            request.convertResponse = function(response){
                const characters = JSON.parse(response.body);
                characters.sort(function(a, b){return a.characterName.localeCompare(b.characterName)});
                return characters;
            }
            request.processValidResponse = function(characters){
                displayCharacters(characters);
            }

        dao.sendRequestAsync(request);

        function displayCharacters(characters){
            const container = document.getElementById("add-friend-search-result");
                container.innerHTML = "";

            characters.length == 0 ? $("#no-character-can-be-friend").show() : $("#no-character-can-be-friend").hide();

            for(let cIndex in characters){
                container.appendChild(createCharacter(characters[cIndex]));
            }

            function createCharacter(character){
                const container = document.createElement("DIV");
                    container.classList.add("character-can-be-friend");

                    const nameCell = document.createElement("DIV");
                        nameCell.innerHTML = character.characterName;
                container.appendChild(nameCell);

                    const addFriendButton = document.createElement("BUTTON");
                        addFriendButton.classList.add("add-friend-button");
                        addFriendButton.innerHTML = Localization.getAdditionalContent("add-friend");
                        addFriendButton.onclick = function(){
                            eventProcessor.processEvent(new Event(events.ADD_FRIEND, character.characterId));
                        }
                container.appendChild(addFriendButton);

                return container;
            }
        }
    }

    function sendFriendRequest(friendId){
        const request = new Request(HttpMethod.PUT, Mapping.ADD_FRIEND, {value: friendId});
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("FRIEND_REQUEST_SENT"));
                document.getElementById("add-friend-search-result").innerHTML = "";
                document.getElementById("friend-name").value = "";
            }
        dao.sendRequestAsync(request);

        eventProcessor.processEvent(new Event(events.OPEN_MAIN_LISTS));
    }

    function init(){
        $("#friend-name").keyup(function(){
            eventProcessor.processEvent(new Event(events.SEARCH_POSSIBLE_FRIENDS_ATTEMPT));
        });
    }
})();