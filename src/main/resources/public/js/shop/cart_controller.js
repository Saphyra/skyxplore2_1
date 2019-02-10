(function CartController(){
    scriptLoader.loadScript("js/common/equipment/equipment_label_service.js");
    
    events.ADD_TO_CART = "add_to_cart";
    events.REMOVE_FROM_CART = "remove_from_cart";
    
    const cart = {};
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.ADD_TO_CART},
        addToCart
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.REMOVE_FROM_CART},
        removeFromCart
    ));
    
    function addToCart(event){
        const itemId = event.getPayload();
        const cartItem = cart[itemId];
        if(cartItem){
            cartItem.increaseAmount();
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
                        const titlePrefix = createSpanWithValue(Items.getItem(itemId).name + " x ");
                        const titleAmount = createSpanWithValue(1);
                    title.appendChild(titlePrefix);
                    title.appendChild(titleAmount);
                container.appendChild(title);
                
                    const buyPrice = itemCache.get(itemId).buyprice
                    const priceContainer = document.createElement("DIV");
                        priceContainer.classList.add("cart-element-price-container");
                        
                        const prefix = createSpanWithValue(Localization.getAdditionalContent("cost") + ": " + buyPrice + " x ");
                        const costAmount = createSpanWithValue(1);
                        const delimiter = createSpanWithValue(" = ");
                        const totalCost = createSpanWithValue(buyPrice);
                    priceContainer.appendChild(prefix);    
                    priceContainer.appendChild(costAmount);    
                    priceContainer.appendChild(delimiter);    
                    priceContainer.appendChild(totalCost);
                    
                container.appendChild(priceContainer);
                
                    const cancelButton = document.createElement("BUTTON");
                        cancelButton.innerHTML = Localization.getAdditionalContent("cancel");
                        cancelButton.onclick = function(){
                            eventProcessor.processEvent(new Event(events.REMOVE_FROM_CART, itemId));
                        }
                container.appendChild(cancelButton);
                
                cart[itemId] = new CartItem(itemId, buyPrice, titleAmount, costAmount, totalCost, container);
                
                return container;
            }
            
            function createSpanWithValue(value){
                const span = document.createElement("SPAN");
                    span.innerHTML = value;
                return span;
            }
        }
    }
    
    function removeFromCart(event){
        const itemId = event.getPayload();
        cart[itemId].decreaseAmount();
    }
    
    function CartItem(id, bPrice, tAmountNode, cAmountNode, tCostNode, container){
        const itemId = id;
        const buyPrice = bPrice;
        const titleAmountNode = tAmountNode;
        const costAmountNode = cAmountNode;
        const totalCostNode = tCostNode;
        const itemContainer = container;
        let amount = 1;
        
        this.increaseAmount = function(){
            amount++;
            updateDisplay();
        }
        
        this.decreaseAmount = function(){
            amount--;
            if(amount == 0){
                document.getElementById("cart-items").removeChild(itemContainer);
                delete cart[itemId];
            }else{
                updateDisplay();
            }
        }
        
        function updateDisplay(){
            titleAmountNode.innerHTML = amount;
            costAmountNode.innerHTML = amount;
            totalCostNode.innerHTML = amount * buyPrice;
        }
    }
})();