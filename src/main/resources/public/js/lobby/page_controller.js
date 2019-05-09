(function PageController(){
    events.EXIT_LOBBY = "exit_lobby";
    events.LOBBY_LOADED = "lobby_loaded";

    $(document).ready(init);

    let lobbyDetails = null;

    window.pageController = new function(){
        this.getLobbyDetails = function(){return lobbyDetails};
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.EXIT_LOBBY},
        exitFromLobby
    ))

    function exitFromLobby(){
        const request = new Request(HttpMethod.DELETE, Mapping.EXIT_FROM_LOBBY);
            request.processValidResponse = function(){
                sessionStorage.successMessage = MessageCode.getMessage("EXITED_FROM_LOBBY");
                window.location.href = "/overview";
            }

        dao.sendRequestAsync(request);
    }

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "lobby"));
        loadLobby();
    }

    function loadLobby(){
        const request = new Request(HttpMethod.GET, Mapping.GET_LOBBY);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(lobby){
                lobbyDetails = lobby;
                eventProcessor.processEvent(new Event(events.LOBBY_LOADED));
            }
        dao.sendRequestAsync(request);
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOBBY_LOADED},
        displayGameDetails
    ))

    function displayGameDetails(){
        document.getElementById("game-mode").innerHTML = lobbyDetails.gameMode; //TODO translate gameMode name
        if(lobbyDetails.data){
            $("#game-details-container").show();
            document.getElementById("game-details-name").innerHTML = lobbyDetails.gameMode; //TODO translate gameDetailsName
            document.getElementById("game-details-value").innerHTML = lobbyDetails.data;
        }else{
            $("#game-details-container").hide();
        }
    }
})();