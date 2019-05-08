(function PageController(){
    $(document).ready(init);

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "lobby"));
    }
})();