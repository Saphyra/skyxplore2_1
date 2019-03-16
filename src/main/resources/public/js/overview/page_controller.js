(function PageController(){
    scriptLoader.loadScript("js/overview/notification_service.js");
    scriptLoader.loadScript("js/overview/equipment_service.js");
    
    $(document).ready(function(){
        init();
    });
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.itemsLoaded
                && LoadState.descriptionLoaded
                && LoadState.messageCodesLoaded;
        },
        function(){
            eventProcessor.processEvent(new Event(events.LOAD_EQUIPMENT));
        },
        true
    ))
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "overview"));
        setIntervalImmediate(
            function(){eventProcessor.processEvent(new Event(events.LOAD_NOTIFICATIONS))},
            20000
        );
    }
})();