(function ChatQueryController(){
    let queryAll = true;

    $(document).ready(init);

    function loadMessages(){
        const request = new Request(HttpMethod.GET, Mapping.GET_GAME_MESSAGES);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = displayMessages;
        dao.sendRequestAsync(request);

        function displayMessages(messages){
            for(let mIndex in messages){
                displayChatRoom(messages[mIndex]);
            }

            function displayChatRoom(chatRoom){
                const roomLabel = getOrCreateRoomLabel(chatRoom);

                function getOrCreateRoomLabel(chatRoom){
                    const roomLabelId = createChatRoomId(chatRoom.roomId);
                    return new Optional(document.getElementById(roomLabelId))
                        .orElseGet(function(){createRoomLabel(chatRoom)});

                    function createRoomLabel(chatRoom){
                        const roomLabel = document.createElement("DIV");
                            roomLabel.classList.add("room-label");

                            roomLabel.id = createChatRoomId(chatRoom.roomId);
                            const label = document.createElement("SPAN");
                                label.innerHTML = chatRoom.defaultRoom ? Localization.getAdditionalContent(chatRoom.roomName) : chatRoom.roomName;
                        roomLabel.appendChild(label);

                            const exitButton = document.createElement("BUTTON");
                                exitButton.innerHTML = "X";
                        roomLabel.appendChild(exitButton);

                        document.getElementById("chat-rooms").appendChild(roomLabel);

                        return roomLabel;
                    }
                }
            }
        }
    }

    function createChatRoomId(roomId){
        return "room-label-" + roomId;
    }

    function init(){
        setInterval(
            loadMessages,
            1000
        );
    }
})();