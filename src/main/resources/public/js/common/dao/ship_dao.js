(function ShipDao(){
    window.shipDao = new function(){
        this.getShip = getShip;
    }
    
    /*
    Queries the ship of the given user.
    Arguments:
        - characterId: The id of the character.
    Returns:
        - The equip info of the ship.
        - The data of equipped equipments.
    Throws:
        - UnknownBackendError exception if request failed.
    */
    function getShip(characterId){
        try{
            const path = "ship/" + characterId;
            const result = dao.sendRequest("GET", path);
            if(result.status == 200){
                const parsed = JSON.parse(result.responseText);
                cache.addAll(parsed.data);
                return parsed.info;
            }else{
                throwException("UnknownBackendError", result.status + " - " + result.responseText);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();