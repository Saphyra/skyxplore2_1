(function EquipmentController(){
    window.equipmentController = new function(){
        scriptLoader.loadScript("js/common/dao/character_dao.js");
        scriptLoader.loadScript("js/common/translator/translator.js");
        scriptLoader.loadScript("js/common/translator/title_service.js");
        
        this.equipment = null;
        
        this.loadEquipment = loadEquipment;
        this.showEquipment = showEquipment;
    }
    
    /*
    Queries the character's equipment from the server.
    */
    function loadEquipment(){
        try{
            this.equipment = mapElements(characterDao.getEquipmentOfCharacter(sessionStorage.characterId));
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function mapElements(elements){
            try{
                const result = {};
                
                for(let eindex in elements){
                    const element = elements[eindex];
                    if(!result[element]){
                        result[element] = 0;
                    }
                    
                    result[element]++;
                }
                
                return result;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return {};
            }
        }
    }
    
    /*
    Displays the stored equipments.
    Throws:
        IllegalState exception if equipments are not loaded.
    */
    function showEquipment(){
        try{
            if(equipmentController.equipment == null){
                throwException("IllegalState", "equipment must not be null.");
            }
            
            const elements = groupAndOrderEquipments(equipmentController.equipment);
            const container = document.getElementById("equipmentlist");
                container.innerHTML = "";
                
            if(!Object.keys(elements).length){
                container.appendChild(createEmptyStorageMessage());
            }else{
                for(let slot in elements){
                    container.appendChild(createEquipmentList(slot, elements[slot]));
                }
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function groupAndOrderEquipments(equipments){
            try{
                const result = {};
                
                for(let equipmentId in equipments){
                    const amount = equipments[equipmentId];
                    const equipmentData = cache.get(equipmentId);
                    
                    if(!result[equipmentData.slot]){
                        result[equipmentData.slot] = {};
                    }
                    result[equipmentData.slot][equipmentId] = amount;
                }
                
                return orderElements(result);
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return {};
            }
            
            function orderElements(elements){
                try{
                    const result = {};
                    const list = [];
                    
                    for(let slot in elements){
                        list.push({slot: slot, elements: elements[slot]});
                    }
                    list.sort(function(a, b){
                        return translator.translateSlot(a.slot).localeCompare(translator.translateSlot(b.slot));
                    })
                    
                    for(let i in list){
                        result[list[i].slot] = list[i].elements;
                    }
                    
                    for(let sindex in result){
                        result[sindex] = orderSlotElements(result[sindex]);
                    }
                    
                    return result;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                    return {};
                }
                
                function orderSlotElements(elements){
                    try{
                        const result = {};
                        const list = [];
                        
                        for(let equipmentId in elements){
                            list.push({equipmentId: equipmentId, amount: elements[equipmentId]});
                        }
                        list.sort(function(a, b){
                            return cache.get(a.equipmentId).name.localeCompare(cache.get(b.equipmentId).name);
                        });
                        
                        for(let i in list){
                            result[list[i].equipmentId] = list[i].amount;
                        }
                        
                        return result;
                    }catch(err){
                        const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                        logService.log(message, "error");
                        return {};
                    }
                }
            }
        }
        
        function createEmptyStorageMessage(){
            try{
                const element = document.createElement("DIV");
                    element.innerHTML = "A raktár üres.";
                    
                    element.classList.add("fontSize2rem");
                    element.classList.add("margin1rem");
                    element.classList.add("textaligncenter");
                    
                return element;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function createEquipmentList(slot, elements){
            try{
                const container = document.createElement("DIV");
                    container.classList.add("equipmentslotcontainer");
                
                    const slotNameContainer = document.createElement("DIV");
                        slotNameContainer.classList.add("equipmentslotcontainertitle");
                        slotNameContainer.innerHTML = translator.translateSlot(slot);
                        
                container.appendChild(slotNameContainer);
                
                    const equipmentListContainer = document.createElement("DIV");
                        equipmentListContainer.classList.add("equipmentslotlistcontainer");
                        
                        for(let equipmentId in elements){
                            equipmentListContainer.appendChild(createEquipmentListElement(equipmentId, elements[equipmentId]));
                        }
                        
                container.appendChild(equipmentListContainer);
                
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return document.createElement("DIV");
            }
            
            function createEquipmentListElement(equipmentId, amount){
                try{
                    const equipmentData = cache.get(equipmentId);
                    const container = document.createElement("DIV");
                        container.classList.add("equipmentlistelement");
                        container.classList.add("slot");
                        container.innerHTML = equipmentData.name + " (x" + amount + ")";
                        container.title = titleService.getTitleForOverview(equipmentId);
                    return container;
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                    return document.createElement("DIV");
                }
            }
        }
    }
})()