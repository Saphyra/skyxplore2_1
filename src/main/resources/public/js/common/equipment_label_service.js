(function EquipmentLabelService(){
    scriptLoader.loadScript("js/common/localization/items.js");
    scriptLoader.loadScript("js/common/localization/description.js");

    window.equipmentLabelService = new function(){
        this.assembleTitleOfItem = assembleTitleOfItem;
    }
    
    function assembleTitleOfItem(data){
        if(data == null || data == undefined){
            throwException("IllegalArgument", "data must not be null or undefined");
        }

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
})();