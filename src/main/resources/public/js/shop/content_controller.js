(function ContentController(){
    scriptLoader.loadScript("js/common/cache.js");
    scriptLoader.loadScript("js/common/equipment/item_cache.js");
    scriptLoader.loadScript("js/common/localization/items.js");
    scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");
    
    events.DISPLAY_CATEGORY = "display_category";
    
    const categoryCache = new Cache(loadItemsOfCategory);
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DISPLAY_CATEGORY},
        function(event){
            displayItemsOfCategory(event.getPayload());
        }
    ));
    
    function displayItemsOfCategory(categoryId){
        const container = document.getElementById("content");
            container.innerHTML = "";
        const itemIds = categoryCache.get(categoryId);
        
        itemIds.sort(function(a, b){
            return Items.getItem(a).name.localeCompare(Items.getItem(b).name);
        });
        
        for(let iindex in itemIds){
            container.appendChild(createItem(itemIds[iindex]));
        }
        
        function createItem(itemId){
            const container = document.createElement("DIV");
                container.classList.add("element");
                container.title = equipmentLabelService.assembleTitleOfItem(itemId)
                
                const titleContainer = document.createElement("DIV");
                    titleContainer.classList.add("element-title-container");
                    
                    const elementTitle = document.createElement("DIV");
                        elementTitle.classList.add("element-title");
                        elementTitle.innerHTML = Items.getItem(itemId).name;
                titleContainer.appendChild(elementTitle);
                
                    const elementDescription = document.createElement("DIV");
                        elementDescription.classList.add("element-description");
                        elementDescription.innerHTML = Items.getItem(itemId).description;
                titleContainer.appendChild(elementDescription);
                
            container.appendChild(titleContainer);
            
                const contentContainer = document.createElement("DIV");
                    contentContainer.classList.add("element-content-container");
                    
                    const buyPrice = itemCache.get(itemId).buyprice;
                    const priceContainer = document.createElement("DIV");
                        priceContainer.innerHTML = Localization.getAdditionalContent("cost") + ": " + buyPrice;
                contentContainer.appendChild(priceContainer);
                
                    const buyButton = document.createElement("BUTTON");
                        buyButton.classList.add("add-to-cart-button");
                        buyButton.innerHTML = Localization.getAdditionalContent("add-to-cart");
                        
                        if(buyPrice > moneyController.getUsableBalance()){
                            buyButton.disabled = true;
                            buyButton.title = Localization.getAdditionalContent("not-enough-money");
                        }
                        
                        buyButton.onclick = function(){
                            eventProcessor.processEvent(new Event(events.ADD_TO_CART, itemId));
                        }
                        
                contentContainer.appendChild(buyButton);
                    
            container.appendChild(contentContainer);
            return container;
        }
    }
    
    function loadItemsOfCategory(categoryId){
        const response = dao.sendRequest(HttpMethod.GET, Mapping.concat(Mapping.ITEMS_OF_CATEGORY, categoryId));
        return response.status == ResponseStatus.OK ? JSON.parse(response.body) : throwException("InvalidResponse", response.toString());
    }
})();