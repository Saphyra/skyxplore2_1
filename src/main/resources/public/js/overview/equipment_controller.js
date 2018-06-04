(function EquipmentController(){
    window.equipmentController = new function(){
        scriptLoader.loadScript("js/common/dao/ship_dao.js");
        scriptLoader.loadScript("js/common/dataservice/title_service.js");
        
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
                
                    const coreHullContainer = document.createElement("DIV");
                        coreHullContainer.innerHTML = "Magburkolat: " + shipData.coreHull;
                        coreHullContainer.title = titleService.getTitleForOverview(shipData.shipType);
                        coreHullContainer.classList.add("border2px");
                        coreHullContainer.classList.add("bordercoloraaa");
                        coreHullContainer.classList.add("borderbottomridge");
                        coreHullContainer.classList.add("fontsize1_25rem");
                        coreHullContainer.classList.add("padding0_25rem");
                container.appendChild(coreHullContainer);
                
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
        }
    }
})();