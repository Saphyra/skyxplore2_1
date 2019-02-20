(function EquipmentLabelService(){
    scriptLoader.loadScript("js/common/equipment/item_cache.js");
    scriptLoader.loadScript("js/common/localization/items.js");
    scriptLoader.loadScript("js/common/localization/description.js");

    window.equipmentLabelService = new function(){
        this.assembleTitleOfItem = assembleTitleOfItem;
        this.createShipDetails = createShipDetails;
        this.updateShipStats = updateShipStats;
    }
    
    function assembleTitleOfItem(itemId){
        if(itemId == null || itemId == undefined || typeof itemId !== "string"){
            throwException("IllegalArgument", "data must not be null or undefined");
        }
        const data = itemCache.get(itemId);
        const itemName = Items.getItem(data.id);

        let title = "";
        
        title += itemName.name;
        if(itemName.description != undefined){
            title += "\n" + itemName.description;
        }
        if(data.level != undefined){
            title += "\n" + Description.getDescription("level") + ": " + data.level;
        }
        if(data.score != undefined){
            title += " - " + Description.getDescription("score") + ": " + data.score;
        }
        
        switch(data.slot){
            case "ability":
                title += "\n" + Description.getDescription("energyusage") + ": " + data.energyusage;
                title += "\n" + Description.getDescription("active") + ": " + data.active + " - " + Description.getDescription("reload") + ": " + data.reload;
            break;
            case "connector":
                switch(data.type){
                    case "corehull":
                        title += "\n" + Description.getDescription("extra-corehull") + ": " + data.capacity;
                    break;
                    case "extender":
                        title += "\n" + Description.getDescription("extra-slots") + ": " + data.extendednum;
                    break;
                    case "generator":
                        title += "\n" + Description.getDescription("energyregen") + ": " + data.energyrecharge;
                    break;
                    case "battery":
                        title += "\n" + Description.getDescription("capacity") + ": " + data.capacity;
                    break;
                    case "storage":
                        title += "\n" + Description.getDescription("capacity") + ": " + data.capacity;
                    break;
                    default:
                        logService.log(data, "warn", "Unknown connector type");
                    break;
                }
            break;
            case "defense":
                title += "\n" + Description.getDescription("capacity") + ": " + data.capacity;
                if(data.type == "shield"){
                    title += "\n" + Description.getDescription("shield-recharge") + ": " + data.regeneration;
                    title += "\n" + Description.getDescription("energyusage") + ": " + data.energyusage;
                }
            break;
            case "ship":
                title += "\n" + Description.getDescription("corehull") + ": " + data.corehull;
                title += "\n" + Description.getDescription("weaponry") + " - " + Description.getDescription("front") + ": " + data.weapon.front + " - " + Description.getDescription("side") + ": " + data.weapon.side + " - " + Description.getDescription("back") + ": " + data.weapon.side;
                title += "\n" + Description.getDescription("defense") + " - " + Description.getDescription("front") + ": " + data.defense.front + " - " + Description.getDescription("side") + ": " + data.defense.side + " - " + Description.getDescription("back") + ": " + data.defense.side;
                title += "\n" + Description.getDescription("connector") + ": " + data.connector;
            break;
            case "weapon":
                title += "\n" + Description.getDescription("attack-speed") + ": " + data.attackSpeed + " - " + Description.getDescription("range") + ": " + data.range;
                title += "\n" + Description.getDescription("critical-rate") + ": " + data.criticalRate;
                title += "\n" + Description.getDescription("hull-damage") + ": " + data.hullDamage + " - " + Description.getDescription("shield-damage") + ": " + data.shieldDamage;
                title += "\n" + Description.getDescription("accuracy") + ": " + data.accuracy;
            break;
            case "material":
            break;
            default:
                logService.log(data, "warn", "Unknown slot ");
            break;
        }
        
        return title;
    }
    
    function createShipDetails(shipData){
        const container = document.createElement("DIV");
            
            const shipDetailsContainer = document.createElement("DIV");
                shipDetailsContainer.classList.add("ship-details-container");
                shipDetailsContainer.title = assembleTitleOfItem(shipData.shipType);
                
                const coreHullContainer = document.createElement("DIV");
                coreHullContainer.appendChild(createSpan(Description.getDescription("corehull") + ": "));
                    coreHullValue = createSpan(countCoreHull(shipData.coreHull, shipData.connectorEquipped));
                    coreHullValue.id = "core-hull-value";
                coreHullContainer.appendChild(coreHullValue);
            shipDetailsContainer.appendChild(coreHullContainer);
            
                const energyContainer = document.createElement("DIV");
                energyContainer.appendChild(createSpan(Description.getDescription("energy") + ": "));
                    const energyValue = createSpan(countEnergy(shipData.connectorEquipped));
                        energyValue.id = "energy-value";
                energyContainer.appendChild(energyValue);
                energyContainer.appendChild(createSpan(" - "));
                energyContainer.appendChild(createSpan(Description.getDescription("energyregen") + ": "));
                    const energyRegenValue = createSpan(countEnergyRegen(shipData.connectorEquipped));
                        energyRegenValue.id = "energy-regen-value";
                energyContainer.appendChild(energyRegenValue);
            shipDetailsContainer.appendChild(energyContainer);
            
                const storageContainer = document.createElement("DIV");
                storageContainer.appendChild(createSpan(Description.getDescription("storage") + ": "));
                    const storageValue = createSpan(countStorage(shipData.connectorEquipped));
                        storageValue.id = "storage-value";
                storageContainer.appendChild(storageValue);
            shipDetailsContainer.appendChild(storageContainer);
            
        container.appendChild(shipDetailsContainer);
        
                const abilityTitle = document.createElement("DIV");
                    abilityTitle.innerHTML = Description.getDescription("abilities");
                    abilityTitle.classList.add("ship-details-ability-title");
            container.appendChild(abilityTitle);
                
                const abilityContainer = document.createElement("DIV");
                    abilityContainer.classList.add("ship-details-ability-container");
                    abilityContainer.id = "ship-abilities";
                    
                    for(let aindex in shipData.ability){
                        const abilityData = itemCache.get(shipData.ability[aindex]);
                        const abilityElement = document.createElement("DIV");
                            abilityElement.classList.add("ship-details-ability");
                            abilityElement.innerHTML = Items.getItem(abilityData.id).name;
                            abilityElement.title = assembleTitleOfItem(abilityData.id);
                        abilityContainer.appendChild(abilityElement);
                    }
            container.appendChild(abilityContainer);
            
        return container;

        function createSpan(text){
            const label = document.createElement("SPAN");
                label.innerHTML = text;
            return label;
        }
    }

    function updateShipStats(shipType, connectors){
        $("#core-hull-value").text(countCoreHull(itemCache.get(shipType).coreHull, connectors));
        $("#energy-value").text(countEnergy(connectors));
        $("#energy-regen-value").text(countEnergyRegen(connectors));
        $("#storage-value").text(countStorage(connectors));
    }

    function countCoreHull(baseHull, connectors){
        let result = baseHull;
        for(let cindex in connectors){
            const connectorData = itemCache.get(connectors[cindex]);
            if(connectorData.type == "corehull"){
                result += connectorData.capacity;
            }
        }

        return result;
    }

    function countEnergy(connectors){
        let result = 0;
        for(let cindex in connectors){
            const connectorData = itemCache.get(connectors[cindex]);
            if(connectorData.type == "battery"){
                result += connectorData.capacity;
            }
        }

        return result;
    }

    function countEnergyRegen(connectors){
        let result = 0;
        for(let cindex in connectors){
            const connectorData = itemCache.get(connectors[cindex]);
            if(connectorData.type == "generator"){
                result += connectorData.energyrecharge;
            }
        }

        return result;
    }

    function countStorage(connectors){
        let result = 0;
        for(let cindex in connectors){
            const connectorData = itemCache.get(connectors[cindex]);
            if(connectorData.type == "storage"){
                result += connectorData.capacity;
            }
        }

        return result;
    }
})();