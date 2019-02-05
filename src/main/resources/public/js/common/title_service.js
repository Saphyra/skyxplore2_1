(function TitleService(){
    window.titleService = new function(){
        this.assembleTitleOfItem = assembleTitleOfItem;
    }
    
    //TODO localization
    function assembleTitleOfItem(data){
        if(data == null || data == undefined){
            throwException("IllegalArgument", "data must not be null or undefined");
        }
        
        let title = "";
        
        title += data.name;
        if(data.description != undefined){
            title += "\n" + data.description;
        }
        if(data.level != undefined){
            title += "\nSzint: " + data.level;
        }
        if(data.score != undefined){
            title += " - Pont: " + data.score;
        }
        
        switch(data.slot){
            case "ability":
                title += "\nEnergiahasználat: " + data.energyusage;
                title += "\nAktív: " + data.active + " - Újratöltés: " + data.reload;
            break;
            case "connector":
                switch(data.type){
                    case "corehull":
                        title += "\nExtra magburkolat: " + data.capacity;
                    break;
                    case "extender":
                        title += "\nExtra hely: " + data.extendednum;
                    break;
                    case "generator":
                        title += "\nEnergiatermelés: " + data.energyrecharge;
                    break;
                    case "battery":
                        title += "\nKapacitás: " + data.capacity;
                    break;
                    case "storage":
                        title += "\nKapacitás: " + data.capacity;
                    break;
                    default:
                        logService.log(data, "warn", "Unknown connector type");
                    break;
                }
            break;
            case "defense":
                title += "\nKapacitás: " + data.capacity;
                if(data.type == "shield"){
                    title += "\nRegeneráció: " + data.regeneration;
                    title += "\nEnergiahasználat: " + data.energyusage;
                }
            break;
            case "ship":
                title += "\nMagburkolat: " + data.corehull;
                title += "\nFegyverzet - Elöl: " + data.weapon.front + " - Oldalt: " + data.weapon.side + " - Hátul: " + data.weapon.side;
                title += "\nVédelem - Elöl: " + data.defense.front + " - Oldalt: " + data.defense.side + " - Hátul: " + data.defense.side;
                title += "\nCsatlakozók: " + data.connector;
            break;
            case "weapon":
                title += "\nTámadási sebesség: " + data.attackSpeed + " - Hatótávolság: " + data.range;
                title += "\nKritikus találati esély: " + data.criticalRate;
                title += "\nBurkolatsebzés: " + data.hullDamage + " - Pajzs sebzés: " + data.shieldDamage;
                title += "\nPontosság: " + data.accuracy;
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