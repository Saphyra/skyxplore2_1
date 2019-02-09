(function PageController(){
    scriptLoader.loadScript("js/shop/menu_controller.js");
    scriptLoader.loadScript("js/shop/content_controller.js");
    scriptLoader.loadScript("js/shop/money_controller.js");
    scriptLoader.loadScript("js/shop/cart_controller.js");
    
    $(document).ready(function(){
        init();
    });
    
    let menuLoaded = false;
    let moneyLoaded = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return !menuLoaded
                && eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.categoryNamesLoaded
                && LoadState.descriptionLoaded
                && LoadState.itemsLoaded
                && moneyLoaded
        },
        function(){
            menuLoaded = true;
            eventProcessor.processEvent(new Event(events.DISPLAY_MENU));
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.MONEY_CHANGED},
        function(){
            moneyLoaded = true;
            eventProcessor.processEvent(new Event(events.LOAD_STATE_CHANGED));
        }
    ));

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "shop"));
    }
})();