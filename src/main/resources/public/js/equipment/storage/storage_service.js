(function StorageService(){
    scriptLoader.loadScript("/js/common/equipment/item_cache.js");
    scriptLoader.loadScript("/js/common/localization/category_names.js");
    scriptLoader.loadScript("/js/equipment/storage/storage_category.js");

    events.LOAD_STORAGE = "load_storage";
    events.ADD_TO_STORAGE = "add_to_storage";
    
    let categories = [];

    window.storageService = new function(){
        this.getCategories = function(){
            return categories;
        }
    }
    
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
            const payload = event.getPayload();
            if(itemCache.get(payload.getId()).type == "extender"){
                eventProcessor.processEvent(new Event(events.LOAD_STORAGE));
                return;
            }
            displayItem(payload.getId());
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.SHIP_EQUIPPED},
        function(event){
            const itemId = event.getPayload();
            removeFromStorage(itemId);
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
            function(eventType){return eventType === events.ITEM_EQUIPPED},
            function(event){
                const payload = event.getPayload();
                removeFromStorage(payload.itemId);
            }
        ));
    
    function loadEquipment(){
        const request = new Request(HttpMethod.GET, Mapping.EQUIPMENT_STORAGE);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(items){
                categories = [];
                document.getElementById("equipment-list").innerHTML = "";
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
            
            const nextCategoryIndex = findNextCategoryAlphabetically(categoryId);
            const nextCategory = categories[nextCategoryIndex] || null;
            document.getElementById("equipment-list").insertBefore(container, nextCategory ? nextCategory.getContainer() : null);
            
            const category = new StorageCategory(categoryId, container, equipmentListContainer)
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
    
    function removeFromStorage(itemId){
        const itemData = itemCache.get(itemId);
        
        const category = getCategory(itemData.category);
        if(!category){
            throwException("IllegalState", "There is no item in storage with categoryId " + itemData.category);
        }
            category.removeItem(itemId);
    }
    
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
})();