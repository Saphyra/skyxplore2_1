(function PageController(){
    scriptLoader.loadScript("js/community/notification_controller.js");

    $(document).ready(function(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "community"));
    });
})();
