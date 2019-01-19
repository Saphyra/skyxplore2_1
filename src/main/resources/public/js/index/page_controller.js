(function PageController(){
    $(document).ready(function(){
        init();
    });
    
    function init(){
        scriptLoader.loadScript("js/index/events.js");
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "index"));
    }
})();