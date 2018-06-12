(function DataDao(){
    window.dataDao = new function(){
        this.getEquipmentCategories = getEquipmentCategories;
    }
    
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
        }
    }
})();