(function PageController(){
    scriptLoader.loadScript("js/overview/notification_service.js");
    scriptLoader.loadScript("js/overview/equipment_service.js");
    
    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "overview"));
        eventProcessor.processEvent(new Event(events.LOAD_NOTIFICATIONS));
        eventProcessor.processEvent(new Event(events.LOAD_EQUIPMENT));
    }
})();