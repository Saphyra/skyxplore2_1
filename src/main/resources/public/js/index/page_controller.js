(function PageController(){
    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "index"));
        scriptLoader.loadScript("js/index/login.js");
        scriptLoader.loadScript("js/index/register.js");
        scriptLoader.loadScript("js/index/input_validator.js");
        
        $(".login-input").on("keyup", function(e){
            if(e.which == 13){
                eventProcessor.processEvent(new Event(events.LOGIN_ATTEMPT));
            }
        });
        
        $(".reg-input").on("keyup", function(e){
            if(e.which == 13){
                eventProcessor.processEvent(new Event(events.REGISTER_ATTEMPT));
            }else{
                eventProcessor.processEvent(new Event(events.VALIDATION_ATTEMPT));
            }
        });
        $(".reg-input").on("focusin", function(){
            eventProcessor.processEvent(new Event(events.VALIDATION_ATTEMPT));
        });
    }
})();