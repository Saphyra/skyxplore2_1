(function EquipmentController(){
    window.equipmentController = new function(){
        scriptLoader.loadScript("js/common/dao/ship_dao.js");
        
        this.showEquipment = showEquipment;
        
        $(document).ready(function(){
            showEquipment();
        });
    }
    
    /*
    Displays the equipped equipments of the ship.
    Throws:
        - IllegalState exception, if characterId cannot be found.
    */
    function showEquipment(){
        try{
            const characterId = sessionStorage.characterId;
            if(characterId == null || characterId == undefined){
                throwException("IllegalState", "characterId cannot be found.");
            }
            const shipData = shipDao.getShip(characterId);
            
            showSlots("frontweapon", shipData.weaponSlot.frontSlot, shipData.weaponSlot.frontEquipped);
            showSlots("frontdefense", shipData.defenseSlot.frontSlot, shipData.defenseSlot.frontEquipped);
            
            showSlots("leftweapon", shipData.weaponSlot.leftSlot, shipData.weaponSlot.leftEquipped);
            showSlots("leftdefense", shipData.defenseSlot.leftSlot, shipData.defenseSlot.leftEquipped);
            
            showSlots("rightweapon", shipData.weaponSlot.rightSlot, shipData.weaponSlot.rightEquipped);
            showSlots("rightdefense", shipData.defenseSlot.rightSlot, shipData.defenseSlot.rightEquipped);
            
            showSlots("backweapon", shipData.weaponSlot.backSlot, shipData.weaponSlot.backEquipped);
            showSlots("backdefense", shipData.defenseSlot.backSlot, shipData.defenseSlot.backEquipped);
            
            showSlots("connectors", shipData.connectorSlot, shipData.connectorEquipped);
            
            fillShipDetails(shipData);
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function showSlots(containerId, slotNum, equipped){
            try{
                const container = document.getElementById(containerId);
                
                let actual = 0;
                for(let eindex in equipped){
                    const equipmentData = cache.get(equipped[eindex]);
                    
                    const slotElement = createSlotElement();
                        slotElement.innerHTML = equipmentData.name;
                    container.appendChild(slotElement);
                    actual++;
                }
                
                for(actual; actual < slotNum; actual++){
                    const emptySlot = createSlotElement();
                        emptySlot.innerHTML = "Üres";
                    container.appendChild(emptySlot);
                }
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
            
            function createSlotElement(){
                try{
                    const element = document.createElement("DIV");
                        element.classList.add("slot");
                    return element;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                }
            }
        }
        
        function fillShipDetails(shipData){
            try{
                const container = document.getElementById("shipdetails");
                
                container.appendChild(document.createTextNode(shipData.coreHull));
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }
})();