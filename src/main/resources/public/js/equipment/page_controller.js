(function PageController(){
    scriptLoader.loadScript("js/equipment/ship_service.js");
    scriptLoader.loadScript("js/equipment/storage_service.js");
    scriptLoader.loadScript("js/equipment/equip_service.js");
    
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
            eventProcessor.processEvent(new Event(events.LOAD_SHIP));
        },
        true
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.itemsLoaded
                && LoadState.descriptionLoaded
                && LoadState.messageCodesLoaded;
        },
        function(){
            eventProcessor.processEvent(new Event(events.LOAD_STORAGE));
        },
        true
    ));

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "equipment"));
    }
})();