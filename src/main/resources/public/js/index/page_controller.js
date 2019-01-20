(function PageController(){
    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "index"));
        scriptLoader.loadScript("js/index/login.js");
        
        $(".login-input").on("keyup", function(e){
            if(e.which == 13){
                eventProcessor.processEvent(new Event(events.LOGIN_ATTEMPT))
            }
        });
    }
})();