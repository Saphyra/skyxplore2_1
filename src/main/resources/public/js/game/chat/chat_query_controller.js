(function ChatQueryController(){
    let queryAll = true;
    let activeChatRoom = null;

    $(document).ready(init);

    window.chatQueryController = new function(){
        this.getActiveChatRoom = function(){return activeChatRoom};
    }

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
                if(activeChatRoom == null){
                    activeChatRoom = chatRoom.roomId;
                }

                const newMessageMarker = getOrCreateRoomLabel(chatRoom);
                    if(chatRoom.messages.length && chatRoom.roomId != activeChatRoom){
                        newMessageMarker.style.display = "inline";
                    }

                const messageContainer = getOrCreateMessageContainer(chatRoom);
                const needScroll = messageContainer.scrollTop + messageContainer.offsetHeight == messageContainer.scrollHeight;
                    chatRoom.messages.sort(function(a, b){return a.createdAt - b.createdAt});
                    for(let messageIndex in chatRoom.messages){
                        const message = chatRoom.messages[messageIndex];
                        messageContainer.appendChild(createMessage(message));
                    }

                if(needScroll){
                    messageContainer.scrollTop = messageContainer.scrollHeight;
                }
                switchTab("chat-group", createMessageContainerId(activeChatRoom));

                function getOrCreateRoomLabel(chatRoom){
                    const newMessageMarkerId = createNewMessageMarkerId(chatRoom.roomId);
                    return new Optional(document.getElementById(newMessageMarkerId))
                        .orElseGet(function(){return createRoomLabel(chatRoom)});

                    function createRoomLabel(chatRoom){
                        const roomLabel = document.createElement("DIV");
                            roomLabel.classList.add("room-label");
                            roomLabel.id = createChatRoomId(chatRoom.roomId);

                            if(activeChatRoom == chatRoom.roomId){
                                roomLabel.classList.add("active-chat-room");
                            }

                            const label = document.createElement("SPAN");
                                label.innerHTML = chatRoom.defaultRoom ? Localization.getAdditionalContent(chatRoom.roomName) : chatRoom.roomName;
                        roomLabel.appendChild(label);

                            const newMessageMarkerSpan = document.createElement("SPAN");
                                newMessageMarkerSpan.innerHTML = " (!)";
                                newMessageMarkerSpan.id = createNewMessageMarkerId(chatRoom.roomId);
                        roomLabel.appendChild(newMessageMarkerSpan);

                            const exitButton = document.createElement("BUTTON");
                                exitButton.innerHTML = "X";
                        roomLabel.appendChild(exitButton);

                        document.getElementById("chat-rooms").appendChild(roomLabel);

                        return newMessageMarkerSpan;
                    }

                    function createNewMessageMarkerId(roomId){
                        return "new-message-marker-" + roomId;
                    }
                }

                function getOrCreateMessageContainer(chatRoom){
                    const messageContainerId = createMessageContainerId(chatRoom.roomId);
                    return new Optional(document.getElementById(messageContainerId))
                        .orElseGet(function(){return createMessageContainer(chatRoom)});


                    function createMessageContainer(chatRoom){
                        const container = document.createElement("DIV");
                            container.classList.add("chat-group");
                            container.id = createMessageContainerId(chatRoom.roomId);

                        document.getElementById("chat-message-container").appendChild(container);
                        return container;
                    }
                }

                function createMessage(message){
                    const messageTag = document.createElement("DIV");
                        messageTag.classList.add("message");

                        const senderSpan = document.createElement("SPAN");
                            senderSpan.innerHTML = message.senderName;
                            senderSpan.classList.add("message-sender");
                    messageTag.appendChild(senderSpan);

                        const messageSpan = document.createElement("SPAN");
                            messageSpan.innerHTML = message.message;
                            messageSpan.classList.add("message-text");
                    messageTag.appendChild(messageSpan);
                    return messageTag;
                }
            }
        }
    }

    function createChatRoomId(roomId){
        return "room-label-" + roomId;
    }

    function createMessageContainerId(roomId){
        return "message-container-" + roomId;
    }

    function init(){
        setIntervalImmediate(
            loadMessages,
            1000
        );
    }
})();