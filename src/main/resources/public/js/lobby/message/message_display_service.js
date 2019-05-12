(function MessageDisplayService(){
    scriptLoader.loadScript("js/common/character_id_query_service.js");

    events.LOAD_MESSAGES = "load_messages";

    let queryAll = true;

    $(document).ready(init);

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOAD_MESSAGES},
        loadMessages
    ));

    function loadMessages(){
        const request = new Request(HttpMethod.GET, Mapping.GET_MESSAGES + "?all=" + queryAll);
            request.convertResponse = function(response){
                const messages = JSON.parse(response.body);
                messages.sort(function(a, b){return a.createdAt - b.createdAt});
                return messages;
            }
            request.processValidResponse = function(messages){
                queryAll = false;
                displayMessages(messages);
            }
        dao.sendRequestAsync(request);

        function displayMessages(messages){
            const container = document.getElementById("messages");

            for(let mIndex in messages){
                container.appendChild(createMessage(messages[mIndex]));
            }

            function createMessage(message){
                const wrapper = document.createElement("DIV");
                    wrapper.classList.add("message-wrapper");
                    if(message.senderId === characterIdQueryService.getCharacterId()){
                        wrapper.classList.add("message-own");
                    }

                    const container = document.createElement("DIV");
                        container.classList.add("message");

                        const sender = document.createElement("DIV");
                            sender.classList.add("message-sender");
                            sender.innerHTML = message.senderName;
                    container.appendChild(sender);

                        const messageText = document.createElement("DIV");
                            messageText.classList.add("message-text");
                            messageText.innerHTML = message.message;
                    container.appendChild(messageText);
                wrapper.appendChild(container);
                return wrapper;
            }
        }
    }

    function init(){
        setInterval(
            function(){eventProcessor.processEvent(new Event(events.LOAD_MESSAGES))},
            1000
        );
    }
})();