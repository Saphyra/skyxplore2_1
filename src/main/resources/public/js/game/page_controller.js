(function PageController(){
    scriptLoader.loadScript("/js/game/chat/chat_query_controller.js");

    $(document).ready(function(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "game"));
    });
})();