(function PageController(){
    scriptLoader.loadScript("js/community/notification_controller.js");
    scriptLoader.loadScript("js/community/write_mail_controller.js");

    events.OPEN_WRITE_MAIL_WINDOW = "open_write_mail_window";
    events.OPEN_MAIN_LISTS = "open_mail_lists";

    $(document).ready(function(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "community"));
    });

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_WRITE_MAIL_WINDOW},
        function(){
            switchTab("main-tab", "main-write-mail");
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
            function(eventType){return eventType === events.OPEN_MAIN_LISTS},
            function(){
                switchTab("main-tab", "main-lists");
            }
        ));
})();
