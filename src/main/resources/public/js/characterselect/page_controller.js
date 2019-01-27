(function PageController(){
    scriptLoader.loadScript("js/characterselect/create_character/create_character_controller.js");
    
    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "characterselect"));
    }
})();