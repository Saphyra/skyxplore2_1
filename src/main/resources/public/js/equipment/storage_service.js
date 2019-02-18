(function EquipmentService(){
    scriptLoader.loadScript("js/common/equipment/item_cache.js");
    scriptLoader.loadScript("js/common/localization/category_names.js");
    scriptLoader.loadScript("js/common/localization/items.js");
    scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");
    
    events.LOAD_STORAGE = "load_storage";
    events.ADD_TO_STORAGE = "add_to_storage";
    
    const categories = [];
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_STORAGE},
        loadEquipment
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType == events.ITEM_UNEQUIPPED
                || eventType == events.ADD_TO_STORAGE;
        },
        function(event){
            displayItem(event.getPayload().itemId);
        }
    ));
    
    function loadEquipment(){
        const request = new Request(HttpMethod.GET, Mapping.EQUIPMENT_STORAGE);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(items){
                for(let iIndex in items){
                    displayItem(items[iIndex]);
                }
            }
        dao.sendRequestAsync(request);
    }
    
    function displayItem(itemId){
        $("#empty-storage").hide();
        
        const itemData = itemCache.get(itemId);
        let category = getCategory(itemData.category);
        if(!category){
            category = createCategory(itemData.category);
        }
        
        category.addItem(itemData);
        
        function getCategory(categoryId){
            let result = null;
            for(let cindex in categories){
                const category = categories[cindex];
                if(category.getId() == categoryId){
                    result = category;
                    break;
                }
            }
            return result;
        }
        
        function createCategory(categoryId){
            const container = document.createElement("DIV");
                container.classList.add("equipment-slot-container");
                
                const categoryTitle = document.createElement("DIV");
                    categoryTitle.classList.add("equipment-slot-container-title");
                    categoryTitle.innerHTML = CategoryNames.getCategoryName(categoryId);
            container.appendChild(categoryTitle);
            
                const equipmentListContainer = document.createElement("DIV");
                    equipmentListContainer.classList.add("equipment-slot-list-container");
            container.appendChild(equipmentListContainer);
            
            document.getElementById("equipment-list").appendChild(container);
            
            const nextCategoryIndex = findNextCategoryAlphabetically(categoryId);
            const nextCategory = categories[nextCategoryIndex] || null;
            document.getElementById("equipment-list").insertBefore(container, nextCategory ? nextCategory.getContainer() : null);
            
            const category = new Category(categoryId, container, equipmentListContainer)
            categories.splice(nextCategoryIndex, 0, category);
            
            return category;
            
            function findNextCategoryAlphabetically(categoryId){
                const categoryName = CategoryNames.getCategoryName(categoryId);
                let result = 0;
                for(result; result < categories.length; result++){
                    const nextCategoryName = CategoryNames.getCategoryName(categories[result].getId());
                    if(categoryName.localeCompare(nextCategoryName) < 0){
                        break;
                    }
                }
                return result;
            }
        }
    }
    
    function Category(id, container, list){
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
        
        function createItem(itemData){
            const container = document.createElement("DIV");
                container.classList.add("slot");
                container.classList.add("equipment-list-element");
                container.title = equipmentLabelService.assembleTitleOfItem(itemData.id);
                
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
            
            const item = new Item(itemData.id, listContainer, container, amountElement);
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
    
    function Item(id, parent, container, amount){
        const itemId = id;
        const categoryList = parent;
        const itemContainer = container;
        const amountElement = amount;
        let currentAmount = 0;
        
        this.getId = function(){
            return itemId;
        }
        
        this.getContainer = function(){
            return itemContainer;
        }
        
        parent.appendChild(container);
        
        this.increaseAmount = function(){
            currentAmount++;
            amountElement.innerHTML = currentAmount;
        }
    }
})();