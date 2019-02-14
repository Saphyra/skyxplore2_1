(function PageController(){
    scriptLoader.loadScript("js/equipment/ship_service.js");
    scriptLoader.loadScript("js/equipment/equipment_service.js");
    
    $(document).ready(function(){
        init();
    });

    let shipLoaded = false;
    let equipmentLoaded = false;
    
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
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return !equipmentLoaded
                && eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.itemsLoaded
                && LoadState.descriptionLoaded
                && LoadState.messageCodesLoaded;
        },
        function(){
            equipmentLoaded = true;
            eventProcessor.processEvent(new Event(events.LOAD_EQUIPMENT));
        }
    ));

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "equipment"));
    }
})();