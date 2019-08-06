scriptLoader.loadScript("/js/common/equipment/equipment_label_service.js");

function Equipments(){
    const equipped = {};

    this.addEquipment = function(containerId, equippedItem){
        if(!equipped[containerId]){
            equipped[containerId] = [];
        }
        equipped[containerId].push(equippedItem);
    }

    this.getItems = function(containerId){
        return equipped[containerId] || [];
    }

    this.removeItem = function(containerId, itemId){
        const items = this.getItems(containerId);
        for(let iIndex in items){
            if(items[iIndex].getId() == itemId){
                items.splice(iIndex, 1);
                break;
            }
        }

        if(itemCache.get(itemId).slot == "connector"){
            equipmentLabelService.updateShipStats(shipService.getShipType(), collectItemIds(items));
        }
    }
}