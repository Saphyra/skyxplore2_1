(function PageController(){
    scriptLoader.loadScript("/js/common/invitation/invitation_controller.js");
    scriptLoader.loadScript("/js/common/game/game_mode.js");

    events.CREATE_LOBBY = "create_lobby";

    $(document).ready(function(){
        init();
    });

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.CREATE_LOBBY},
        function(event){
            createLobby(event.getPayload())
        }
    ));

    function createLobby(gameMode){
        let data = null;
        if(gameMode == GameMode.CLAN_WARS){
            data = $("#clan-wars-mode").val();
        }else if(gameMode == GameMode.TEAMFIGHT){
            data = $("#teamfight-size").val();
        }

        const request = new Request(HttpMethod.PUT, Mapping.CREATE_LOBBY, {gameMode: gameMode, data: data});
                request.processValidResponse = function(){
                    window.location.href = "lobby-page";
                }
        dao.sendRequestAsync(request);
    }

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "hangar"));
        $("label").on("click", function(e){e.stopPropagation()});
    }
})();