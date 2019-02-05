(function ScriptLoader(){
    window.scriptLoader = new function(){
        this.loadedScripts = [];
        this.loadScript = loadScript;
    }
    
    scriptLoader.loadScript("js/common/utils.js");
    scriptLoader.loadScript("js/common/log_service.js");
    scriptLoader.loadScript("js/common/dao/mapping.js");
    scriptLoader.loadScript("js/common/dao/dao.js");
    scriptLoader.loadScript("js/common/events.js");
    scriptLoader.loadScript("js/common/event_processor.js");
    scriptLoader.loadScript("js/common/notification_service.js");
    
    scriptLoader.loadScript("js/common/localization/message_code.js");
    scriptLoader.loadScript("js/common/localization/localization.js");
    scriptLoader.loadScript("js/common/logout_service.js");
    
    /*
        Loads the script given as argument.
        Arguments:
            - src: The path of the requested script.
        Throws
            - IllegalArgument exception if src is null ord undefined
            - IllegalState exception if jQuery cannot be found.
    */
    function loadScript(src){
        if(src == undefined || src == null){
            throwException("IllegalArgument", "src must not be null or undefined.");
        }
        
        if(this.loadedScripts.indexOf(src) > -1){
            return;
        }
        
        if($ == undefined){
            throwException("IllegalState", "jQuery cannot be resolved.");
        }
        $.ajax({
            async: false,
            url: src,
            dataType: "script",
            cache: true
        });
        this.loadedScripts.push(src);
    }
})();