(function ShipController(){
    window.shipController = new function(){
        scriptLoader.loadScript("js/common/dao/ship_dao.js");
        scriptLoader.loadScript("js/common/translator/title_service.js");
        
        this.shipData = null;
        
        this.loadShipEquipment = loadShipEquipment;
        this.showShipEquipment = showShipEquipment;
    }
    
    function loadShipEquipment(){
        try{
            shipController.shipData = shipDao.getShip(sessionStorage.characterId);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Displays the equipped equipments of the ship.
    Throws:
        - IllegalState exception, if shipController..shipData is null
    */
    function showShipEquipment(){
        try{
            if(shipController.shipData == null){
                throwException("IllegalState", "shipData must not be null.");
            }
            
            showSlots("frontweapon", shipController.shipData.weaponSlot.frontSlot, shipController.shipData.weaponSlot.frontEquipped);
            showSlots("frontdefense", shipController.shipData.defenseSlot.frontSlot, shipController.shipData.defenseSlot.frontEquipped);
            
            showSlots("leftweapon", shipController.shipData.weaponSlot.leftSlot, shipController.shipData.weaponSlot.leftEquipped);
            showSlots("leftdefense", shipController.shipData.defenseSlot.leftSlot, shipController.shipData.defenseSlot.leftEquipped);
            
            showSlots("rightweapon", shipController.shipData.weaponSlot.rightSlot, shipController.shipData.weaponSlot.rightEquipped);
            showSlots("rightdefense", shipController.shipData.defenseSlot.rightSlot, shipController.shipData.defenseSlot.rightEquipped);
            
            showSlots("backweapon", shipController.shipData.weaponSlot.backSlot, shipController.shipData.weaponSlot.backEquipped);
            showSlots("backdefense", shipController.shipData.defenseSlot.backSlot, shipController.shipData.defenseSlot.backEquipped);
            
            showSlots("connectors", shipController.shipData.connectorSlot, shipController.shipData.connectorEquipped);
            
            fillShipDetails(shipController.shipData);
            
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
                        slotElement.title = titleService.getTitleForOverview(equipmentData.id);
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
                
                    const shipDetailsContainer = document.createElement("DIV");
                        shipDetailsContainer.classList.add("border2px");
                        shipDetailsContainer.classList.add("bordercoloraaa");
                        shipDetailsContainer.classList.add("borderbottomridge");
                        shipDetailsContainer.classList.add("fontsize1_25rem");
                        shipDetailsContainer.classList.add("padding0_25rem");
                        shipDetailsContainer.title = titleService.getTitleForOverview(shipData.shipType);
                        
                        const coreHullContainer = document.createElement("DIV");
                            coreHullContainer.innerHTML = "Magburkolat: " + shipData.coreHull;
                    shipDetailsContainer.appendChild(coreHullContainer);
                    
                        const energyContainer = document.createElement("DIV");
                            const energy = countEnergy(shipData.connectorEquipped);
                            const energyRegeneration = countEnergyRegen(shipData.connectorEquipped);
                            energyContainer.innerHTML = "Energia: " + energy + " - Regeneráció: " + energyRegeneration;
                    shipDetailsContainer.appendChild(energyContainer);
                    
                        const storageContainer = document.createElement("DIV");
                            const storage = countStorage(shipData.connectorEquipped);
                            storageContainer.innerHTML = "Raktér: " + storage;
                    shipDetailsContainer.appendChild(storageContainer);
                    
                container.appendChild(shipDetailsContainer);
                
                    const abilityTitle = document.createElement("DIV");
                        abilityTitle.innerHTML = "Képességek";
                        abilityTitle.classList.add("fontsize1_5rem");
                container.appendChild(abilityTitle);
                
                    const abilityContainer = document.createElement("DIV");
                        abilityContainer.classList.add("inlineblock");
                        abilityContainer.classList.add("textalignleft");
                        
                        for(let aindex in shipData.ability){
                            const abilityData = cache.get(shipData.ability[aindex]);
                            const abilityElement = document.createElement("DIV");
                                abilityElement.classList.add("border2px");
                                abilityElement.classList.add("bordercoloraaa");
                                abilityElement.classList.add("borderridge");
                                abilityElement.classList.add("fontsize1_25rem");
                                abilityElement.classList.add("margin0_25rem");
                                abilityElement.classList.add("padding0_25rem");
                                abilityElement.innerHTML = abilityData.name;
                                abilityElement.title = titleService.getTitleForOverview(abilityData.id);
                            abilityContainer.appendChild(abilityElement);
                        }
                        
                container.appendChild(abilityContainer);
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
            
            function countEnergy(connectors){
                try{
                    let result = 0;
                    for(let cindex in connectors){
                        const connectorData = cache.get(connectors[cindex]);
                        if(connectorData.type == "battery"){
                            result += connectorData.capacity;
                        }
                    }
                    
                    return result;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                }
            }
            
            function countEnergyRegen(connectors){
                try{
                    let result = 0;
                    for(let cindex in connectors){
                        const connectorData = cache.get(connectors[cindex]);
                        if(connectorData.type == "generator"){
                            result += connectorData.energyrecharge;
                        }
                    }
                    
                    return result;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                }
            }
            
            function countStorage(connectors){
                try{
                    let result = 0;
                    for(let cindex in connectors){
                        const connectorData = cache.get(connectors[cindex]);
                        if(connectorData.type == "storage"){
                            result += connectorData.capacity;
                        }
                    }
                    
                    return result;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                }
            }
        }
    }
})()