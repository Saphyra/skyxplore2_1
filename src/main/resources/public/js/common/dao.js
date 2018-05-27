(function DAO(){
    window.dao = new function(){
        this.allowedMethods = ["GET", "POST", "PUT", "DELETE"];
        this.sendRequest = sendRequest;
    }
    
    function sendRequest(method, path, content){
        const request = new XMLHttpRequest();
        try{
            if(!method || typeof method !== "string"){
                throwException("IllegalArgument", "method must be a string.");
            }
            method = method.toUpperCase();
            if(this.allowedMethods.indexOf(method) == -1){
                throwException("IllegalArgument", "Unsupported method: " + method);
            }
            if(!path || typeof path !== "string"){
                throwException("IllegalArgument", "path must be a string.");
            }
            
            content = content || "";
            if(typeof content === "object"){
                content = stringifyContent(content);
            }
            
            request.open(method, path, 0);
            if(method === "POST"){
                request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            }
            request.send(content);
            validateResponse(request);
        }
        catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error", request.responseURL + " - ");
        }finally{
            return request.responseText;
        }
        
        function stringifyContent(content){
            try{
                let result = "";
                let start = true;
                    for(let index in content){
                        if(!start){
                            result += "&";
                        }
                        result += index + "=" + content[index];
                        if(start){
                            start = false;
                        }
                    }
                return result;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error", request.responseURL + " - ");
            }
        }
        
        function validateResponse(request){
            try{
                if(request.status != 200){
                    switch(request.status){
                        case 400:
                            throwException("400 - BadRequest", request.responseText);
                        break;
                        case 401:
                            window.location.href = "/";
                        break;
                        case 404:
                            throwException("404 - NotFound", request.responseText);
                        break;
                        case 500:
                            throwException("500 - InternalServerError", request.responseText);
                        break;
                        default:
                            throwException("Unknown HTTP error: " + request.status, request.responseText);
                        break;
                    }
                }
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error", request.responseURL + " - ");
            }
        }
    }
})();