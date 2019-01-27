(function PageController(){
    scriptLoader.loadScript("js/characterselect/create_character/create_character_controller.js");
    scriptLoader.loadScript("js/characterselect/character_list/character_list_controller.js");
    scriptLoader.loadScript("js/characterselect/select_character_service.js");
    
    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "characterselect"));
        eventProcessor.processEvent(new Event(events.LOAD_CHARACTERS));
    }
})();