(function PageController(){
    scriptLoader.loadScript("/js/game/chat/chat_query_controller.js");
    scriptLoader.loadScript("/js/game/chat/message_sender_service.js");

    $(document).ready(function(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "game"));
    });
})();