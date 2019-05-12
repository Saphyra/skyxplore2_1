(function PageController(){
    scriptLoader.loadScript("js/common/invitation/invitation_controller.js");

    scriptLoader.loadScript("js/shop/menu_controller.js");
    scriptLoader.loadScript("js/shop/content_controller.js");
    scriptLoader.loadScript("js/shop/money_controller.js");
    scriptLoader.loadScript("js/shop/cart_controller.js");
    
    let moneyLoaded = false;

    $(document).ready(function(){
        init();
    });
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.categoryNamesLoaded
                && LoadState.descriptionLoaded
                && LoadState.itemsLoaded
                && moneyLoaded
        },
        function(){
            eventProcessor.processEvent(new Event(events.DISPLAY_MENU));
        },
        true
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