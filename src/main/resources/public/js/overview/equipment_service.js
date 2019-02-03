(function EquipmentService(){
    events.LOAD_EQUIPMENT = "load_equipment";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_EQUIPMENT},
        loadEquipment
    ));
    
    function loadEquipment(){
        const request = new Request(HttpMethod.GET, Mapping.SHIP_DATA);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(ship){
                displayShip(ship);
            }
        dao.sendRequestAsync(request);
    }
    
    function displayShip(shipData){
        showSlots("front-weapon", shipData.weaponSlot.frontSlot, shipData.weaponSlot.frontEquipped);
        showSlots("front-defense", shipData.defenseSlot.frontSlot, shipData.defenseSlot.frontEquipped);
        
        showSlots("left-weapon", shipData.weaponSlot.leftSlot, shipData.weaponSlot.leftEquipped);
        showSlots("left-defense", shipData.defenseSlot.leftSlot, shipData.defenseSlot.leftEquipped);
        
        showSlots("right-weapon", shipData.weaponSlot.rightSlot, shipData.weaponSlot.rightEquipped);
        showSlots("right-defense", shipData.defenseSlot.rightSlot, shipData.defenseSlot.rightEquipped);
        
        showSlots("back-weapon", shipData.weaponSlot.backSlot, shipData.weaponSlot.backEquipped);
        showSlots("back-defense", shipData.defenseSlot.backSlot, shipData.defenseSlot.backEquipped);
        
        showSlots("connectors", shipData.connectorSlot, shipData.connectorEquipped);
        
        fillShipDetails(shipData);
        
        function showSlots(containerId, slotNum, equipped){
            const container = document.getElementById(containerId);
            
            let actual = 0;
            for(let eindex in equipped){
                const equipmentData = itemCache.get(equipped[eindex]);
                
                const slotElement = createSlotElement();
                    slotElement.innerHTML = equipmentData.name;
                    slotElement.title = titleService.getTitleForOverview(equipmentData.id);
                container.appendChild(slotElement);
                actual++;
            }
            
            for(actual; actual < slotNum; actual++){
                const emptySlot = createSlotElement();
                    emptySlot.innerHTML = "Üres";
                container.appendChild(emptySlot);
            }
        }
    }
})();