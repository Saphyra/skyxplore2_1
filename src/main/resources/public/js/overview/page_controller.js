(function PageController(){
    scriptLoader.loadScript("/js/common/invitation/invitation_controller.js");

    scriptLoader.loadScript("/js/overview/notification_service.js");
    scriptLoader.loadScript("/js/overview/equipment_service.js");
    
    $(document).ready(function(){
        init();
    });

    events.DESELECT_CHARACTER = "deselect_character";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
                && LoadState.itemsLoaded
                && LoadState.descriptionLoaded
                && LoadState.messageCodesLoaded;
        },
        function(){
            eventProcessor.processEvent(new Event(events.LOAD_EQUIPMENT));
        },
        true
    ))

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DESELECT_CHARACTER},
        function(){
            const response = dao.sendRequest(HttpMethod.DELETE, Mapping.DESELECT_CHARACTER);
            if(response.status == ResponseStatus.OK){
                window.location.href = Mapping.CHARACTER_SELECT_PAGE;
            }else {
                throwException("UnknownBackendResponse", response.toString());
            }
        }
    ));
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "overview"));
        setIntervalImmediate(
            function(){eventProcessor.processEvent(new Event(events.LOAD_NOTIFICATIONS))},
            20000
        );
    }
})();