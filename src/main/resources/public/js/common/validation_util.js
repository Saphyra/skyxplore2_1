function createSuccessProcess(id){
    return function(){
        logService.logToConsole("Running successProcess for id " + id);
        $(id).fadeOut();
    }
}

function createErrorProcess(id, code){
    return function errorProcess(){
        logService.logToConsole("Running successProcess for id " + id + " and code " + code);
        $(id).prop("title", MessageCode.getMessage(code))
            .fadeIn();
    }
}

function getValidationTimeout(){
    const presetTimeout = getCookie("validation-timeout");
    return presetTimeout == null ? 1000 : Number(presetTimeout);
}