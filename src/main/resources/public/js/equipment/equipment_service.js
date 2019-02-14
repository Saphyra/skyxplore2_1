(function EquipmentService(){
    scriptLoader.loadScript("js/common/equipment/item_cache.js");
    scriptLoader.loadScript("js/common/localization/category_names.js");
    scriptLoader.loadScript("js/common/localization/items.js");
    
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
    }
})();