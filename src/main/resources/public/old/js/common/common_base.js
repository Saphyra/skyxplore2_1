$(document).ready(function(){
    scriptLoader.loadScript("js/common/logservice.js");
    scriptLoader.loadScript("js/common/cache.js");
    scriptLoader.loadScript("js/common/dao.js");
    scriptLoader.loadScript("js/common/auth_service.js");
    scriptLoader.loadScript("js/common/notificationservice.js");
});



function getActualTimeStamp(){
    return Math.floor(new Date().getTime() / 1000);
}

function switchTab(clazz, id){
    try{
        $("." + clazz).hide();
        $("#" + id).show();
    }catch(err){
        const message = arguments.callee.name + " - " + err.name + ": " + err.message;
        logService.log(message, "error");
    }
}