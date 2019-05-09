(function PageController(){
    events.EXIT_LOBBY = "exit_lobby";

    $(document).ready(init);

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
    }
})();