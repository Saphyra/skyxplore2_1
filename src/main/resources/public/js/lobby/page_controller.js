(function PageController(){
    scriptLoader.loadScript("js/common/localization/localization_map.js");
    scriptLoader.loadScript("js/common/game/game_mode.js");

    events.EXIT_LOBBY = "exit_lobby";
    events.LOBBY_LOADED = "lobby_loaded";

    $(document).ready(init);

    let lobbyDetails = null;
    let gameModeLocalization = new LocalizationMap("game_mode");

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
        document.getElementById("game-mode").innerHTML = gameModeLocalization.getLocalization(lobbyDetails.gameMode);
        if(lobbyDetails.data){
            $("#game-details-container").show();
            document.getElementById("game-details-name").innerHTML = getDataName(lobbyDetails.gameMode);
            document.getElementById("game-details-value").innerHTML = translateData(lobbyDetails.gameMode, lobbyDetails.data);
        }else{
            $("#game-details-container").hide();
        }

        function getDataName(gameMode){
            switch(gameMode){
                case GameMode.CLAN_WARS:
                    return gameModeLocalization.getLocalization("CLAN_WARS_DATA");
                break;
                case GameMode.TEAMFIGHT:
                    return gameModeLocalization.getLocalization("TEAMFIGHT_DATA");
                break;
                default:
                    throwException("IllegalArgument", gameMode + " has no data localization");
                break;
            }
        }

        function translateData(gameMode, data){
            switch(gameMode){
                case GameMode.CLAN_WARS:
                    return gameModeLocalization.getLocalization(data);
                break;
                default:
                    return data;
                break;
            }
        }
    }
})();