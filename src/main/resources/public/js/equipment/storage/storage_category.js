scriptLoader.loadScript("js/equipment/storage/storage_item.js");
scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");
scriptLoader.loadScript("js/common/localization/items.js");

function StorageCategory(id, container, list){
    const categoryId = id;
    const categoryContainer = container;
    const listContainer = list;
    const items = [];

    this.getContainer = function(){
        return categoryContainer;
    }

    this.getId = function(){
        return categoryId;
    }

    this.addItem = function(itemData){
        let item = getItem(itemData.id);
        if(!item){
            item = createItem(itemData);
        }
        item.increaseAmount();

        function createItem(itemData){
            const container = document.createElement("DIV");
                container.classList.add("slot");
                container.classList.add("equipment-list-element");
                container.title = equipmentLabelService.assembleTitleOfItem(itemData.id);

                if(itemData.slot === "ship"){
                    container.onclick = function(){
                        eventProcessor.processEvent(new Event(events.EQUIP_SHIP, itemData.id));
                    }
                }else{
                    container.draggable = true;
                    container.ondragstart = function(e){
                        e.dataTransfer.setData("item", itemData.id);
                        equipService.dragStart(e);
                    }
                    container.ondragend = equipService.dragEnd;
                }

                const titleContainer = document.createElement("DIV");
                    const itemName = document.createElement("SPAN");
                        itemName.innerHTML = Items.getItem(itemData.id).name;
                titleContainer.appendChild(itemName);
                    const amountPrefix = document.createElement("SPAN");
                        amountPrefix.innerHTML = " (";
                titleContainer.appendChild(amountPrefix);
                    const amountElement = document.createElement("SPAN");
                titleContainer.appendChild(amountElement);
                    const amountSuffix = document.createElement("SPAN");
                        amountSuffix.innerHTML = ")";
                titleContainer.appendChild(amountSuffix);
            container.appendChild(titleContainer);

            const nextItemIndex = findNextItemAlphabetically(itemData.id);
            const nextItem = items[nextItemIndex] || null;
            listContainer.insertBefore(container, nextItem ? nextItem.getContainer() : null);

            const item = new StorageItem(itemData.id, listContainer, container, amountElement);
            items.splice(nextItemIndex, 0, item);
            return item;

            function findNextItemAlphabetically(itemId){
                const itemName = Items.getItem(itemId).name;
                let result = 0;
                for(result; result < items.length; result++){
                    const nextItemName = Items.getItem(items[result].getId()).name;
                    if(itemName.localeCompare(nextItemName) < 0){
                        break;
                    }
                }
                return result;
            }
        }
    }

    this.removeItem = function(itemId){
        let item = getItem(itemId);
        if(!item){
            throwException("IllegalState", "There is no items left in storage with itemId " + itemId);
        }
        item.decreaseAmount();

        if(!item.getAmount()){
            listContainer.removeChild(item.getContainer());
            items.splice(items.indexOf(item), 1);

            if(!items.length){
                document.getElementById("equipment-list").removeChild(categoryContainer);
                const categories = storageService.getCategories();
                categories.splice(categories.indexOf(this), 1);
            }
        }
    }

    function getItem(itemId){
        let result = null;
        for(iIndex in items){
            const item = items[iIndex];
            if(item.getId() == itemId){
                result = item;
                break;
            }
        }
        return result;
    }
}