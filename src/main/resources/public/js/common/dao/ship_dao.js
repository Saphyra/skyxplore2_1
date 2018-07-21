(function ShipDao(){
    window.shipDao = new function(){
        scriptLoader.loadScript("js/common/dao/response_status_mapper.js");
        
        this.getShip = getShip;
        this.unequip = unequip;
    }
    
    /*
    Queries the ship of the given user.
    Arguments:
        - characterId: The id of the character.
    Returns:
        - The equip info of the ship.
        - The data of equipped equipments.
    Throws:
        - IllegalArgument exception if characterId is null.
        - UnknownBackendError exception if request failed.
    */
    function getShip(characterId){
        try{
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined");
            }
            
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
    
    /*
    Unequips the selected item.
    Arguments:
        slot: the ship's slot.
        itemId: the id of the item to unequip.
        characterId: the id of the character.
    Returns:
        true, if the item is successfully unequipped.
        false otherwise.
    Throws
        IllegalArgument exception if slot, itemId, or characterId is null or undefined.
        UnknownBackendError if request fails.
    */
    function unequip(slot, itemId, characterId){
        try{
            if(slot == null || slot == undefined){
                throwException("IllegalArgument", "slot must not be null or undefined.");
            }
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined");
            }
            if(itemId == null || itemId == undefined){
                throwException("IllegalArgument", "itemId must not be null or undefined");
            }
            
            const path = "ship/unequip/" + characterId;
            const body = {
                slot: slot,
                itemId: itemId
            };
            const response = dao.sendRequest(dao.POST, path, body);
            if(response.status == 200){
                return true;
            }else{
                throwException("UnknownBackendError", new Response(response).toString());
                return false;
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();