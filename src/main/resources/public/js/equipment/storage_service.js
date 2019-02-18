(function EquipmentService(){
    scriptLoader.loadScript("js/common/equipment/item_cache.js");
    scriptLoader.loadScript("js/common/localization/category_names.js");
    scriptLoader.loadScript("js/common/localization/items.js");
    scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");
    
    events.LOAD_STORAGE = "load_storage";
    
    const categories = {};
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_STORAGE},
        loadEquipment
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ITEM_UNEQUIPPED},
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
        if(!categories[itemData.category]){
            createCategory(itemData.category);
        }
        
        categories[itemData.category].addItem(itemData);
        
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
            
            categories[categoryId] = new Category(container, equipmentListContainer);
        }
    }
    
    function Category(container, list){
        const categoryContainer = container;
        const listContainer = list;
        const items = {};
        
        this.addItem = function(itemData){
            if(!items[itemData.id]){
                createItem(itemData);
            }
            items[itemData.id].increaseAmount();
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
            
            items[itemData.id] = new Item(listContainer, container, amountElement);
        }
    }
    
    function Item(parent, container, amount){
        const categoryList = parent;
        const itemContainer = container;
        const amountElement = amount;
        let currentAmount = 0;
        
        parent.appendChild(container);
        
        this.increaseAmount = function(){
            currentAmount++;
            amountElement.innerHTML = currentAmount;
        }
    }
})();