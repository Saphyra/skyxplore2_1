(function LobbyEventController(){
    events.LOAD_LOBBY_EVENTS = "load_lobby_events";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOBBY_LOADED},
        function(){
            setInterval(function(){eventProcessor.processEvent(new Event(events.LOAD_LOBBY_EVENTS))}, 5000);
        },
        true
    ))

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_LOBBY_EVENTS},
        function(){
            loadEvents();
        }
    ))

    function loadEvents(){
        const request = new Request(HttpMethod.GET, Mapping.GET_LOBBY_EVENTS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(lobbyEvents){
                processEvents(lobbyEvents);
            }

        dao.sendRequestAsync(request);
    }

    function processEvents(lobbyEvents){
        for(let eIndex in lobbyEvents){
            setTimeout(function(){processEvent(lobbyEvents[eIndex])}, 0);
        }

        function processEvent(event){
            switch (event.eventType){
                case "ENTER":
                    eventProcessor.processEvent(new Event(events.DISPLAY_CHARACTERS, [event.data]));
                break;
                case "EXIT":
                    eventProcessor.processEvent(new Event(events.MEMBER_LEFT, event.data.characterId));
                break;
                case "OWNER_CHANGED":
                    $("#owner-control-panel").hide();
                    eventProcessor.processEvent(new Event(events.LOAD_LOBBY));
                break;
                default:
                    throwException("IllegalArgument", event.eventType + " is not supported eventType");
                break;
            }
        }
    }
})();