(function EquipmentService(){
    scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");
    scriptLoader.loadScript("js/common/localization/items.js");
    
    events.LOAD_SHIP = "load_ship";
    
    const items = [];
    let shipType;

    window.shipService = new function(){
        this.isExtenderOfTypeEquipped = isExtenderOfTypeEquipped;
    }
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_SHIP},
        loadShip
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.ITEM_UNEQUIPPED},
        function(event){
            const payload = event.getPayload();
            document.getElementById(payload.getContainerId()).removeChild(payload.getElement());
            document.getElementById(payload.getContainerId()).appendChild(createEmptySlot(payload.getContainerId()));
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.SHIP_EQUIPPED},
        prepareNewShip
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.ITEM_EQUIPPED},
        function(event){
            const payload = event.getPayload();
            equipItem(payload.itemId, payload.containerId);
        }
    ));

    function isExtenderOfTypeEquipped(extendedSlot){
        let result = false;
        for(let iIndex in items){
            const item = items[iIndex];
            const itemData = itemCache.get(item.getId());
            if(itemData.slot === "extender" && itemData.extendedslot === extendedSlot){
                result = true;
                break;
            }
        }
        return result;
    }
    
    function loadShip(){
        const request = new Request(HttpMethod.GET, Mapping.GET_SHIP_DATA);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(shipData){
                displayShip(shipData);
                shipType = shipData.shipType;
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
                container.innerHTML = "";
            
            let actual = 0;
            for(let eindex in equipped){
                const itemId = equipped[eindex];
                
                container.appendChild(createEquippedSlotElement(containerId, itemId));
                actual++;
            }
            
            for(actual; actual < slotNum; actual++){
                container.appendChild(createEmptySlot(containerId));
            }
        }
        
        function fillShipDetails(shipData){
            document.getElementById("ship-details").innerHTML = "";
            document.getElementById("ship-details").appendChild(equipmentLabelService.createShipDetails(shipData));
        }
    }
    
    function prepareNewShip(event){
        const oldShipType = shipType;
        eventProcessor.processEvent(new Event(
            events.ADD_TO_STORAGE,
            {getId: function(){return oldShipType;}}
        ));
        shipType = event.getPayload();
        loadShip();
    }

    function equipItem(itemId, containerId){
        $(createEquippedSlotElement(containerId, itemId)).insertBefore(getFirstEmptySlotInContainer(containerId));
        getFirstEmptySlotInContainer(containerId).remove();

        function getFirstEmptySlotInContainer(containerId){
            return $("#" + containerId + " .empty-slot").first();
        }
    }

    function createEquippedSlotElement(containerId, itemId){
        const slotElement = createSlotElement();
            slotElement.innerHTML = Items.getItem(itemId).name;
            slotElement.title = equipmentLabelService.assembleTitleOfItem(itemId);

            const equippedItem = new EquippedItem(containerId, itemId, slotElement);
            items.push(equippedItem);

            slotElement.onclick = function(){
                eventProcessor.processEvent(new Event(
                    events.UNEQUIP_ITEM,
                    equippedItem
                ));
            }
        return slotElement;
    }

    function createEmptySlot(containerId){
        const emptySlot = createSlotElement();
            emptySlot.innerHTML = Localization.getAdditionalContent("empty-slot");
            emptySlot.classList.add("empty-slot");
            emptySlot.setAttribute("parent-id", containerId);
        return emptySlot;
    }
    
    function createSlotElement(){
        const element = document.createElement("DIV");
            element.classList.add("slot");
        return element;
    }
    
    function EquippedItem(container, item, elem){
        const containerId = container;
        const itemId = item;
        const itemContainerElement = elem;
        
        this.getId = function(){
            return itemId;
        }
        
        this.getContainerId = function(){
            return containerId;
        }
        
        this.getElement = function(){
            return itemContainerElement;
        }
    }
})();