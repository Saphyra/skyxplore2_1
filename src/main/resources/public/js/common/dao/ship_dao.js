(function ShipDao(){
    window.shipDao = new function(){
        this.equip = equip;
        this.equipShip = equipShip;
        this.getShip = getShip;
        this.unequip = unequip;
    }
    
    /*
    Equips the selected item.
    Arguments:
        - itemId: The id of the item to equip.
        - equipTo: The id of the slot to equip.
        - characterId: The id of the character.
    Returns:
        - true, if the equipment was successful.
        - false otherwise.
    Throws:
        - IllegalArgument exception if itemId, equipTo or characterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function equip(itemId, equipTo, characterId){
        try{
            if(itemId == null || itemId == undefined){
                throwException("IllegalArgument", "itemId must not be null or undefined.");
            }
            if(equipTo == null || equipTo == undefined){
                throwException("IllegalArgument", "equipTo must not be null or undefined.");
            }
            if(characterId == null || characterId == undefined){
                throwException("IllegalArgument", "characterId must not be null or undefined.");
            }
            
            const path = "ship/equip/" + characterId;
            const body = {
                equipTo: equipTo,
                itemId: itemId
            };
            const response = dao.sendRequest(dao.POST, path, body);
            if(response.status == ResponseStatus.OK){
                return true;
            }else{
                return false;
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
    
    /*
    Sends a ship equip request to the server.
    Arguments:
        - itemId: the id of the ship to equip.
        - characterId: the id of the character.
    Returns:
        - true, if the ship equipment was successful.
        - false otherwise.
    Throws:
        - IllegalArgument exception if itemId or characterId is null or undefined.
        - UnknownBackendError exception if request fails.
    */
    function equipShip(itemId, characterId){
        try{
            if(itemId == null || itemId == undefined){
                throwException("itemId must not be null or undefined.");
            }
            if(characterId == null || characterId == undefined){
                throwException("characterId must not be null or undefined.");
            }
            
            const path = "ship/equipship/" + characterId + "/shipid/" + itemId;
            const response = dao.sendRequest(dao.POST, path);
            if(response.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
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
            if(result.status == ResponseStatus.OK){
                const parsed = JSON.parse(result.response);
                cache.addAll(parsed.data);
                return parsed.info;
            }else{
                throwException("UnknownBackendError", result.toString());
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
            if(response.status == ResponseStatus.OK){
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