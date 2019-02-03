(function PageController(){
    scriptLoader.loadScript("js/common/cache.js");
    scriptLoader.loadScript("js/overview/notification_service.js");
    scriptLoader.loadScript("js/overview/equipment_service.js");
    
    window.itemCache = new Cache(loadItem);
    
    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "overview"));
        eventProcessor.processEvent(new Event(events.LOAD_NOTIFICATIONS));
        eventProcessor.processEvent(new Event(events.LOAD_EQUIPMENT));
    }
    
    function loadItem(itemId){
        const response = dao.sendRequest(HttpMethod.GET, "gamedata/" + itemId + ".json");
        if(response.status == ResponseStatus.OK){
            return JSON.parse(response.body);
        }else{
            throwException("UnknownResponse", response.toString());
        }
    }
})();