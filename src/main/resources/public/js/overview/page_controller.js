(function PageController(){
    scriptLoader.loadScript("js/overview/notification_service.js");
    scriptLoader.loadScript("js/overview/equipment_service.js");
    
    $(document).ready(function(){
        init();
    });
    
    let equipmentLoaded = false;
    
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
    ))
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "overview"));
        eventProcessor.processEvent(new Event(events.LOAD_NOTIFICATIONS));
    }
})();