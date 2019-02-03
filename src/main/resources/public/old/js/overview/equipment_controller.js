(function EquipmentController(){
    window.equipmentController = new function(){
        scriptLoader.loadScript("js/common/dao/ship_dao.js");
        scriptLoader.loadScript("js/common/translator/title_service.js");
        
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
        function showSlots(containerId, slotNum, equipped){
            try{
                
                
                
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
})();