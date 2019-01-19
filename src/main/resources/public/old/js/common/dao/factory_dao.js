(function FactoryDao(){
    window.factoryDao = new function(){
        this.addToQueue = addToQueue;
        this.getMaterials = getMaterials;
        this.getQueue = getQueue;
    }
    
    /*
    Arguments:
        - elementId: the id of the element to add
        - amount: amount to add
    Returns
        - Response object represents the result
    Throws
        - IllegalArgument exception is characterId is null or undefined.
        - IllegalArgument exception is elementId is null or undefined.
        - IllegalArgument exception is amount is null, undefined, or not a number.
    */
    function addToQueue(elementId, amount){
        try{
            if(elementId == null || elementId == undefined){
                throwException("IllegalArgument", "elementId must not be null or undefined");
            }
            if(amount == null || amount == undefined){
                throwException("IllegalArgument", "amount must not be null or undefined");
            }
            if(typeof amount != "number"){
                throwException("IllegalArgument", "amount must be a number. Actual: " + typeof amount);
            }
            
            const path = "factory";
            const content = {
                elementId: elementId,
                amount: amount
            };
            return dao.sendRequest(dao.PUT, path, content);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return new Response();
        }
    }
    
    /*
    Returns:
        - The materials of the character.
        - Empty object upon fail.
    Throws:
        - UnknownServerError if request fail.
    */
    function getMaterials(){
        try{
            const path = "factory/materials";
            const result = dao.sendRequest(dao.GET, path);
            if(result.status == ResponseStatus.OK){
                return JSON.parse(result.response);
            }else{
                throwException("UnknownServerError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return {};
        }
    }
    
    /*
    Queries the factory queue
    Returns:
        - the queue
        - empty object upon fail
    Throws
        - UnknownServerError exception if request fails.
    */
    function getQueue(){
        try{
            
            const path = "factory/queue";
            const result = dao.sendRequest(dao.GET, path);
            if(result.status == ResponseStatus.OK){
                const response = JSON.parse(result.response);
                cache.addAll(response.data);
                return response.info;
            }else{
                throwException("UnknownServerError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return {};
        }
    }
})();