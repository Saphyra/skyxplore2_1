(function PageController(){
    scriptLoader.loadScript("/js/common/invitation/invitation_controller.js");

    scriptLoader.loadScript("/js/factory/menu_controller.js");
    scriptLoader.loadScript("/js/factory/materials_controller.js");
    scriptLoader.loadScript("/js/factory/money_controller.js");
    scriptLoader.loadScript("/js/factory/queue_controller.js");
    scriptLoader.loadScript("/js/factory/content/content_controller.js");

    $(document).ready(function(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "factory"));
    });

    let materialsLoaded = false;
    let moneyLoaded = false;

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.categoryNamesLoaded
                && materialsLoaded
                && moneyLoaded
        },
        function(){
            eventProcessor.processEvent(new Event(events.DISPLAY_MENU));
        },
        true
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.categoryNamesLoaded
                && LoadState.itemsLoaded
        },
        function(){
            eventProcessor.processEvent(new Event(events.LOAD_MATERIALS));
            eventProcessor.processEvent(new Event(events.LOAD_QUEUE));
        },
        true
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.MATERIALS_LOADED},
        function(){
            materialsLoaded = true;
            eventProcessor.processEvent(new Event(events.LOAD_STATE_CHANGED));
        },
        true
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.MONEY_CHANGED},
        function(){
            moneyLoaded = true;
            eventProcessor.processEvent(new Event(events.LOAD_STATE_CHANGED));
        },
        true
    ));
})();