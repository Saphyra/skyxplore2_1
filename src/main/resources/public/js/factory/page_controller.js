(function PageController(){
    scriptLoader.loadScript("js/factory/menu_controller.js");

    $(document).ready(function(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "factory"));
    });

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.categoryNamesLoaded
        },
        function(){
            eventProcessor.processEvent(new Event(events.DISPLAY_MENU));
        },
        true
    ));
})();