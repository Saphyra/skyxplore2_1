(function DataDao(){
    window.dataDao = new function(){
        scriptLoader.loadScript("js/common/dao/response_status_mapper.js");
        
        this.getCategoryEquipments = getCategoryEquipments;
        this.getEquipmentCategories = getEquipmentCategories;
    }
    
    /*
    Queries the elements of the given type.
    Arguments:
        - type: the type to query
    Returns:
        - the elements of the type
        - empty list upon fail
    Throws
        - IllegalArgument exception if type is null or undefined.
        - UnknownBackendError if request fails.
    */
    function getCategoryEquipments(type){
        try{
            if(type == null || type == undefined){
                throwException("IllegalArgument", "type must not be null or undefined.");
            }
            
            const path = "data/equipment/category/" + type;
            const result = dao.sendRequest(dao.GET, path);
            if(result.status == 200){
                return JSON.parse(result.responseText);
            }else{
                throwException("UnknownBackendError", result.status + " - " + result.responseText);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    Queries the category data from the server.
    Returns:
        - the categories
        - empty list upon fail
    Throws:
        - UnknownBackendError exception if request fails
    */
    function getEquipmentCategories(){
        try{
            const path = "data/equipment/category";
            const result = dao.sendRequest(dao.GET, path);
            
            if(result.status == 200){
                return JSON.parse(result.responseText);
            }else{
                throwException("UnknownBackendError", result.status + " - " + result.responseText);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
})();