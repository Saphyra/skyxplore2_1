(function PageController(){
    scriptLoader.loadScript("js/equipment/equipment_service.js");
    
    $(document).ready(function(){
        init();
    });

    let shipLoaded = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return !shipLoaded
                && eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.itemsLoaded
                && LoadState.descriptionLoaded
                && LoadState.messageCodesLoaded;
        },
        function(){
            shipLoaded = true;
            eventProcessor.processEvent(new Event(events.LOAD_SHIP));
        }
    ))

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "equipment"));
    }
})();