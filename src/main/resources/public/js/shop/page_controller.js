(function PageController(){
    scriptLoader.loadScript("js/shop/menu_controller.js");
    scriptLoader.loadScript("js/shop/content_controller.js");
    
    $(document).ready(function(){
        init();
    });
    
    let menuLoaded = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return !menuLoaded
                && eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.categoryNamesLoaded
                && LoadState.descriptionLoaded
                && LoadState.itemsLoaded
        },
        function(){
            menuLoaded = true;
            eventProcessor.processEvent(new Event(events.DISPLAY_MENU));
        }
    ));

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "shop"));
    }
})();