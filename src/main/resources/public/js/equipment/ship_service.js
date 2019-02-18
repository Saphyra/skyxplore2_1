(function EquipmentService(){
    scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");
    scriptLoader.loadScript("js/common/localization/items.js");
    
    events.LOAD_SHIP = "load_ship";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_SHIP},
        loadShip
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.ITEM_UNEQUIPPED},
        function(event){
            const payload = event.getPayload();
            document.getElementById(payload.containerId).removeChild(payload.element);
            document.getElementById(payload.containerId).appendChild(createEmptySlot());
        }
    ));
    
    function loadShip(){
        const request = new Request(HttpMethod.GET, Mapping.GET_SHIP_DATA);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(shipData){
                displayShip(shipData)
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
                const itemId = equipped[eindex];
                
                container.appendChild(createEquippedSlotElement(containerId, itemId));
                actual++;
            }
            
            for(actual; actual < slotNum; actual++){
                container.appendChild(createEmptySlot());
            }
            
            function createEquippedSlotElement(containerId, itemId){
                const slotElement = createSlotElement();
                    slotElement.innerHTML = Items.getItem(itemId).name;
                    slotElement.title = equipmentLabelService.assembleTitleOfItem(itemId);
                    slotElement.onclick = function(){
                        eventProcessor.processEvent(new Event(
                            events.UNEQUIP_ITEM,
                            {containerId: containerId, itemId: itemId, element: slotElement}
                        ));
                    }
                return slotElement;
            }
        }
        
        function fillShipDetails(shipData){
            document.getElementById("ship-details").appendChild(equipmentLabelService.createShipDetails(shipData));
        }
    }
    
    function createEmptySlot(){
        const emptySlot = createSlotElement();
            emptySlot.innerHTML = Localization.getAdditionalContent("empty-slot");
            emptySlot.classList.add("empty-slot");
        return emptySlot;
    }
    
    function createSlotElement(){
        const element = document.createElement("DIV");
            element.classList.add("slot");
        return element;
    }
})();