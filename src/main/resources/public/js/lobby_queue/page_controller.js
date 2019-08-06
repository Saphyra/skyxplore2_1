(function PageController(){
    scriptLoader.loadScript("/js/common/lobby_message/lobby_message_controller.js");

    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "lobby-queue"));
        setInterval(checkCharacterStatus, 5000);
    }

    function checkCharacterStatus(){
        const request = new Request(HttpMethod.GET, Mapping.GET_CHARACTER_STATUS);
        request.processValidResponse = function(response){
            if(response.body === "IN_GAME"){
                window.location.href = Mapping.GAME_PAGE;
            }
        }
        dao.sendRequestAsync(request);
    }
})();