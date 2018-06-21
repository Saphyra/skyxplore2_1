(function FactoryDao(){
    window.factoryDao = new function(){
        scriptLoader.loadScript("js/common/dao/response_status_mapper.js");
        this.getMaterials = getMaterials;
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
            const path = "factory/materials/" + sessionStorage.characterId;
            const result = dao.sendRequest(dao.GET, path);
            if(result.status == 200){
                return JSON.parse(result.responseText);
            }else{
                throwException("UnknownServerError", new Response(result).toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return {};
        }
    }
})();