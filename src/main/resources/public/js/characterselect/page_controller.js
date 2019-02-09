(function PageController(){
    scriptLoader.loadScript("js/characterselect/create_character/create_character_controller.js");
    scriptLoader.loadScript("js/characterselect/character_list/character_list_controller.js");
    scriptLoader.loadScript("js/characterselect/rename_character/rename_character_controller.js");
    scriptLoader.loadScript("js/characterselect/select_character_service.js");
    scriptLoader.loadScript("js/characterselect/delete_character_service.js");

    let charactersLoaded = false;
    
    $(document).ready(function(){
        init();
    });
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return !charactersLoaded
                && eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.messageCodesLoaded;
        },
        function(){
            charactersLoaded = true;
            eventProcessor.processEvent(new Event(events.LOAD_CHARACTERS));
        }
    ))
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "characterselect"));
    }
})();