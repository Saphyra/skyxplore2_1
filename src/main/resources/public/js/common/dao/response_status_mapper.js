(function ResponseStatusMapper(){
    window.responseStatusMapper = new function(){
        this.getKeyOf = getKeyOf;
    }
    
    /*
    Gets the key of the given status code.
    Arguments: 
        - statusCode: the status code
    Returns:
        - The key of the given status code
    Throws:
        - IllegalArgument exception if statusCode is null, undefined, or not a number.
        - KeyNotFound exception if key not found.
    */
    function getKeyOf(statusCode){
        try{
            if(statusCode == null || statusCode == undefined){
                throwException("IllegalArgument", "statusCode must not be null or undefined");
            }
            if(typeof statusCode != "number"){
                throwException("IllegalArgument", "statusCode must be a number");
            }
            
            for(let key in ResponseStatus){
                if(ResponseStatus[key] == statusCode){
                    return key;
                }
            }
            
            throwException("KeyNotFound", "No key found for status code " + statusCode);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return null;
        }
    }
})();

/*
Enumeration contains response status codes for HttpRequest
*/
window.ResponseStatus = new function(){
    this.OK = 200;
    this.BAD_REQUEST = 400;
    this.UNAUTHORIZED = 401;
}

/*
Response object contains the response status, statusKey, and text of the qiven request.
*/
function Response(response){
    const statusKey = responseStatusMapper.getKeyOf(response.status);
    
    this.statusKey = statusKey;
    this.status = response.status;
    this.result = response.responseText;
    
    this.toString = function(){
        return this.status + ": " + this.status + " - " + this.result;
    }
}