(function DAO(){
    window.dao = new function(){
        this.GET = "GET";
        this.POST = "POST";
        this.PUT = "PUT";
        this.DELETE = "DELETE";
        
        this.allowedMethods = [this.GET, this.POST, this.PUT, this.DELETE];
        this.sendRequest = sendRequest;
    }
    
    /*
    Sends HttpRequest based on the specified arguments
    Arguments:
        - method: The method of the request.
        - path: The target of the request.
        - content: The body of the request.
    Returns:
        - The sent request.
    Throws:
        - IllegalArgument exception, if method is not a string.
        - IllegalArgument exception, if method is unsupported.
        - IllegalArgument exception, if path is not a string.
    */
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
                content = JSON.stringify(content);
            }
            
            request.open(method, path, 0);
            if(method !== "GET"){
                request.setRequestHeader("Content-Type", "application/json");
            }
            
            request.setRequestHeader("Request-Type", "rest");
            request.send(content);
            if(request.status == 401){
                authService.logout();
            }
        }
        catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error", request.responseURL + " - ");
        }finally{
            return request;
        }
    }
})();