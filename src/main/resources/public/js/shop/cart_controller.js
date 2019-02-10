(function CartController(){
    scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");
    
    events.ADD_TO_CART = "add_to_cart";
    
    const cart = {};
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.ADD_TO_CART},
        addToCart
    ));
    
    function addToCart(event){
        const itemId = event.getPayload();
        if(cart[itemId]){
            
        }else{
            createNewItem(itemId)
        }
            
        function createNewItem(itemId){
            const container = document.getElementById("cart-items");
            $("#empty-cart").hide();
            
            container.appendChild(createCartElement(itemId));
            
            function createCartElement(itemId){
                const container = document.createElement("DIV");
                    container.classList.add("cart-element");
                    container.title = equipmentLabelService.assembleTitleOfItem(itemId);
                    
                    const title = document.createElement("DIV");
                        title.classList.add("cart-element-title");
                        title.innerHTML = Items.getItem(itemId).name + " x " + 1;
                container.appendChild(title);
                
                    const buyPrice = itemCache.get(itemId).buyprice
                    const priceContainer = document.createElement("DIV");
                        priceContainer.classList.add("cart-element-price-container");
                        priceContainer.innerHTML = Localization.getAdditionalContent("cost") + ": " + buyPrice + " x " + 1 + " = " + buyPrice;
                container.appendChild(priceContainer);
                
                    const cancelButton = document.createElement("BUTTON");
                        cancelButton.innerHTML = Localization.getAdditionalContent("cancel");
                container.appendChild(cancelButton);
                        
                return container;
            }
        }
    }
    
    
})();