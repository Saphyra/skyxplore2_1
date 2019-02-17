(function EquipmentService(){
    scriptLoader.loadScript("js/common/equipment/item_cache.js");
    scriptLoader.loadScript("js/common/localization/category_names.js");
    scriptLoader.loadScript("js/common/localization/items.js");
    scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");
    
    events.LOAD_EQUIPMENT = "load_equipment";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_EQUIPMENT},
        loadEquipment
    ));
    
    function loadEquipment(){
        const request = new Request(HttpMethod.GET, Mapping.EQUIPMENT_STORAGE);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(items){
                displayItems(items);
            }
        dao.sendRequestAsync(request);
    }
    
    function displayItems(items){
        const itemMap = mapItems(items);
        
        displayItemMap(itemMap);
        
        function mapItems(items){
            const countMap = countItems(items);
            const categoryMap = mapByCategories(countMap);
            const orderedMap = orderItems(categoryMap);
            
            return orderedMap;
            
            function countItems(items){
                const result = {};
            
                for(let index in items){
                    const itemId = items[index];
                    if(!result[itemId]){
                        result[itemId] = 0;
                    }
                    result[itemId]++;
                }
                return result;
            }
            
            function mapByCategories(map){
                const result = {};
                
                for(let itemId in map){
                    const itemData = itemCache.get(itemId);
                    if(!result[itemData.category]){
                        result[itemData.category] = {};
                    }
                    result[itemData.category][itemId] = map[itemId];
                }
                return result;
            }
            
            function orderItems(map){
                for(let categoryId in map){
                    sortedCategory = orderMapByProperty(
                        map[categoryId],
                        function(a, b){
                            return Items.getItem(a.getKey()).name.localeCompare(Items.getItem(b.getKey()));
                        }
                    );
                    map[categoryId] = sortedCategory;
                }
                    
                return orderMapByProperty(
                    map,
                    function(a, b){
                        return CategoryNames.getCategoryName(a.getKey()).localeCompare(CategoryNames.getCategoryName(b.getKey()));
                    }
                );
            }
        }
        
        function displayItemMap(itemMap){
            if(Object.keys(itemMap).length > 0){
                $("#empty-storage").hide();
            }
            
            const container = document.getElementById("equipment-list");
            
            for(let categoryId in itemMap){
                container.appendChild(createCategoryElement(categoryId, itemMap[categoryId]));
            }
        }
        
        function createCategoryElement(categoryId, items){
            const container = document.createElement("DIV");
                container.classList.add("equipment-slot-container");
                
                const categoryTitle = document.createElement("DIV");
                    categoryTitle.classList.add("equipment-slot-container-title");
                    categoryTitle.innerHTML = CategoryNames.getCategoryName(categoryId);
            container.appendChild(categoryTitle);
            
                const equipmentListContainer = document.createElement("DIV");
                    equipmentListContainer.classList.add("equipment-slot-list-container");
                    
                    for(let itemId in items){
                        equipmentListContainer.appendChild(createEquipmentItem(itemId, items[itemId]));
                    }
                    
            container.appendChild(equipmentListContainer);
                
            return container;
        }
        
        function createEquipmentItem(itemId, amount){
            const container = document.createElement("DIV");
                container.classList.add("slot");
                container.classList.add("equipment-list-element");
                container.title = equipmentLabelService.assembleTitleOfItem(itemId);
                
                const titleContainer = document.createElement("DIV");
                    const itemName = document.createElement("SPAN");
                        itemName.innerHTML = Items.getItem(itemId).name;
                titleContainer.appendChild(itemName);
                    const amountPrefix = document.createElement("SPAN");
                        amountPrefix.innerHTML = " (";
                titleContainer.appendChild(amountPrefix);
                    const amountElement = document.createElement("SPAN");
                        amountElement.innerHTML = amount;
                titleContainer.appendChild(amountElement);
                    const amountSuffix = document.createElement("SPAN");
                        amountSuffix.innerHTML = ")";
                titleContainer.appendChild(amountSuffix);
            container.appendChild(titleContainer);
            return container;
                
        }
    }
})();