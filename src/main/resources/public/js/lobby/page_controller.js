(function PageController(){
    scriptLoader.loadScript("/js/common/localization/localization_map.js");
    scriptLoader.loadScript("/js/common/game/game_mode.js");
    scriptLoader.loadScript("/js/common/game/game_mode_translation.js");
    scriptLoader.loadScript("/js/lobby/invitation/invitation_controller.js");
    scriptLoader.loadScript("/js/common/lobby_message/lobby_message_controller.js");
    scriptLoader.loadScript("/js/lobby/member/member_controller.js");
    scriptLoader.loadScript("/js/lobby/event/lobby_event_controller.js");
    scriptLoader.loadScript("/js/common/character_id_query_service.js");

    events.EXIT_LOBBY = "exit_lobby";
    events.LOAD_LOBBY = "load_lobby";
    events.LOBBY_LOADED = "lobby_loaded";
    events.START_QUEUEING = "start_queueing";

    $(document).ready(init);

    let lobbyDetails = null;
    const gameModeLocalization = localizationMapCache.getLocalizationMap("game_mode");

    window.pageController = new function(){
        this.getLobbyDetails = function(){return lobbyDetails};
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOBBY_LOADED},
        function(){
            if(lobbyDetails.ownerId === characterIdQueryService.getCharacterId()){
                $("#owner-control-panel").show();
            }
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOAD_LOBBY},
        function(){
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
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOBBY_LOADED},
        function(){
            document.getElementById("game-mode").innerHTML = gameModeLocalization.getLocalization(lobbyDetails.gameMode);
            if(lobbyDetails.data){
                $("#game-details-container").show();
                document.getElementById("game-details-name").innerHTML = gameModeTranslation.getDataName(lobbyDetails.gameMode);
                document.getElementById("game-details-value").innerHTML = gameModeTranslation.translateData(lobbyDetails.gameMode, lobbyDetails.data);
            }else{
                $("#game-details-container").hide();
            }
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.EXIT_LOBBY},
        function(){
            const request = new Request(HttpMethod.DELETE, Mapping.EXIT_FROM_LOBBY);
                request.processValidResponse = function(){
                    sessionStorage.successMessage = MessageCode.getMessage("EXITED_FROM_LOBBY");
                    window.location.href = Mapping.OVERVIEW_PAGE;
                }

            dao.sendRequestAsync(request);
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.START_QUEUEING},
        function(){
            const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.START_QUEUEING, $("#autofill-enabled").val()));
                request.processValidResponse = function(){
                    eventProcessor.processEvent(new Event(events.LOAD_LOBBY_EVENTS));
                }
            dao.sendRequestAsync(request);
        }
    ));

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "lobby"));
        eventProcessor.processEvent(new Event(events.LOAD_LOBBY));
    }
})();