$(document).ready(function(){
    scriptLoader.loadScript("js/common/localization.js");
    scriptLoader.loadScript("js/common/logservice.js");
});

(function ScriptLoader(){
    window.scriptLoader = new function(){
        this.loadedScripts = [];
        this.loadScript = loadScript;
    }
    
    scriptLoader.loadScript("js/common/dao.js");
    scriptLoader.loadScript("js/common/event_processor.js");
    
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

function throwException(name, message){
    name = name == undefined ? "" : name;
    message = message == undefined ? "" : message;
    throw {name: name, message: message};
}